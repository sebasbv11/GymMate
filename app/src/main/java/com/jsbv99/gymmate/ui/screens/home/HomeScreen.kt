package com.jsbv99.gymmate.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
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
    var confirmSignOut by rememberSaveable { mutableStateOf(false) }
    if (confirmSignOut) AlertDialog(
        onDismissRequest = { confirmSignOut = false },
        title = { Text("¿Cerrar sesión?") },
        text = { Text("Podrás volver a ingresar para consultar tus rutinas y entrenamientos.") },
        confirmButton = { TextButton(onClick = { confirmSignOut = false; onSignOut() }) { Text("Cerrar sesión") } },
        dismissButton = { TextButton(onClick = { confirmSignOut = false }) { Text("Cancelar") } }
    )
    Surface(modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(Modifier.safeDrawingPadding(), contentAlignment = Alignment.TopCenter) {
        Column(Modifier.widthIn(max = 560.dp).fillMaxWidth().verticalScroll(rememberScrollState()).padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ScreenHeading("¡Hola, $userName!", "Cada entrenamiento cuenta. Vamos a dar el siguiente paso.")
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Tu semana", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text("3 entrenamientos · 120 minutos", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text("Resumen de ejemplo · Datos simulados", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
            Text("¿Qué quieres hacer hoy?", style = MaterialTheme.typography.titleLarge)
            Button(onClick = onRoutines, modifier = Modifier.fillMaxWidth()) { Text("Ver mis rutinas") }
            OutlinedButton(onClick = onExercises, modifier = Modifier.fillMaxWidth()) { Text("Explorar ejercicios") }
            OutlinedButton(onClick = onHistory, modifier = Modifier.fillMaxWidth()) { Text("Historial de entrenamientos") }
            OutlinedButton(onClick = onProgress, modifier = Modifier.fillMaxWidth()) { Text("Consultar mi progreso") }
            TextButton(onClick = onSettings, modifier = Modifier.fillMaxWidth()) { Text("Configuración") }
            TextButton(onClick = { confirmSignOut = true }, modifier = Modifier.fillMaxWidth()) { Text("Cerrar sesión") }
            Text("A tu ritmo", style = MaterialTheme.typography.titleMedium)
            Text("Prioriza la técnica y realiza un calentamiento antes de comenzar.")
        }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Preview(showBackground = true, widthDp = 320, heightDp = 480, fontScale = 1.3f)
@Composable
private fun HomeScreenPreview() {
    GymMateTheme { HomeScreen({}, {}, {}, {}, {}) }
}
