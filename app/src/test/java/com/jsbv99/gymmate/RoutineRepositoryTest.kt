package com.jsbv99.gymmate

import com.jsbv99.gymmate.data.exercises.FakeExerciseRepository
import com.jsbv99.gymmate.data.routines.FakeRoutineRepository
import com.jsbv99.gymmate.model.Exercise
import com.jsbv99.gymmate.model.Routine
import com.jsbv99.gymmate.model.RoutineExercise
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class RoutineRepositoryTest {
    @Test fun savesAndUpdatesSameRoutineWithoutLosingOthers() = runBlocking {
        val repository = FakeRoutineRepository(emptyList())
        val first = Routine("uno", "Primera", listOf(RoutineExercise(Exercise("a", "A", "Piernas"))))
        val second = first.copy(id = "dos", name = "Segunda")
        repository.save(first)
        repository.save(second)
        repository.save(first.copy(name = "Editada"))
        assertEquals(listOf("Editada", "Segunda"), repository.routines.value.map { it.name })
    }
    @Test fun routineCatalogIncludesEveryDetailedExercise() {
        val ids = FakeExerciseRepository().exercises.value.map { it.id }.toSet()
        val routineIds = com.jsbv99.gymmate.data.RoutineSamples.exercises.map { it.id }.toSet()
        assertEquals(ids, routineIds)
        assertEquals(6, ids.size)
    }
}
