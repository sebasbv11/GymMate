package com.jsbv99.gymmate.ui.screens.workout

import com.jsbv99.gymmate.data.RoutineSamples
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
    state: WorkoutUiState,
    onCompleteSet: () -> Unit,
    onFinish: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val routine = state.routine ?: return
    val exercises = routine.exercises
    val totalSets = state.totalSets
    val completedSets = state.completedSets
    val exerciseIndex = state.exerciseIndex
    val item = exercises[exerciseIndex]
    val previousSets = state.previousSets
    val currentSet = completedSets - previousSets + 1
    val repsPerSet = item.repetitions
    var exitDialog by rememberSaveable(state.sessionId) { mutableStateOf(false) }
    var leaving by rememberSaveable(state.sessionId) { mutableStateOf(false) }
    BackHandler { if (!state.saving) exitDialog = true }

    WorkoutLayout("Entrenamiento activo", modifier) {
        Text(routine.name, style = MaterialTheme.typography.titleMedium)
        Text("${exercises.size} ejercicios · $totalSets series")
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
            Button(onClick = onCompleteSet, enabled = !state.saving && state.phase == WorkoutPhase.ACTIVE,
                modifier = Modifier.fillMaxWidth()) { Text("Completar serie") }
        } else {
            Text("¡Completaste la rutina!", style = MaterialTheme.typography.titleLarge)
            Button(onClick = onFinish, enabled = !state.saving && state.phase == WorkoutPhase.ACTIVE,
                modifier = Modifier.fillMaxWidth()) { Text(if (state.saving) "Guardando sesión…" else "Ver resumen") }
        }
        Text("Duración: ${workoutTime(state.durationSeconds)}")
        state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        OutlinedButton(onClick = { exitDialog = true }, enabled = !state.saving, modifier = Modifier.fillMaxWidth()) { Text("Salir del entrenamiento") }
    }
    if (exitDialog) {
        AlertDialog(
            onDismissRequest = { exitDialog = false },
            title = { Text("¿Salir del entrenamiento?") },
            text = { Text("El avance de esta sesión se descartará y no aparecerá en el historial.") },
            confirmButton = { TextButton(onClick = { if (!leaving) { leaving = true; exitDialog = false; onExit() } }) { Text("Salir") } },
            dismissButton = { TextButton(onClick = { exitDialog = false }) { Text("Continuar") } }
        )
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Preview(showBackground = true, widthDp = 640, heightDp = 360, fontScale = 1.5f)
@Composable
private fun WorkoutPreview() {
    GymMateTheme { WorkoutScreen(WorkoutUiState(routine = RoutineSamples.routines.first()), {}, {}, {}) }
}
