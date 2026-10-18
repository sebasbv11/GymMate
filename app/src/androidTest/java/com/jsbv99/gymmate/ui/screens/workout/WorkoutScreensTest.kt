package com.jsbv99.gymmate.ui.screens.workout

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.junit4.StateRestorationTester
import com.jsbv99.gymmate.ui.theme.GymMateTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class WorkoutScreensTest {
    @get:Rule val compose = createComposeRule()

    @Test fun completesNineSetsAndReportsResultsOnce() {
        var rests = 0
        var summaries = 0
        compose.setContent {
            GymMateTheme {
                WorkoutScreen("demo", { rests++ }, { seconds, sets, reps ->
                    assertTrue(seconds >= 0)
                    assertEquals(9, sets)
                    assertEquals(108, reps)
                    summaries++
                }, {})
            }
        }
        repeat(9) { compose.onNodeWithText("Completar serie").performScrollTo().performClick() }
        compose.onNodeWithText("9 de 9 series completadas").assertExists()
        compose.onNodeWithText("Ver resumen").performScrollTo().performClick().assertIsNotEnabled()
        compose.runOnIdle { assertEquals(8, rests); assertEquals(1, summaries) }
    }

    @Test fun preservesProgressAndConfirmsExit() {
        var exits = 0
        val restoration = StateRestorationTester(compose)
        restoration.setContent { GymMateTheme { WorkoutScreen("demo", {}, { _, _, _ -> }, { exits++ }) } }
        compose.onNodeWithText("Completar serie").performScrollTo().performClick()
        restoration.emulateSavedInstanceStateRestore()
        compose.onNodeWithText("1 de 9 series completadas").assertExists()
        compose.onNodeWithText("Salir del entrenamiento").performScrollTo().performClick()
        compose.onNodeWithText("Continuar").performClick()
        compose.runOnIdle { assertEquals(0, exits) }
        compose.onNodeWithText("Salir del entrenamiento").performScrollTo().performClick()
        compose.onNodeWithText("Salir", substring = false).performClick()
        compose.runOnIdle { assertEquals(1, exits) }
    }

    @Test fun restReachesZeroAndFinishesOnlyOnRequest() {
        var finished = 0
        compose.setContent { GymMateTheme { RestScreen({}, { finished++ }, durationSeconds = 1) } }
        compose.onNodeWithText("Finalizar descanso").performScrollTo().assertIsNotEnabled()
        compose.waitUntil(timeoutMillis = 5000) {
            compose.onAllNodesWithText("00:00").fetchSemanticsNodes().isNotEmpty()
        }
        compose.runOnIdle { assertEquals(0, finished) }
        compose.onNodeWithText("Finalizar descanso").performClick().assertIsNotEnabled()
        compose.runOnIdle { assertEquals(1, finished) }
    }

    @Test fun skipIsSingleUseAndRemovalDoesNotNavigate() {
        var skipped = 0
        var finished = 0
        val visible = mutableStateOf(true)
        compose.setContent { GymMateTheme { if (visible.value) RestScreen({ skipped++ }, { finished++ }) } }
        compose.onNodeWithText("Omitir descanso").performScrollTo().performClick().assertIsNotEnabled()
        compose.runOnIdle { visible.value = false }
        compose.runOnIdle { assertEquals(1, skipped); assertEquals(0, finished) }
    }

    @Test fun summaryShowsSessionAndExposesBothActions() {
        var home = 0
        var repeat = 0
        compose.setContent {
            GymMateTheme { WorkoutSummaryScreen({ home++ }, { repeat++ }, durationSeconds = 125, completedSets = 9, completedReps = 108) }
        }
        compose.onNodeWithText("Duración: 02:05").assertExists()
        compose.onNodeWithText("Series completadas: 9").assertExists()
        compose.onNodeWithText("Repeticiones completadas: 108").assertExists()
        compose.onNodeWithText("Volver al inicio").performScrollTo().performClick()
        compose.onNodeWithText("Repetir entrenamiento").performScrollTo().performClick()
        compose.runOnIdle { assertEquals(1, home); assertEquals(1, repeat) }
    }
}
