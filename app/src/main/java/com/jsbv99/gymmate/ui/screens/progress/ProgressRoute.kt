package com.jsbv99.gymmate.ui.screens.progress

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProgresoRoute(model: ProgressViewModel, onBack: () -> Unit, onHistory: () -> Unit, modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier) {
    val state by model.uiState.collectAsStateWithLifecycle()
    ProgresoScreen(onBack = onBack, onHistory = onHistory, state = state, modifier = modifier)
}
