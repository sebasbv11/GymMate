package com.jsbv99.gymmate.ui.screens.exercises

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsbv99.gymmate.ui.components.ScreenHeading
import com.jsbv99.gymmate.ui.theme.GymMateTheme

@Composable
fun ExerciseDetailScreen(exerciseId: String, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val exercise = exerciseSamples.find { it.id == exerciseId }
    Surface(modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(Modifier.safeDrawingPadding().verticalScroll(rememberScrollState()).padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            TextButton(onClick = onBack) { Text("← Volver") }
            if (exercise == null) {
                ScreenHeading("Ejercicio no encontrado", "Vuelve al catálogo y selecciona un ejercicio disponible.")
            } else {
                ScreenHeading(exercise.name, exercise.muscle)
                Text("Equipo: ${exercise.equipment}", style = MaterialTheme.typography.labelLarge)
                Text("Descripción", style = MaterialTheme.typography.titleLarge)
                Text(exercise.description, style = MaterialTheme.typography.bodyLarge)
                Text("Cómo realizarlo", style = MaterialTheme.typography.titleLarge)
                exercise.steps.forEachIndexed { index, step -> Text("${index + 1}. $step", style = MaterialTheme.typography.bodyLarge) }
                Card {
                    Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Tutorial en video — Próximamente", style = MaterialTheme.typography.titleMedium)
                        Text("Mientras tanto, sigue las indicaciones de técnica.")
                    }
                }
                Text("Utiliza una carga cómoda y detente si sientes dolor.", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Preview(showBackground = true, widthDp = 320, heightDp = 480, fontScale = 1.3f)
@Composable
private fun ExerciseDetailScreenPreview() { GymMateTheme { ExerciseDetailScreen("sentadilla", {}) } }
