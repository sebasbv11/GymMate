package com.jsbv99.gymmate.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jsbv99.gymmate.GymMateApplication
import com.jsbv99.gymmate.ui.theme.ThemeMode
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsbv99.gymmate.ui.theme.GymMateTheme
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlin.math.roundToInt

@Composable
fun ConfiguracionScreen(
    onBack: () -> Unit,
    state: ConfiguracionUiState,
    onRemindersChange: (Boolean) -> Unit,
    onSoundChange: (Boolean) -> Unit,
    onUnitChange: (String) -> Unit,
    onRestChange: (Float) -> Unit,
    onReset: () -> Unit,
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    onThemeChange: (ThemeMode) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var confirmReset by rememberSaveable { mutableStateOf(false) }
    var resetDone by rememberSaveable { mutableStateOf(false) }
    if (confirmReset) AlertDialog(
        onDismissRequest = { confirmReset = false },
        title = { Text("¿Restablecer opciones?") },
        text = { Text("Las opciones de demostración volverán a sus valores iniciales. La apariencia no cambiará.") },
        confirmButton = { TextButton(onClick = { onReset(); confirmReset = false; resetDone = true }) { Text("Restablecer") } },
        dismissButton = { TextButton(onClick = { confirmReset = false }) { Text("Cancelar") } }
    )
    if (resetDone) AlertDialog(
        onDismissRequest = { resetDone = false },
        title = { Text("Opciones restablecidas") },
        confirmButton = { TextButton(onClick = { resetDone = false }) { Text("Entendido") } }
    )
    Surface(modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier.safeDrawingPadding().widthIn(max = 840.dp).fillMaxWidth()
                .verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(onClick = onBack) { Text("Volver") }
            Text("Configuración", modifier = Modifier.semantics { heading() }, style = MaterialTheme.typography.headlineMedium)
            Text("Elige la apariencia de la aplicación. Las demás opciones son de demostración y no modifican tus entrenamientos.")
            Card {
                Column(Modifier.fillMaxWidth().padding(20.dp).selectableGroup()) {
                    Text("Apariencia", style = MaterialTheme.typography.titleLarge)
                    listOf(ThemeMode.SYSTEM to "Como el dispositivo", ThemeMode.LIGHT to "Claro", ThemeMode.DARK to "Oscuro").forEach { (mode, label) ->
                        Row(Modifier.fillMaxWidth().selectable(selected = themeMode == mode, role = Role.RadioButton,
                            onClick = { onThemeChange(mode) }).padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = themeMode == mode, onClick = null)
                            Text(label, Modifier.padding(start = 12.dp))
                        }
                    }
                }
            }
            Card {
                Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Preferencias", style = MaterialTheme.typography.titleLarge)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Recordatorios simulados", Modifier.weight(1f))
                        Switch(
                            modifier = Modifier.semantics { contentDescription = "Recordatorios simulados" },
                            checked = state.reminders,
                            onCheckedChange = onRemindersChange
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Sonido de descanso simulado", Modifier.weight(1f))
                        Switch(
                            modifier = Modifier.semantics { contentDescription = "Sonido de descanso simulado" },
                            checked = state.sound,
                            onCheckedChange = onSoundChange
                        )
                    }
                }
            }
            Card {
                Column(Modifier.fillMaxWidth().padding(20.dp).selectableGroup()) {
                    Text("Unidad de peso de ejemplo", style = MaterialTheme.typography.titleLarge)
                    listOf(ConfiguracionUiState.KILOGRAMS, ConfiguracionUiState.POUNDS).forEach { option ->
                        Row(
                            Modifier.fillMaxWidth().selectable(
                                selected = state.unit == option,
                                role = Role.RadioButton,
                                onClick = { onUnitChange(option) }
                            ).padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = state.unit == option, onClick = null)
                            Text(option, Modifier.padding(start = 12.dp))
                        }
                    }
                }
            }
            Card {
                Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Descanso de ejemplo", style = MaterialTheme.typography.titleLarge)
                    Text("${state.restSeconds} segundos")
                    Slider(
                        modifier = Modifier.semantics { contentDescription = "Descanso de ejemplo en segundos" },
                        value = state.restSeconds.toFloat(),
                        onValueChange = onRestChange,
                        valueRange = 30f..180f,
                        steps = 9
                    )
                    Text("Entre 30 y 180 segundos, en intervalos de 15 segundos.")
                }
            }
            OutlinedButton(onClick = { confirmReset = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Restablecer opciones")
            }
        }
    }
}

/** Adaptador compatible con la llamada actual de AppNavigation. */
@Composable
fun ConfiguracionScreen(onBack: () -> Unit, modifier: Modifier = Modifier) {
    val model: ConfiguracionViewModel = hiltViewModel()
    val theme = (LocalContext.current.applicationContext as GymMateApplication).themePreference
    val mode by theme.mode.collectAsStateWithLifecycle()
    ConfiguracionRoute(model = model, onBack = onBack, themeMode = mode, onThemeChange = theme::setMode, modifier = modifier)
}

@Preview(showBackground = true, widthDp = 320, heightDp = 480, fontScale = 1.3f)
@Preview(showBackground = true, widthDp = 840, heightDp = 900)
@Composable
private fun ConfiguracionPreview() {
    GymMateTheme {
        ConfiguracionScreen(
            onBack = {}, state = ConfiguracionUiState(), onRemindersChange = {}, onSoundChange = {},
            onUnitChange = {}, onRestChange = {}, onReset = {}
        )
    }
}
