package com.jsbv99.gymmate.ui.screens.settings

data class ConfiguracionUiState(
    val reminders: Boolean = true,
    val sound: Boolean = false,
    val unit: String = KILOGRAMS,
    val restSeconds: Int = 60
) {
    companion object {
        const val KILOGRAMS = "Kilogramos (kg)"
        const val POUNDS = "Libras (lb)"
    }
}
