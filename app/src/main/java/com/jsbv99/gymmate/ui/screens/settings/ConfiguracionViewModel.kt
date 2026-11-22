package com.jsbv99.gymmate.ui.screens.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.roundToInt

class ConfiguracionViewModel : ViewModel() {
    private val mutableUiState = MutableStateFlow(ConfiguracionUiState())
    val uiState = mutableUiState.asStateFlow()

    fun setReminders(enabled: Boolean) = mutableUiState.update { it.copy(reminders = enabled) }
    fun setSound(enabled: Boolean) = mutableUiState.update { it.copy(sound = enabled) }

    fun setUnit(unit: String) {
        if (unit == ConfiguracionUiState.KILOGRAMS || unit == ConfiguracionUiState.POUNDS) {
            mutableUiState.update { it.copy(unit = unit) }
        }
    }

    fun setRestSeconds(seconds: Float) {
        val bounded = seconds.roundToInt().coerceIn(30, 180)
        val snapped = 30 + (((bounded - 30) / 15f).roundToInt() * 15)
        mutableUiState.update { it.copy(restSeconds = snapped.coerceIn(30, 180)) }
    }

    fun reset() {
        mutableUiState.value = ConfiguracionUiState()
    }
}
