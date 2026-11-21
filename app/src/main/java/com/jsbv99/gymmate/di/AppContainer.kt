package com.jsbv99.gymmate.di

import com.jsbv99.gymmate.data.auth.AuthRepository
import com.jsbv99.gymmate.data.auth.FakeAuthRepository
import com.jsbv99.gymmate.data.exercises.ExerciseRepository
import com.jsbv99.gymmate.data.exercises.FakeExerciseRepository
import com.jsbv99.gymmate.data.routines.RoutineRepository
import com.jsbv99.gymmate.data.routines.FakeRoutineRepository
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class AppContainer {
    val authRepository: AuthRepository = FakeAuthRepository()
    val exerciseRepository: ExerciseRepository = FakeExerciseRepository()
    val routineRepository: RoutineRepository = FakeRoutineRepository()

    inline fun <reified T : ViewModel> factory(crossinline create: () -> T): ViewModelProvider.Factory =
        viewModelFactory { initializer { create() } }
}

val LocalAppContainer = staticCompositionLocalOf<AppContainer> {
    error("AppContainer no disponible")
}
