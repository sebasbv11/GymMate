package com.jsbv99.gymmate.model

import java.io.Serializable

data class Exercise(val id: String, val name: String, val muscle: String) : Serializable

data class RoutineExercise(val exercise: Exercise, val sets: Int = 3, val repetitions: Int = 12) : Serializable {
    init {
        require(sets in 1..20 && repetitions in 1..100)
    }
}

data class Routine(val id: String, val name: String, val exercises: List<RoutineExercise>) : Serializable {
    init {
        require(name.isNotBlank() && exercises.isNotEmpty())
        require(exercises.map { it.exercise.id }.distinct().size == exercises.size)
    }
}
