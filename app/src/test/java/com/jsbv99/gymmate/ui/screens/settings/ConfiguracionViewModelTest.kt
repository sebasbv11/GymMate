package com.jsbv99.gymmate.ui.screens.settings

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ConfiguracionViewModelTest {
    @Test
    fun optionsCanBeChangedAndResetToTheirOriginalValues() {
        val model = ConfiguracionViewModel()
        model.setReminders(false)
        model.setSound(true)
        model.setUnit(ConfiguracionUiState.POUNDS)
        model.setRestSeconds(120f)

        assertFalse(model.uiState.value.reminders)
        assertTrue(model.uiState.value.sound)
        assertEquals(ConfiguracionUiState.POUNDS, model.uiState.value.unit)
        assertEquals(120, model.uiState.value.restSeconds)

        model.reset()

        assertEquals(ConfiguracionUiState(), model.uiState.value)
    }

    @Test
    fun restDurationIsBoundedAndSnappedToFifteenSecondSteps() {
        val model = ConfiguracionViewModel()
        model.setRestSeconds(178f)
        assertEquals(180, model.uiState.value.restSeconds)

        model.setRestSeconds(34f)
        assertEquals(30, model.uiState.value.restSeconds)
    }
}
