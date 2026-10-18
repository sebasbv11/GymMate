package com.jsbv99.gymmate.ui.screens.workout

import android.os.SystemClock
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsbv99.gymmate.ui.theme.GymMateTheme

/** Local demo only; shared exercise/routine models remain the responsibility of the routines module. */
@Composable
fun WorkoutScreen(
    routineId: String,
    onRest: () -> Unit,
    onFinish: (durationSeconds: Int, completedSets: Int, completedReps: Int) -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val exercises = remember { listOf("Sentadillas", "Flexiones", "Abdominales") }
    val totalSets = 9
    val repsPerSet = 12
    var completedSets by rememberSaveable(routineId) { mutableIntStateOf(0) }
    var startedAt by rememberSaveable(routineId) { mutableLongStateOf(SystemClock.elapsedRealtime()) }
    var exitDialog by rememberSaveable(routineId) { mutableStateOf(false) }
    var finished by rememberSaveable(routineId) { mutableStateOf(false) }
    // The monotonic clock can reset after a device reboot; avoid a negative session duration.
    if (startedAt > SystemClock.elapsedRealtime()) startedAt = SystemClock.elapsedRealtime()
    val exerciseIndex = (completedSets / 3).coerceAtMost(exercises.lastIndex)
    val currentSet = completedSets % 3 + 1
    BackHandler { exitDialog = true }

    WorkoutLayout("Entrenamiento activo", modifier) {
        Text("Rutina de demostración · Cuerpo completo", style = MaterialTheme.typography.titleMedium)
        Text("Datos simulados · 3 ejercicios · 3 series de 12 repeticiones")
        LinearProgressIndicator(progress = { completedSets / totalSets.toFloat() }, modifier = Modifier.fillMaxWidth())
        Text("$completedSets de $totalSets series completadas")
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Ejercicio ${exerciseIndex + 1} de ${exercises.size}", style = MaterialTheme.typography.labelLarge)
                Text(exercises[exerciseIndex], style = MaterialTheme.typography.headlineSmall)
                Text(if (completedSets == totalSets) "Todas las series completadas" else "Serie $currentSet de 3 · $repsPerSet repeticiones")
                repeat(3) { set ->
                    val done = completedSets > exerciseIndex * 3 + set
                    Text("Serie ${set + 1}: ${if (done) "Completada" else "Pendiente"} · $repsPerSet repeticiones")
                }
            }
        }
        if (completedSets < totalSets) {
            Button(onClick = {
                completedSets++
                if (completedSets < totalSets) onRest()
            }, enabled = !finished, modifier = Modifier.fillMaxWidth()) { Text("Completar serie") }
        } else {
            Text("¡Completaste la rutina!", style = MaterialTheme.typography.titleLarge)
            Button(onClick = {
                if (!finished) {
                    finished = true
                    val seconds = ((SystemClock.elapsedRealtime() - startedAt).coerceAtLeast(0) / 1000).toInt()
                    onFinish(seconds, completedSets, completedSets * repsPerSet)
                }
            }, enabled = !finished, modifier = Modifier.fillMaxWidth()) { Text("Ver resumen") }
        }
        OutlinedButton(onClick = { exitDialog = true }, modifier = Modifier.fillMaxWidth()) { Text("Salir del entrenamiento") }
    }
    if (exitDialog) {
        AlertDialog(
            onDismissRequest = { exitDialog = false },
            title = { Text("¿Salir del entrenamiento?") },
            text = { Text("Esta sesión de demostración no se guardará.") },
            confirmButton = { TextButton(onClick = { exitDialog = false; onExit() }) { Text("Salir") } },
            dismissButton = { TextButton(onClick = { exitDialog = false }) { Text("Continuar") } }
        )
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Preview(showBackground = true, widthDp = 640, heightDp = 360, fontScale = 1.5f)
@Composable
private fun WorkoutPreview() {
    GymMateTheme { WorkoutScreen("demo", {}, { _, _, _ -> }, {}) }
}
