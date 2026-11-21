package com.jsbv99.gymmate.ui.screens.workout

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle

@Composable
private fun WorkoutTimer(model: WorkoutViewModel) {
    val owner = LocalLifecycleOwner.current
    LaunchedEffect(model, owner) {
        owner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) { model.tickWhileVisible() }
    }
}

@Composable
fun WorkoutRoute(model: WorkoutViewModel, onExit: () -> Unit) {
    val state by model.uiState.collectAsStateWithLifecycle()
    WorkoutTimer(model)
    WorkoutScreen(state, model::completeSet, model::finish, onExit)
}

@Composable
fun RestRoute(model: WorkoutViewModel) {
    val state by model.uiState.collectAsStateWithLifecycle()
    WorkoutTimer(model)
    RestScreen(model::skipRest, model::finishRest, remainingMillis = state.remainingRestMillis)
}

@Composable
fun WorkoutSummaryRoute(model: WorkoutViewModel, onHome: () -> Unit, onRepeat: () -> Unit) {
    val state by model.uiState.collectAsStateWithLifecycle()
    val result = state.result ?: return
    var leaving by rememberSaveable(result.id) { mutableStateOf(false) }
    WorkoutSummaryScreen(
        onHome = { if (!leaving) { leaving = true; onHome() } },
        onRepeat = { if (!leaving) { leaving = true; onRepeat() } }, durationSeconds = result.durationSeconds,
        completedSets = result.completedSets, completedReps = result.completedReps,
        routineName = result.routine.name, sessionSaved = true, error = state.error)
}
