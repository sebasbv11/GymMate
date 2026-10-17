package com.jsbv99.gymmate.ui.screens.exercises

import org.junit.Assert.*
import org.junit.Test

class ExerciseSamplesTest {
    @Test fun filtersCombineQueryAndMuscle() {
        assertEquals(listOf("remo"), filterExercises(" REMO ", "Espalda").map { it.id })
        assertTrue(filterExercises("remo", "Piernas").isEmpty())
        assertTrue(filterExercises("inexistente", "Todos").isEmpty())
        assertEquals(exerciseSamples.size, filterExercises("", "Todos").size)
    }
    @Test fun catalogHasUniqueIdsAndInstructions() {
        assertEquals(exerciseSamples.size, exerciseSamples.map { it.id }.distinct().size)
        assertTrue(exerciseSamples.all { it.description.isNotBlank() && it.steps.size >= 3 })
    }
}
