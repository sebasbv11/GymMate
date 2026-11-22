package com.jsbv99.gymmate.ui.screens.workout

import android.os.SystemClock
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsbv99.gymmate.data.routines.RoutineRepository
import com.jsbv99.gymmate.data.workout.WorkoutRepository
import com.jsbv99.gymmate.model.WorkoutSession
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel(
    private val routines: RoutineRepository,
    private val workouts: WorkoutRepository,
    private val savedState: SavedStateHandle,
    private val monotonicMillis: () -> Long = { SystemClock.elapsedRealtime() },
    private val wallMillis: () -> Long = { System.currentTimeMillis() }
) : ViewModel() {
    @Inject constructor(routines: RoutineRepository, workouts: WorkoutRepository, savedState: SavedStateHandle) :
        this(routines, workouts, savedState, { SystemClock.elapsedRealtime() }, { System.currentTimeMillis() })
    private val mutableState = MutableStateFlow(
        (savedState.get<WorkoutUiState>(KEY) ?: WorkoutUiState()).copy(saving = false)
    )
    val uiState = mutableState.asStateFlow()

    private fun update(state: WorkoutUiState) {
        mutableState.value = state
        savedState[KEY] = state
    }

    fun start(routineId: String): Boolean {
        if (uiState.value.saving) return false
        val routine = routines.routines.value.find { it.id == routineId } ?: return false
        update(WorkoutUiState(routine = routine.copy(exercises = routine.exercises.toList()),
            sessionId = UUID.randomUUID().toString(), startedAtMillis = monotonicMillis()))
        return true
    }

    fun completeSet() {
        val state = uiState.value
        if (state.routine == null || state.phase != WorkoutPhase.ACTIVE || state.complete || state.saving) return
        val completed = state.completedSets + 1
        update(state.copy(completedSets = completed, remainingRestMillis = REST_MILLIS,
            phase = if (completed < state.totalSets) WorkoutPhase.REST else WorkoutPhase.ACTIVE))
    }

    fun skipRest() {
        if (uiState.value.phase == WorkoutPhase.REST) update(uiState.value.copy(phase = WorkoutPhase.ACTIVE))
    }

    fun finishRest() {
        if (uiState.value.remainingRestMillis == 0L) skipRest()
    }

    /** Solo se ejecuta mientras la ruta está RESUMED. La cancelación pausa el descanso. */
    suspend fun tickWhileVisible() {
        var last = monotonicMillis()
        while (true) {
            val now = monotonicMillis()
            val state = uiState.value
            if (state.routine == null || state.phase == WorkoutPhase.SUMMARY) return
            val start = state.startedAtMillis.coerceAtMost(now)
            update(state.copy(startedAtMillis = start,
                durationSeconds = ((now - start).coerceAtLeast(0) / 1000).toInt(),
                remainingRestMillis = if (state.phase == WorkoutPhase.REST)
                    (state.remainingRestMillis - (now - last).coerceAtLeast(0)).coerceAtLeast(0)
                    else state.remainingRestMillis))
            last = now
            delay(100)
        }
    }

    fun finish() {
        val state = uiState.value
        if (!state.complete || state.phase != WorkoutPhase.ACTIVE || state.saving) return
        val routine = state.routine ?: return
        // Mantener el mismo resultado también ante error/reintento.
        val result = state.result ?: WorkoutSession(state.sessionId, routine, wallMillis(),
            ((monotonicMillis() - state.startedAtMillis).coerceAtLeast(0) / 1000).toInt(),
            state.completedSets, state.completedReps)
        update(state.copy(saving = true, error = null, result = result))
        viewModelScope.launch {
            try {
                workouts.save(result)
                update(uiState.value.copy(saving = false, result = result,
                    durationSeconds = result.durationSeconds, phase = WorkoutPhase.SUMMARY))
            } catch (e: CancellationException) { throw e }
            catch (_: Exception) {
                update(uiState.value.copy(saving = false, error = "No se pudo guardar la sesión. Inténtalo de nuevo."))
            }
        }
    }

    fun repeat(): Boolean {
        val routine = uiState.value.result?.routine ?: return false
        update(WorkoutUiState(routine = routine, sessionId = UUID.randomUUID().toString(),
            startedAtMillis = monotonicMillis()))
        return true
    }

    fun clear() { if (!uiState.value.saving) update(WorkoutUiState()) }

    private companion object {
        const val KEY = "workout_state"
        const val REST_MILLIS = 60_000L
    }
}
