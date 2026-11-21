package com.jsbv99.gymmate.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jsbv99.gymmate.data.exercises.ExerciseRepository
import com.jsbv99.gymmate.data.routines.RoutineRepository
import com.jsbv99.gymmate.ui.screens.exercises.ExercisesViewModel
import com.jsbv99.gymmate.ui.screens.routines.RoutinesViewModel

fun exercisesFactory(repository: ExerciseRepository): ViewModelProvider.Factory = viewModelFactory {
    initializer { ExercisesViewModel(repository) }
}
fun routinesFactory(repository: RoutineRepository): ViewModelProvider.Factory = viewModelFactory {
    initializer { RoutinesViewModel(repository) }
}
