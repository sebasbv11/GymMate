package com.jsbv99.gymmate.ui.screens.progress

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsbv99.gymmate.model.Exercise
import com.jsbv99.gymmate.model.Routine
import com.jsbv99.gymmate.model.RoutineExercise
import com.jsbv99.gymmate.model.WorkoutSession
import com.jsbv99.gymmate.ui.theme.GymMateTheme

@Composable
fun ProgresoScreen(
    onBack: () -> Unit,
    onHistory: () -> Unit,
    state: ProgressUiState,
    modifier: Modifier = Modifier
) {
    val sessionCount = state.sessionCount
    val sessionCountLabel = if (sessionCount == 1) "1 entrenamiento" else "$sessionCount entrenamientos"
    Surface(modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier.safeDrawingPadding().widthIn(max = 840.dp).fillMaxWidth()
                .verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(onClick = onBack) { Text("Volver") }
            Text("Tu progreso", style = MaterialTheme.typography.headlineMedium)
            Text("Estadísticas calculadas a partir de tus sesiones guardadas.")
            if (state.isEmpty) {
                Card {
                    Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Aún no hay datos de progreso", style = MaterialTheme.typography.titleLarge)
                        Text(sessionCountLabel, style = MaterialTheme.typography.headlineSmall)
                        Text("Finaliza un entrenamiento para empezar a consultar tus estadísticas.")
                    }
                }
            } else {
                Card {
                    Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Resumen de tus sesiones", style = MaterialTheme.typography.titleLarge)
                        Text(sessionCountLabel, style = MaterialTheme.typography.headlineSmall)
                        val totalSeconds = state.totalDurationSeconds
                        val minutes = totalSeconds / 60
                        val seconds = totalSeconds % 60
                        val duration = when {
                            minutes == 0L -> "$seconds s"
                            seconds == 0L -> "$minutes min"
                            else -> "$minutes min $seconds s"
                        }
                        Text("$duration de actividad")
                        Text("${state.completedSets} series completadas")
                        Text("${state.completedReps} repeticiones completadas")
                    }
                }
                Card {
                    Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Duración por sesión", style = MaterialTheme.typography.titleLarge)
                        state.sessions.forEach { session ->
                            val durationMinutes = session.durationSeconds / 60
                            val progress = if (state.longestSessionSeconds == 0) 0f
                                else session.durationSeconds.toFloat() / state.longestSessionSeconds
                            Text("${session.routine.name} · $durationMinutes min ${session.durationSeconds % 60} s")
                            LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
            OutlinedButton(onClick = onHistory, modifier = Modifier.fillMaxWidth()) { Text("Ver historial") }
        }
    }
}

/** Adaptador compatible con la llamada actual de AppNavigation. */
@Composable
fun ProgresoScreen(onBack: () -> Unit, onHistory: () -> Unit, modifier: Modifier = Modifier) {
    val container = com.jsbv99.gymmate.di.LocalAppContainer.current
    val model: ProgressViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = container.factory { ProgressViewModel(container.workoutRepository) }
    )
    ProgresoRoute(model = model, onBack = onBack, onHistory = onHistory, modifier = modifier)
}

@Preview(showBackground = true, widthDp = 320, heightDp = 480, fontScale = 1.3f)
@Preview(showBackground = true, widthDp = 840, heightDp = 900)
@Composable
private fun ProgresoPreview() {
    val routine = Routine(
        id = "preview-routine",
        name = "Rutina de piernas",
        exercises = listOf(
            RoutineExercise(Exercise("preview-squat", "Sentadilla", "Piernas"), sets = 2)
        )
    )
    val session = WorkoutSession(
        id = "preview-session",
        routine = routine,
        finishedAtMillis = 0L,
        durationSeconds = 45,
        completedSets = 2,
        completedReps = 24
    )
    GymMateTheme {
        ProgresoScreen(onBack = {}, onHistory = {}, state = ProgressUiState(listOf(session)))
    }
}
