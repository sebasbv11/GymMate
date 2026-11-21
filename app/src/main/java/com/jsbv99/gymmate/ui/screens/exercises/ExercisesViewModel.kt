package com.jsbv99.gymmate.ui.screens.exercises

import androidx.lifecycle.ViewModel
import com.jsbv99.gymmate.data.exercises.ExerciseRepository

class ExercisesViewModel(repository: ExerciseRepository) : ViewModel() {
    val exercises = repository.exercises
}
