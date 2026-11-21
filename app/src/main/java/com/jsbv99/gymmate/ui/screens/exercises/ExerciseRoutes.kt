package com.jsbv99.gymmate.ui.screens.exercises

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ExercisesRoute(model: ExercisesViewModel, onBack: () -> Unit, onExerciseClick: (String) -> Unit) {
    val catalog by model.exercises.collectAsStateWithLifecycle()
    ExercisesScreen(onBack, onExerciseClick, catalog = catalog)
}

@Composable
fun ExerciseDetailRoute(model: ExercisesViewModel, exerciseId: String, onBack: () -> Unit) {
    val catalog by model.exercises.collectAsStateWithLifecycle()
    ExerciseDetailScreen(exerciseId, onBack, catalog = catalog)
}
