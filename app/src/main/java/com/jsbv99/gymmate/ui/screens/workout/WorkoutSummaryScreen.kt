package com.jsbv99.gymmate.ui.screens.workout

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsbv99.gymmate.ui.theme.GymMateTheme

@Composable
fun WorkoutSummaryScreen(
    onHome: () -> Unit,
    onRepeat: () -> Unit,
    modifier: Modifier = Modifier,
    durationSeconds: Int = 1200,
    completedSets: Int = 9,
    completedReps: Int = 108
) {
    BackHandler(onBack = onHome)
    WorkoutLayout("¡Buen trabajo!", modifier) {
        Text("Resumen del entrenamiento", style = MaterialTheme.typography.titleLarge)
        Text("Rutina de demostración · Cuerpo completo")
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Duración: ${workoutTime(durationSeconds)}", style = MaterialTheme.typography.headlineSmall)
                Text("Series completadas: ${completedSets.coerceAtLeast(0)}")
                Text("Repeticiones completadas: ${completedReps.coerceAtLeast(0)}")
                Text("Calorías simuladas: 150 kcal")
            }
        }
        Text("Sesión local de demostración. Los resultados no se guardan en el historial.",
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Button(onClick = onHome, modifier = Modifier.fillMaxWidth()) { Text("Volver al inicio") }
        OutlinedButton(onClick = onRepeat, modifier = Modifier.fillMaxWidth()) { Text("Repetir entrenamiento") }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Preview(showBackground = true, widthDp = 640, heightDp = 360, fontScale = 1.5f)
@Composable
private fun SummaryPreview() {
    GymMateTheme { WorkoutSummaryScreen({}, {}) }
}
