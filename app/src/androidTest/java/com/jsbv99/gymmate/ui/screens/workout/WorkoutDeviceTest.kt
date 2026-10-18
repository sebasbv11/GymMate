package com.jsbv99.gymmate.ui.screens.workout

import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.jsbv99.gymmate.ui.theme.GymMateTheme
import java.io.File
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue

class WorkoutDeviceTest {
    @get:Rule val compose = createAndroidComposeRule<ComponentActivity>()

    @Test fun restPausesWhileActivityIsStopped() {
        compose.setContent { GymMateTheme { RestScreen({}, {}, durationSeconds = 30) } }
        compose.waitForIdle()
        fun remaining(): Int = compose.onNode(SemanticsMatcher("Cuenta regresiva") { node ->
            node.config.contains(SemanticsProperties.Text) &&
                node.config[SemanticsProperties.Text].any { it.text.matches(Regex("00:[0-9]{2}")) }
        })
            .fetchSemanticsNode().config[SemanticsProperties.Text].single().text.substringAfter(":").toInt()
        val before = remaining()
        compose.activityRule.scenario.moveToState(Lifecycle.State.CREATED)
        // Real elapsed time is intentional: the production timer uses the Android monotonic clock.
        Thread.sleep(2500)
        compose.activityRule.scenario.moveToState(Lifecycle.State.RESUMED)
        // Allow a boundary tick during lifecycle transitions, but not the background interval.
        assertTrue(remaining() >= before - 1)
    }

    @Test fun captureScreensAndReachButtonsWithLargeTextInCompactViewport() {
        val screen = mutableIntStateOf(0)
        val compact = mutableStateOf(false)
        compose.setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, if (compact.value) 1.5f else 1f)) {
                GymMateTheme {
                    Box(if (compact.value) Modifier.requiredSize(320.dp, 360.dp) else Modifier) {
                        when (screen.intValue) {
                            0 -> WorkoutScreen("demo", {}, { _, _, _ -> }, {})
                            1 -> RestScreen({}, {})
                            else -> WorkoutSummaryScreen({}, {})
                        }
                    }
                }
            }
        }
        val labels = listOf("Salir del entrenamiento", "Omitir descanso", "Repetir entrenamiento")
        for (small in listOf(false, true)) {
            for (index in 0..2) {
                compose.runOnIdle { compact.value = small; screen.intValue = index }
                compose.waitForIdle()
                saveScreenshot("screen-$index-${if (small) "compact" else "normal"}")
                compose.onNodeWithText(labels[index]).performScrollTo().assertIsDisplayed()
            }
        }
    }

    private fun saveScreenshot(name: String) {
        val bitmap = compose.onRoot().captureToImage().asAndroidBitmap()
        val directory = File(compose.activity.getExternalFilesDir(null), "workout-evidence").apply { mkdirs() }
        File(directory, "$name.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
    }
}
