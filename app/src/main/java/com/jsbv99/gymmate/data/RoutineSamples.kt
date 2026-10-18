package com.jsbv99.gymmate.data

import com.jsbv99.gymmate.model.Exercise
import com.jsbv99.gymmate.model.Routine
import com.jsbv99.gymmate.model.RoutineExercise

object RoutineSamples {
    // shortcut: proyección del catálogo de Sebastian; unificar al coordinar los modelos compartidos.
    val exercises = listOf(
        Exercise("sentadilla", "Sentadilla", "Piernas"),
        Exercise("flexiones", "Flexiones", "Pecho"),
        Exercise("remo", "Remo con mancuerna", "Espalda"),
        Exercise("press", "Press de hombros", "Hombros"),
        Exercise("curl", "Curl de bíceps", "Brazos")
    )
    val routines = listOf(
        Routine("cuerpo-completo", "Cuerpo completo", exercises.take(3).map { RoutineExercise(it) }),
        Routine("tren-superior", "Tren superior", exercises.drop(1).map { RoutineExercise(it, 3, 10) }),
        Routine("piernas", "Día de piernas", listOf(RoutineExercise(exercises.first(), 4, 15)))
    )
}
