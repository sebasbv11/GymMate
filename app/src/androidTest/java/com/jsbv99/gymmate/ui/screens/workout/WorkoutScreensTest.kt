package com.jsbv99.gymmate.ui.screens.workout

import androidx.compose.runtime.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.SavedStateHandle
import com.jsbv99.gymmate.data.RoutineSamples
import com.jsbv99.gymmate.data.routines.FakeRoutineRepository
import com.jsbv99.gymmate.data.workout.FakeWorkoutRepository
import com.jsbv99.gymmate.ui.theme.GymMateTheme
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class WorkoutScreensTest {
    @get:Rule val compose = createComposeRule()

    @Test fun selectedRoutineSavesOnceAndRepeatsAsNewSession() {
        val repo = FakeWorkoutRepository()
        lateinit var model: WorkoutViewModel
        compose.setContent {
            model = remember { WorkoutViewModel(FakeRoutineRepository(), repo, SavedStateHandle()).also {
                it.start(RoutineSamples.routines.last().id)
            } }
            val state by model.uiState.collectAsState()
            GymMateTheme {
                WorkoutScreen(state, { model.completeSet(); model.skipRest() }, model::finish, {})
            }
        }
        compose.onNodeWithText("Día de piernas").assertExists()
        repeat(4) { compose.onNodeWithText("Completar serie").performScrollTo().performClick() }
        compose.onNodeWithText("4 de 4 series completadas").assertExists()
        compose.onNodeWithText("Ver resumen").performScrollTo().performClick()
        compose.waitUntil(5000) { repo.sessions.value.size == 1 }
        compose.runOnIdle {
            model.finish()
            val session = repo.sessions.value.single()
            assertEquals(4, session.completedSets)
            assertEquals(60, session.completedReps)
            assertEquals(WorkoutPhase.SUMMARY, model.uiState.value.phase)
            model.start(session.routine.id)
            assertEquals(0, model.uiState.value.completedSets)
            assertNotEquals(session.id, model.uiState.value.sessionId)
        }
    }

    @Test fun exitRequiresConfirmation() {
        var exits = 0
        compose.setContent { GymMateTheme {
            WorkoutScreen(WorkoutUiState(routine = RoutineSamples.routines.first()), {}, {}, { exits++ })
        } }
        compose.onNodeWithText("Salir del entrenamiento").performScrollTo().performClick()
        compose.onNodeWithText("Continuar").performClick()
        compose.runOnIdle { assertEquals(0, exits) }
        compose.onNodeWithText("Salir del entrenamiento").performScrollTo().performClick()
        compose.onNodeWithText("Salir", substring = false).performClick()
        compose.runOnIdle { assertEquals(1, exits) }
    }

    @Test fun restReachesZeroWithoutAutomaticNavigation() {
        compose.setContent { GymMateTheme { RestTestContent(1) } }
        compose.waitUntil(5000) { compose.onAllNodesWithText("00:00").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithText("Finalizar descanso").performScrollTo().assertIsEnabled()
        compose.onNodeWithText("Tiempo de descanso").assertExists()
    }

    @Test fun savedStateRestoresRestAndSummaryWithoutDuplicateSessions() {
        val repo = FakeWorkoutRepository()
        lateinit var completedModel: WorkoutViewModel
        compose.runOnIdle {
            val handle = SavedStateHandle()
            val model = WorkoutViewModel(FakeRoutineRepository(), repo, handle)
            model.start(RoutineSamples.routines.last().id)
            model.completeSet()
            model.completeSet() // La segunda pulsación durante descanso se ignora.
            assertEquals(1, model.uiState.value.completedSets)
            val restored = WorkoutViewModel(FakeRoutineRepository(), repo,
                SavedStateHandle(mapOf("workout_state" to model.uiState.value)))
            assertEquals(WorkoutPhase.REST, restored.uiState.value.phase)
            assertEquals(1, restored.uiState.value.completedSets)
            repeat(3) { restored.skipRest(); restored.completeSet() }
            completedModel = restored
            restored.finish(); restored.finish()
        }
        compose.waitUntil(5000) { repo.sessions.value.size == 1 }
        compose.runOnIdle {
            assertEquals(60, repo.sessions.value.single().completedReps)
            WorkoutViewModel(FakeRoutineRepository(), repo,
                SavedStateHandle(mapOf("workout_state" to completedModel.uiState.value)))
        }
        compose.waitForIdle()
        compose.runOnIdle { assertEquals(1, repo.sessions.value.size) }
    }

    @Test fun saveFailureCanBeRetriedWithoutChangingSessionId() {
        val delegate = FakeWorkoutRepository()
        var fail = true
        val repository = object : com.jsbv99.gymmate.data.workout.WorkoutRepository {
            override val sessions = delegate.sessions
            override suspend fun save(session: com.jsbv99.gymmate.model.WorkoutSession) {
                if (fail) throw IllegalStateException("Fallo simulado")
                delegate.save(session)
            }
        }
        lateinit var model: WorkoutViewModel
        compose.runOnIdle {
            model = WorkoutViewModel(FakeRoutineRepository(), repository, SavedStateHandle())
            model.start(RoutineSamples.routines.last().id)
            repeat(4) { model.skipRest(); model.completeSet() }
            model.finish()
        }
        compose.waitUntil(5000) { model.uiState.value.error != null }
        val id = model.uiState.value.sessionId
        compose.runOnIdle { fail = false; model.finish() }
        compose.waitUntil(5000) { delegate.sessions.value.size == 1 }
        compose.runOnIdle { assertEquals(id, delegate.sessions.value.single().id) }
    }

    @Test fun summaryShowsSavedSessionAndExposesBothActions() {
        var home = 0
        var repeat = 0
        compose.setContent { GymMateTheme {
            WorkoutSummaryScreen({ home++ }, { repeat++ }, durationSeconds = 125,
                completedSets = 4, completedReps = 60, sessionSaved = true)
        } }
        compose.onNodeWithText("Duración: 02:05").assertExists()
        compose.onNodeWithText("Series completadas: 4").assertExists()
        compose.onNodeWithText("Repeticiones completadas: 60").assertExists()
        compose.onNodeWithText("Volver al inicio").performScrollTo().performClick()
        compose.onNodeWithText("Repetir entrenamiento").performScrollTo().performClick()
        compose.runOnIdle { assertEquals(1, home); assertEquals(1, repeat) }
    }
}
