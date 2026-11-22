package com.jsbv99.gymmate.ui.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ConfiguracionRoute(model: ConfiguracionViewModel, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val state by model.uiState.collectAsStateWithLifecycle()
    ConfiguracionScreen(
        onBack = onBack,
        state = state,
        onRemindersChange = model::setReminders,
        onSoundChange = model::setSound,
        onUnitChange = model::setUnit,
        onRestChange = model::setRestSeconds,
        onReset = model::reset,
        modifier = modifier
    )
}
