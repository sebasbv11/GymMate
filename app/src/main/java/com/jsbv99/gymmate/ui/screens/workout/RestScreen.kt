package com.jsbv99.gymmate.ui.screens.workout

import android.os.SystemClock
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jsbv99.gymmate.ui.theme.GymMateTheme
import kotlinx.coroutines.delay

/** Pauses outside RESUMED; leaving composition cancels the coroutine. Zero never navigates automatically. */
@Composable
fun RestScreen(
    onSkip: () -> Unit,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
    durationSeconds: Int = 60
) {
    val duration = durationSeconds.coerceIn(1, 3600)
    var remainingMillis by rememberSaveable(duration) { mutableLongStateOf(duration * 1000L) }
    var leaving by rememberSaveable(duration) { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner, duration, leaving) {
        if (!leaving) lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            var lastTick = SystemClock.elapsedRealtime()
            while (remainingMillis > 0) {
                delay(100)
                val now = SystemClock.elapsedRealtime()
                remainingMillis = (remainingMillis - (now - lastTick)).coerceAtLeast(0)
                lastTick = now
            }
        }
    }
    val skip = {
        if (!leaving) { leaving = true; onSkip() }
    }
    BackHandler(onBack = skip)
    val seconds = ((remainingMillis + 999) / 1000).toInt()
    WorkoutLayout("Tiempo de descanso", modifier) {
        Text("Respira y recupera energía para la siguiente serie.")
        Text(workoutTime(seconds), style = MaterialTheme.typography.displayLarge)
        LinearProgressIndicator(
            progress = { remainingMillis / (duration * 1000f) },
            modifier = Modifier.fillMaxWidth()
        )
        Text(if (seconds == 0) "¡Descanso completado! Puedes continuar." else "El descanso se pausa al salir de la aplicación.")
        Button(onClick = {
            if (!leaving) { leaving = true; onFinish() }
        }, enabled = seconds == 0 && !leaving, modifier = Modifier.fillMaxWidth()) { Text("Finalizar descanso") }
        OutlinedButton(onClick = skip, enabled = !leaving, modifier = Modifier.fillMaxWidth()) { Text("Omitir descanso") }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Preview(showBackground = true, widthDp = 640, heightDp = 360, fontScale = 1.5f)
@Composable
private fun RestPreview() {
    GymMateTheme { RestScreen({}, {}) }
}
