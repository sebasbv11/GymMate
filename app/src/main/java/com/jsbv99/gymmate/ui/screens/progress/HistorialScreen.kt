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
import com.jsbv99.gymmate.ui.theme.GymMateTheme

@Composable
fun HistorialScreen(onBack: () -> Unit, onProgress: () -> Unit, modifier: Modifier = Modifier) {
    var expandedSession by rememberSaveable { mutableStateOf<String?>(null) }
    // Ejemplos independientes del registro real de entrenamientos.
    val sessions = remember { listOf(
        listOf("16 oct 2026", "Cuerpo completo", "40 min", "12 series", "Sentadillas, flexiones y plancha"),
        listOf("14 oct 2026", "Tren superior", "45 min", "15 series", "Remo, press de pecho y curl de bíceps"),
        listOf("12 oct 2026", "Piernas", "35 min", "12 series", "Sentadillas, zancadas y elevación de talones"),
        listOf("9 oct 2026", "Cuerpo completo", "40 min", "12 series", "Sentadillas, flexiones y plancha"),
        listOf("7 oct 2026", "Tren superior", "35 min", "12 series", "Remo, press de pecho y curl de bíceps"),
        listOf("5 oct 2026", "Piernas", "30 min", "9 series", "Sentadillas, zancadas y elevación de talones")
    ) }
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
                    Text("Datos simulados · Octubre de 2026", style = MaterialTheme.typography.labelLarge)
                    OutlinedButton(onClick = onProgress) { Text("Consultar progreso") }
                }
            }
            items(sessions, key = { it[0] }) { session ->
                val expanded = expandedSession == session[0]
                Card(onClick = { expandedSession = if (expanded) null else session[0] }) {
                    Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(session[0], style = MaterialTheme.typography.labelLarge)
                        Text(session[1], style = MaterialTheme.typography.titleLarge)
                        Text("${session[2]} · ${session[3]}")
                        Text(if (expanded) "Ocultar ejercicios" else "Ver ejercicios", color = MaterialTheme.colorScheme.primary)
                        if (expanded) Text(session[4])
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
