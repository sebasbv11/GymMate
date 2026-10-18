package com.jsbv99.gymmate.ui.screens.home

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
fun HomeScreen(
    onExercises: () -> Unit, onRoutines: () -> Unit, onHistory: () -> Unit,
    onProgress: () -> Unit, onSettings: () -> Unit, modifier: Modifier = Modifier,
    userName: String = "deportista",
    onSignOut: () -> Unit = {}
) {
    Surface(modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(Modifier.safeDrawingPadding().verticalScroll(rememberScrollState()).padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ScreenHeading("¡Hola, $userName!", "Cada entrenamiento cuenta. Vamos a dar el siguiente paso.")
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Tu semana", style = MaterialTheme.typography.titleLarge)
                    Text("3 entrenamientos · 120 minutos", style = MaterialTheme.typography.bodyLarge)
                    Text("Resumen de ejemplo · Datos simulados", style = MaterialTheme.typography.labelMedium)
                }
            }
            Text("¿Qué quieres hacer hoy?", style = MaterialTheme.typography.titleLarge)
            Button(onClick = onRoutines, modifier = Modifier.fillMaxWidth()) { Text("Ver mis rutinas") }
            OutlinedButton(onClick = onExercises, modifier = Modifier.fillMaxWidth()) { Text("Explorar ejercicios") }
            OutlinedButton(onClick = onHistory, modifier = Modifier.fillMaxWidth()) { Text("Historial de entrenamientos") }
            OutlinedButton(onClick = onProgress, modifier = Modifier.fillMaxWidth()) { Text("Consultar mi progreso") }
            TextButton(onClick = onSettings, modifier = Modifier.fillMaxWidth()) { Text("Configuración") }
            TextButton(onClick = onSignOut, modifier = Modifier.fillMaxWidth()) { Text("Cerrar sesión") }
            Text("A tu ritmo", style = MaterialTheme.typography.titleMedium)
            Text("Prioriza la técnica y realiza un calentamiento antes de comenzar.")
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Preview(showBackground = true, widthDp = 320, heightDp = 480, fontScale = 1.3f)
@Composable
private fun HomeScreenPreview() {
    GymMateTheme { HomeScreen({}, {}, {}, {}, {}) }
}
