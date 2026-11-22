package com.jsbv99.gymmate.ui.screens.progress

import com.jsbv99.gymmate.model.Exercise
import com.jsbv99.gymmate.model.Routine
import com.jsbv99.gymmate.model.RoutineExercise
import com.jsbv99.gymmate.model.WorkoutSession
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ProgressUiStateTest {
    @Test
    fun emptySessionsProduceAnEmptyStateAndZeroTotals() {
        val state = ProgressUiState()

        assertTrue(state.isEmpty)
        assertEquals(0, state.sessionCount)
        assertEquals(0L, state.totalDurationSeconds)
        assertEquals(0, state.completedSets)
        assertEquals(0, state.completedReps)
        assertEquals(0, state.longestSessionSeconds)
    }

    @Test
    fun totalsAreCalculatedFromRecordedSessions() {
        val routine = Routine("routine", "Fuerza", listOf(RoutineExercise(Exercise("squat", "Sentadilla", "Piernas"))))
        val sessions = listOf(
            WorkoutSession("one", routine, 100, 1_800, 4, 40),
            WorkoutSession("two", routine, 200, 2_400, 5, 50)
        )

        val state = ProgressUiState(sessions)

        assertFalse(state.isEmpty)
        assertEquals(2, state.sessionCount)
        assertEquals(4_200L, state.totalDurationSeconds)
        assertEquals(9, state.completedSets)
        assertEquals(90, state.completedReps)
        assertEquals(2_400, state.longestSessionSeconds)
    }
}
