package com.jsbv99.gymmate.ui.screens.workout

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jsbv99.gymmate.ui.theme.GymMateTheme

/** Presentación pura: el propietario de la ruta gestiona el temporizador. */
@Composable
fun RestScreen(
    onSkip: () -> Unit,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
    remainingMillis: Long = 60_000,
    durationSeconds: Int = 60
) {
    val safeRemaining = remainingMillis.coerceIn(0, durationSeconds.coerceIn(1, 3600) * 1000L)
    BackHandler(onBack = onSkip)
    val seconds = ((safeRemaining + 999) / 1000).toInt()
    WorkoutLayout("Tiempo de descanso", modifier) {
        Text("Respira y recupera energía para la siguiente serie.")
        Text(workoutTime(seconds), style = MaterialTheme.typography.displayLarge)
        LinearProgressIndicator(
            progress = { safeRemaining / (durationSeconds.coerceIn(1, 3600) * 1000f) },
            modifier = Modifier.fillMaxWidth()
        )
        Text(if (seconds == 0) "¡Descanso completado! Puedes continuar." else "El descanso se pausa al salir de la aplicación.")
        Button(onClick = onFinish, enabled = seconds == 0, modifier = Modifier.fillMaxWidth()) { Text("Finalizar descanso") }
        OutlinedButton(onClick = onSkip, modifier = Modifier.fillMaxWidth()) { Text("Omitir descanso") }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Preview(showBackground = true, widthDp = 640, heightDp = 360, fontScale = 1.5f)
@Composable
private fun RestPreview() {
    GymMateTheme { RestScreen({}, {}) }
}
