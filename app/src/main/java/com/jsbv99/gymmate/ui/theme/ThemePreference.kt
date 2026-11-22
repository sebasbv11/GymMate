package com.jsbv99.gymmate.ui.theme

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class ThemeMode { SYSTEM, LIGHT, DARK }

class ThemePreference(context: Context) {
    private val prefs = context.getSharedPreferences("appearance", Context.MODE_PRIVATE)
    private val current = MutableStateFlow(
        runCatching { ThemeMode.valueOf(prefs.getString("theme", ThemeMode.SYSTEM.name)!!) }.getOrDefault(ThemeMode.SYSTEM)
    )
    val mode = current.asStateFlow()
    fun setMode(mode: ThemeMode) {
        current.value = mode
        prefs.edit().putString("theme", mode.name).apply()
    }
}
