package com.jsbv99.gymmate.di

import com.jsbv99.gymmate.data.auth.AuthRepository
import com.jsbv99.gymmate.data.exercises.ExerciseRepository
import com.jsbv99.gymmate.data.exercises.FakeExerciseRepository
import com.jsbv99.gymmate.data.routines.RoutineRepository
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class AppContainer {
    private val firebaseAuth by lazy { com.google.firebase.auth.FirebaseAuth.getInstance() }
    private val firestore by lazy { com.google.firebase.firestore.FirebaseFirestore.getInstance() }
    val workoutRepository: com.jsbv99.gymmate.data.workout.WorkoutRepository by lazy {
        com.jsbv99.gymmate.data.firebase.CloudWorkoutRepository(firebaseAuth, firestore)
    }
    val authRepository: AuthRepository by lazy { com.jsbv99.gymmate.data.auth.FirebaseAuthRepository(firebaseAuth) }
    val exerciseRepository: ExerciseRepository = FakeExerciseRepository()
    val routineRepository: RoutineRepository by lazy { com.jsbv99.gymmate.data.firebase.CloudRoutineRepository(firebaseAuth, firestore) }

    inline fun <reified T : ViewModel> factory(crossinline create: () -> T): ViewModelProvider.Factory =
        viewModelFactory { initializer { create() } }
}

val LocalAppContainer = staticCompositionLocalOf<AppContainer> {
    error("AppContainer no disponible")
}
