package com.jsbv99.gymmate

import android.app.Application
import com.jsbv99.gymmate.di.AppContainer

class GymMateApplication : Application() {
    val container = AppContainer()
}
