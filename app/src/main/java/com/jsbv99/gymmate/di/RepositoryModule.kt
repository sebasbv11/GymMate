package com.jsbv99.gymmate.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jsbv99.gymmate.data.auth.AuthRepository
import com.jsbv99.gymmate.data.auth.FirebaseAuthRepository
import com.jsbv99.gymmate.data.exercises.ExerciseRepository
import com.jsbv99.gymmate.data.exercises.FakeExerciseRepository
import com.jsbv99.gymmate.data.firebase.CloudRoutineRepository
import com.jsbv99.gymmate.data.firebase.CloudWorkoutRepository
import com.jsbv99.gymmate.data.routines.RoutineRepository
import com.jsbv99.gymmate.data.workout.WorkoutRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides @Singleton fun auth(): FirebaseAuth = FirebaseAuth.getInstance()
    @Provides @Singleton fun firestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
    @Provides @Singleton fun authRepository(auth: FirebaseAuth): AuthRepository = FirebaseAuthRepository(auth)
    @Provides @Singleton fun exercises(): ExerciseRepository = FakeExerciseRepository()
    @Provides @Singleton fun routines(auth: FirebaseAuth, db: FirebaseFirestore): RoutineRepository = CloudRoutineRepository(auth, db)
    @Provides @Singleton fun workouts(auth: FirebaseAuth, db: FirebaseFirestore): WorkoutRepository = CloudWorkoutRepository(auth, db)
}
