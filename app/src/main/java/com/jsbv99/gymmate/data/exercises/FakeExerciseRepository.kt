package com.jsbv99.gymmate.data.exercises

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeExerciseRepository : ExerciseRepository {
    private val data = MutableStateFlow(exerciseSamples.toList())
    override val exercises = data.asStateFlow()
}
