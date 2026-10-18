package com.jsbv99.gymmate.ui.screens.workout

import android.os.SystemClock
import com.jsbv99.gymmate.data.RoutineSamples
import com.jsbv99.gymmate.model.Routine
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsbv99.gymmate.ui.theme.GymMateTheme

/** Uses the selected local routine; progress survives the rest destination. */
@Composable
fun WorkoutScreen(
    routineId: String,
    onRest: () -> Unit,
    onFinish: (durationSeconds: Int, completedSets: Int, completedReps: Int) -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
    routine: Routine = RoutineSamples.routines.first()
) {
    val exercises = routine.exercises
    val totalSets = exercises.sumOf { it.sets }
    var completedSets by rememberSaveable(routineId) { mutableIntStateOf(0) }
    var startedAt by rememberSaveable(routineId) { mutableLongStateOf(SystemClock.elapsedRealtime()) }
    var exitDialog by rememberSaveable(routineId) { mutableStateOf(false) }
    var finished by rememberSaveable(routineId) { mutableStateOf(false) }
    // The monotonic clock can reset after a device reboot; avoid a negative session duration.
    if (startedAt > SystemClock.elapsedRealtime()) startedAt = SystemClock.elapsedRealtime()
    val exerciseIndex = exercises.indices.firstOrNull { index ->
        completedSets < exercises.take(index + 1).sumOf { it.sets }
    } ?: exercises.lastIndex
    val item = exercises[exerciseIndex]
    val previousSets = exercises.take(exerciseIndex).sumOf { it.sets }
    val currentSet = completedSets - previousSets + 1
    val repsPerSet = item.repetitions
    BackHandler { exitDialog = true }

    WorkoutLayout("Entrenamiento activo", modifier) {
        Text(routine.name, style = MaterialTheme.typography.titleMedium)
        Text("Datos simulados · ${exercises.size} ejercicios · $totalSets series")
        LinearProgressIndicator(progress = { completedSets / totalSets.toFloat() }, modifier = Modifier.fillMaxWidth())
        Text("$completedSets de $totalSets series completadas")
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Ejercicio ${exerciseIndex + 1} de ${exercises.size}", style = MaterialTheme.typography.labelLarge)
                Text(item.exercise.name, style = MaterialTheme.typography.headlineSmall)
                Text(if (completedSets == totalSets) "Todas las series completadas" else "Serie $currentSet de ${item.sets} · $repsPerSet repeticiones")
                repeat(item.sets) { set ->
                    val done = completedSets > previousSets + set
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
                    onFinish(seconds, completedSets, exercises.sumOf { it.sets * it.repetitions })
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
