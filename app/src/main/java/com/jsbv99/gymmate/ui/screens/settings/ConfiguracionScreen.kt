package com.jsbv99.gymmate.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsbv99.gymmate.ui.theme.GymMateTheme
import kotlin.math.roundToInt

@Composable
fun ConfiguracionScreen(onBack: () -> Unit, modifier: Modifier = Modifier) {
    var reminders by rememberSaveable { mutableStateOf(true) }
    var sound by rememberSaveable { mutableStateOf(false) }
    var unit by rememberSaveable { mutableStateOf("Kilogramos (kg)") }
    var rest by rememberSaveable { mutableFloatStateOf(60f) }
    Surface(modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier.safeDrawingPadding().widthIn(max = 840.dp).fillMaxWidth()
                .verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(onClick = onBack) { Text("Volver") }
            Text("Configuración", style = MaterialTheme.typography.headlineMedium)
            Text("Opciones de demostración. Se conservan durante esta pantalla; no programan avisos ni modifican entrenamientos.")
            Card {
                Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Preferencias", style = MaterialTheme.typography.titleLarge)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Recordatorios simulados", Modifier.weight(1f))
                        Switch(modifier = Modifier.semantics { contentDescription = "Recordatorios simulados" }, checked = reminders, onCheckedChange = { reminders = it })
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Sonido de descanso simulado", Modifier.weight(1f))
                        Switch(modifier = Modifier.semantics { contentDescription = "Sonido de descanso simulado" }, checked = sound, onCheckedChange = { sound = it })
                    }
                }
            }
            Card {
                Column(Modifier.fillMaxWidth().padding(20.dp).selectableGroup()) {
                    Text("Unidad de peso de ejemplo", style = MaterialTheme.typography.titleLarge)
                    listOf("Kilogramos (kg)", "Libras (lb)").forEach { option ->
                        Row(
                            Modifier.fillMaxWidth().selectable(selected = unit == option, role = Role.RadioButton, onClick = { unit = option }).padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = unit == option, onClick = null)
                            Text(option, Modifier.padding(start = 12.dp))
                        }
                    }
                }
            }
            Card {
                Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Descanso de ejemplo", style = MaterialTheme.typography.titleLarge)
                    Text("${rest.roundToInt()} segundos")
                    Slider(modifier = Modifier.semantics { contentDescription = "Descanso de ejemplo en segundos" }, value = rest, onValueChange = { rest = it }, valueRange = 30f..180f, steps = 9)
                    Text("Entre 30 y 180 segundos, en intervalos de 15 segundos.")
                }
            }
            OutlinedButton(onClick = { reminders = true; sound = false; unit = "Kilogramos (kg)"; rest = 60f }, modifier = Modifier.fillMaxWidth()) {
                Text("Restablecer opciones")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 480, fontScale = 1.3f)
@Preview(showBackground = true, widthDp = 840, heightDp = 900)
@Composable
private fun ConfiguracionPreview() { GymMateTheme { ConfiguracionScreen({}) } }

