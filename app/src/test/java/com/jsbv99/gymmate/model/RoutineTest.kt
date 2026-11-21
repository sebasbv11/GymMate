package com.jsbv99.gymmate.model

import com.jsbv99.gymmate.data.RoutineSamples
import com.jsbv99.gymmate.ui.screens.exercises.exerciseSamples
import org.junit.Assert.*
import org.junit.Test
import java.io.*

class RoutineTest {
    @Test fun sessionRoutinesCanBeSerializedAndRestored() {
        val routines = arrayListOf(Routine("custom", "Rutina editada",
            listOf(RoutineExercise(RoutineSamples.exercises.first(), 2, 8))))
        val bytes = ByteArrayOutputStream().also { output ->
            ObjectOutputStream(output).use { it.writeObject(routines) }
        }.toByteArray()
        val restored = ObjectInputStream(ByteArrayInputStream(bytes)).use { it.readObject() }
        assertEquals(routines, restored)
    }

    @Test fun routineExercisesMatchTheIntegratedCatalog() {
        RoutineSamples.exercises.forEach { exercise ->
            val integrated = exerciseSamples.single { it.id == exercise.id }
            assertEquals(integrated.name, exercise.name)
            assertEquals(integrated.muscle, exercise.muscle)
        }
    }
    @Test fun samplesHaveUniqueIdsAndConsistentExercises() {
        val catalog = RoutineSamples.exercises
        assertEquals(catalog.size, catalog.map { it.id }.distinct().size)
        assertEquals(RoutineSamples.routines.size, RoutineSamples.routines.map { it.id }.distinct().size)
        RoutineSamples.routines.forEach { routine ->
            assertTrue(routine.exercises.all { it.exercise in catalog })
            assertTrue(routine.exercises.all { it.sets > 0 && it.repetitions > 0 })
        }
    }
    @Test fun invalidRoutineValuesAreRejected() {
        val exercise = RoutineSamples.exercises.first()
        listOf(0 to 12, 21 to 12, 3 to 0, 3 to 101).forEach { (sets, reps) ->
            assertThrows(IllegalArgumentException::class.java) { RoutineExercise(exercise, sets, reps) }
        }
        val item = RoutineExercise(exercise)
        assertThrows(IllegalArgumentException::class.java) { Routine("id", " ", listOf(item)) }
        assertThrows(IllegalArgumentException::class.java) { Routine("id", "Rutina", emptyList()) }
        assertThrows(IllegalArgumentException::class.java) { Routine("id", "Rutina", listOf(item, item)) }
        assertEquals(100, RoutineExercise(exercise, 20, 100).repetitions)
    }
}
