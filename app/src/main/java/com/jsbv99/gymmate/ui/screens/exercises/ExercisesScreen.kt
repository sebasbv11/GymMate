package com.jsbv99.gymmate.ui.screens.exercises

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsbv99.gymmate.ui.components.*
import com.jsbv99.gymmate.ui.theme.GymMateTheme

@Composable
fun ExercisesScreen(onBack: () -> Unit, onExerciseClick: (String) -> Unit, modifier: Modifier = Modifier) {
    var query by rememberSaveable { mutableStateOf("") }
    var muscle by rememberSaveable { mutableStateOf("Todos") }
    val exercises = filterExercises(query, muscle)
    Surface(modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        LazyColumn(Modifier.safeDrawingPadding().imePadding(), contentPadding = PaddingValues(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item { TextButton(onClick = onBack) { Text("← Volver") } }
            item { ScreenHeading("Ejercicios", "Encuentra el movimiento para tu próximo entrenamiento.") }
            item {
                OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Buscar por nombre o músculo") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            }
            item {
                Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    (listOf("Todos") + exerciseSamples.map { it.muscle }.distinct()).forEach { group ->
                        FilterChip(selected = muscle == group, onClick = { muscle = group }, label = { Text(group) })
                    }
                }
            }
            item { Text("${exercises.size} ejercicios · Catálogo de ejemplo", style = MaterialTheme.typography.labelLarge) }
            if (exercises.isEmpty()) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("No encontramos ejercicios con esos filtros.")
                        TextButton(onClick = { query = ""; muscle = "Todos" }) { Text("Limpiar filtros") }
                    }
                }
            }
            items(exercises, key = { it.id }) { exercise ->
                ExerciseCard(exercise.name, exercise.muscle, exercise.equipment, { onExerciseClick(exercise.id) })
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Preview(showBackground = true, widthDp = 320, heightDp = 480, fontScale = 1.3f)
@Composable
private fun ExercisesScreenPreview() { GymMateTheme { ExercisesScreen({}, {}) } }
