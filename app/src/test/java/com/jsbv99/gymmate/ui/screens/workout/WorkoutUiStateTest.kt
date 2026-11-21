package com.jsbv99.gymmate.ui.screens.workout

import com.jsbv99.gymmate.model.*
import org.junit.Assert.*
import org.junit.Test
import java.io.*

class WorkoutUiStateTest {
    private val routine = Routine("mixed", "Mixta", listOf(
        RoutineExercise(Exercise("a", "Uno", "Piernas"), 2, 8),
        RoutineExercise(Exercise("b", "Dos", "Brazos"), 1, 15)))

    @Test fun mixedSetsUseCorrectExerciseAndCompletedRepetitions() {
        val state = WorkoutUiState(routine = routine)
        assertEquals(3, state.totalSets)
        assertEquals(0, state.exerciseIndex)
        assertEquals(8, state.copy(completedSets = 1).completedReps)
        assertEquals(1, state.copy(completedSets = 2).exerciseIndex)
        assertEquals(16, state.copy(completedSets = 2).completedReps)
        assertTrue(state.copy(completedSets = 3).complete)
        assertEquals(31, state.copy(completedSets = 3).completedReps)
        assertEquals(1, state.copy(completedSets = 3).exerciseIndex)
    }

    @Test fun restSnapshotCanBeSerializedForAndroidRestoration() {
        val state = WorkoutUiState(routine = routine, completedSets = 1,
            remainingRestMillis = 12_345, phase = WorkoutPhase.REST, sessionId = "same")
        val bytes = ByteArrayOutputStream().also { ObjectOutputStream(it).use { out -> out.writeObject(state) } }.toByteArray()
        val restored = ObjectInputStream(ByteArrayInputStream(bytes)).use { it.readObject() }
        assertEquals(state, restored)
    }
}
