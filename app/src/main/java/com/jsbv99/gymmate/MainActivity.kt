package com.jsbv99.gymmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jsbv99.gymmate.navigation.AppNavigation
import com.jsbv99.gymmate.ui.theme.GymMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GymMateTheme {
                AppNavigation()
            }
        }
    }
}
