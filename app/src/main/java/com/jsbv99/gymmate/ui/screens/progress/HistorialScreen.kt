package com.jsbv99.gymmate.ui.screens.progress

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsbv99.gymmate.model.WorkoutSession
import com.jsbv99.gymmate.ui.theme.GymMateTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistorialScreen(
    onBack: () -> Unit,
    onProgress: () -> Unit,
    modifier: Modifier = Modifier,
    sessions: List<WorkoutSession> = emptyList()
) {
    var expandedSession by rememberSaveable { mutableStateOf<String?>(null) }
    Surface(modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        LazyColumn(
            Modifier.safeDrawingPadding().widthIn(max = 840.dp).fillMaxWidth(),
            contentPadding = PaddingValues(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = onBack) { Text("Volver") }
                    Text("Historial", style = MaterialTheme.typography.headlineMedium)
                    Text("Cada sesión cuenta. Consulta tus entrenamientos anteriores.")
                    Text("Sesiones guardadas en esta sesión de la aplicación", style = MaterialTheme.typography.labelLarge)
                    OutlinedButton(onClick = onProgress) { Text("Consultar progreso") }
                }
            }
            if (sessions.isEmpty()) {
                item {
                    Card {
                        Column(
                            Modifier.fillMaxWidth().padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Aún no hay entrenamientos", style = MaterialTheme.typography.titleLarge)
                            Text("Cuando finalices una sesión, aparecerá aquí con sus resultados.")
                        }
                    }
                }
            } else {
                items(sessions, key = { it.id }) { session ->
                    val expanded = expandedSession == session.id
                    Card(onClick = { expandedSession = if (expanded) null else session.id }) {
                        Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                SimpleDateFormat("dd MMM yyyy · HH:mm", Locale.forLanguageTag("es-EC"))
                                    .format(Date(session.finishedAtMillis)),
                                style = MaterialTheme.typography.labelLarge
                            )
                            Text(session.routine.name, style = MaterialTheme.typography.titleLarge)
                            Text("${session.durationSeconds / 60} min ${session.durationSeconds % 60} s · ${session.completedSets} series")
                            Text("${session.completedReps} repeticiones")
                            Text(if (expanded) "Ocultar ejercicios" else "Ver ejercicios", color = MaterialTheme.colorScheme.primary)
                            if (expanded) Text(session.routine.exercises.joinToString { it.exercise.name })
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 480, fontScale = 1.3f)
@Preview(showBackground = true, widthDp = 840, heightDp = 900)
@Composable
private fun HistorialPreview() { GymMateTheme { HistorialScreen({}, {}) } }
