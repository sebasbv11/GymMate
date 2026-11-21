package com.jsbv99.gymmate.ui.screens.workout

import android.os.SystemClock
import androidx.compose.runtime.Composable
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jsbv99.gymmate.data.RoutineSamples
import com.jsbv99.gymmate.data.routines.FakeRoutineRepository
import com.jsbv99.gymmate.data.workout.FakeWorkoutRepository

@Composable
internal fun RestTestContent(seconds: Int = 60) {
    val model: WorkoutViewModel = viewModel(factory = viewModelFactory {
        initializer {
            val handle = createSavedStateHandle()
            if (!handle.contains("workout_state")) handle["workout_state"] = WorkoutUiState(
                routine = RoutineSamples.routines.first(), sessionId = "test",
                startedAtMillis = SystemClock.elapsedRealtime(), phase = WorkoutPhase.REST,
                remainingRestMillis = seconds * 1000L)
            WorkoutViewModel(FakeRoutineRepository(), FakeWorkoutRepository(), handle)
        }
    })
    RestRoute(model)
}
