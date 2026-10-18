package com.jsbv99.gymmate.ui.screens.progress

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsbv99.gymmate.ui.theme.GymMateTheme

@Composable
fun ProgresoScreen(onBack: () -> Unit, onHistory: () -> Unit, modifier: Modifier = Modifier) {
    var currentWeek by rememberSaveable { mutableStateOf(true) }
    val minutes = if (currentWeek) listOf(35, 45, 40) else listOf(30, 35, 40)
    Surface(modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier.safeDrawingPadding().widthIn(max = 840.dp).fillMaxWidth()
                .verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(onClick = onBack) { Text("Volver") }
            Text("Tu progreso", style = MaterialTheme.typography.headlineMedium)
            Text("Estadísticas simuladas · Octubre de 2026")
            Column {
                FilterChip(selected = currentWeek, onClick = { currentWeek = true }, label = { Text("Semana del 12 al 18") })
                FilterChip(selected = !currentWeek, onClick = { currentWeek = false }, label = { Text("Semana del 5 al 11") })
            }
            Card {
                Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Resumen semanal", style = MaterialTheme.typography.titleLarge)
                    Text("3 entrenamientos", style = MaterialTheme.typography.headlineSmall)
                    Text("${minutes.sum()} minutos de actividad")
                    Text(if (currentWeek) "39 series completadas · 15 minutos más que la semana anterior" else "33 series completadas")
                    Text("Meta de ejemplo: 3 de 4 sesiones (75 %)")
                    LinearProgressIndicator(progress = { 0.75f }, modifier = Modifier.fillMaxWidth())
                }
            }
            Card {
                Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Minutos por sesión", style = MaterialTheme.typography.titleLarge)
                    Text("Escala: 0 a 60 minutos. Cada barra representa una sesión.")
                    minutes.forEachIndexed { index, value ->
                        Text("Sesión ${index + 1}: $value minutos")
                        LinearProgressIndicator(progress = { value / 60f }, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
            Text("La constancia suma", style = MaterialTheme.typography.titleLarge)
            Text("Compara tus sesiones a tu ritmo. Estos indicadores son ejemplos y no se actualizan con tus entrenamientos.")
            OutlinedButton(onClick = onHistory, modifier = Modifier.fillMaxWidth()) { Text("Ver historial") }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 480, fontScale = 1.3f)
@Preview(showBackground = true, widthDp = 840, heightDp = 900)
@Composable
private fun ProgresoPreview() { GymMateTheme { ProgresoScreen({}, {}) } }
