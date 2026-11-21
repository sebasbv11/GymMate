package com.jsbv99.gymmate.data.exercises

import kotlinx.coroutines.flow.StateFlow

interface ExerciseRepository {
    val exercises: StateFlow<List<ExerciseSample>>
}
