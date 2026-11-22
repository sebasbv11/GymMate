package com.jsbv99.gymmate

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GymMateApplication : Application() {
    val themePreference by lazy { com.jsbv99.gymmate.ui.theme.ThemePreference(this) }
}
