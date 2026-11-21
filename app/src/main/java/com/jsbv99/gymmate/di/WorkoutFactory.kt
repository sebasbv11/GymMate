package com.jsbv99.gymmate.di

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jsbv99.gymmate.data.routines.RoutineRepository
import com.jsbv99.gymmate.data.workout.WorkoutRepository
import com.jsbv99.gymmate.ui.screens.workout.WorkoutViewModel

fun workoutFactory(routines: RoutineRepository, workouts: WorkoutRepository) = viewModelFactory {
    initializer { WorkoutViewModel(routines, workouts, createSavedStateHandle()) }
}
