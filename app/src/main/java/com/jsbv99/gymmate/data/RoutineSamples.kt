package com.jsbv99.gymmate.data

import com.jsbv99.gymmate.model.Exercise
import com.jsbv99.gymmate.model.Routine
import com.jsbv99.gymmate.model.RoutineExercise

object RoutineSamples {
    // shortcut: proyección del catálogo de Sebastian; unificar al coordinar los modelos compartidos.
    val exercises = com.jsbv99.gymmate.data.exercises.exerciseSamples.map {
        Exercise(it.id, it.name, it.muscle)
    }
    val routines = listOf(
        Routine("cuerpo-completo", "Cuerpo completo", exercises.take(3).map { RoutineExercise(it) }),
        Routine("tren-superior", "Tren superior", exercises.filter { it.id in listOf("flexiones", "remo", "press", "curl") }.map { RoutineExercise(it, 3, 10) }),
        Routine("piernas", "Día de piernas", listOf(RoutineExercise(exercises.first(), 4, 15)))
    )
}
