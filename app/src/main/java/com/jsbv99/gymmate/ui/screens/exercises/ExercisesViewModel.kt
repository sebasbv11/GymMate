package com.jsbv99.gymmate.ui.screens.exercises

import androidx.lifecycle.ViewModel
import com.jsbv99.gymmate.data.exercises.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(repository: ExerciseRepository) : ViewModel() {
    val exercises = repository.exercises
}
