package com.jsbv99.gymmate.ui.screens.routines

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsbv99.gymmate.data.RoutineSamples
import com.jsbv99.gymmate.model.*
import com.jsbv99.gymmate.ui.theme.GymMateTheme
import java.util.UUID

@Composable
private fun RoutinePage(title: String, onBack: () -> Unit, modifier: Modifier, content: @Composable ColumnScope.() -> Unit) {
    Surface(modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(Modifier.safeDrawingPadding().imePadding().verticalScroll(rememberScrollState())
            .padding(20.dp).widthIn(max = 640.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            TextButton(onClick = onBack) { Text("← Volver") }
            Text(title, style = MaterialTheme.typography.headlineMedium)
            content()
        }
    }
}

@Composable
fun RoutinesScreen(
    onBack: () -> Unit,
    onCreateRoutine: () -> Unit,
    onRoutineClick: (String) -> Unit,
    routines: List<Routine> = RoutineSamples.routines,
    modifier: Modifier = Modifier
) {
    RoutinePage("Mis rutinas", onBack, modifier) {
        Text("Organiza tu próximo entrenamiento")
        Button(onClick = onCreateRoutine, modifier = Modifier.fillMaxWidth()) { Text("Crear rutina") }
        if (routines.isEmpty()) Text("Todavía no tienes rutinas. Crea la primera.")
        routines.forEach { routine ->
            Card(onClick = { onRoutineClick(routine.id) }, modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(routine.name, style = MaterialTheme.typography.titleLarge)
                    Text("${routine.exercises.size} ejercicios · ${routine.exercises.sumOf { it.sets }} series")
                    Text("Ver rutina →", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun RoutineDetailScreen(
    routine: Routine?,
    onBack: () -> Unit,
    onEditRoutine: (String) -> Unit,
    onStartWorkout: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    RoutinePage(routine?.name ?: "Rutina no encontrada", onBack, modifier) {
        if (routine == null) {
            Text("Vuelve a la lista y selecciona una rutina disponible.")
        } else {
            Text("${routine.exercises.size} ejercicios para tu sesión")
            routine.exercises.forEachIndexed { index, item ->
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("${index + 1}. ${item.exercise.name}", style = MaterialTheme.typography.titleMedium)
                        Text(item.exercise.muscle)
                        Text("${item.sets} series × ${item.repetitions} repeticiones")
                    }
                }
            }
            OutlinedButton(onClick = { onEditRoutine(routine.id) }, modifier = Modifier.fillMaxWidth()) { Text("Editar rutina") }
            Button(onClick = { onStartWorkout(routine.id) }, modifier = Modifier.fillMaxWidth()) { Text("Iniciar entrenamiento") }
        }
    }
}

@Composable
fun RoutineEditorScreen(
    onBack: () -> Unit,
    onSaveRoutine: (Routine) -> Unit,
    routine: Routine? = null,
    exercises: List<Exercise> = RoutineSamples.exercises,
    modifier: Modifier = Modifier
) {
    val key = routine?.id ?: "new"
    val catalog = (exercises + routine?.exercises.orEmpty().map { it.exercise }).distinctBy { it.id }
    var id by rememberSaveable(key) { mutableStateOf(routine?.id ?: UUID.randomUUID().toString()) }
    var name by rememberSaveable(key) { mutableStateOf(routine?.name.orEmpty()) }
    var selected by rememberSaveable(key) { mutableStateOf(ArrayList(routine?.exercises.orEmpty().map { it.exercise.id })) }
    var sets by rememberSaveable(key) { mutableStateOf(ArrayList(catalog.map { ex -> routine?.exercises?.find { it.exercise.id == ex.id }?.sets?.toString() ?: "3" })) }
    var reps by rememberSaveable(key) { mutableStateOf(ArrayList(catalog.map { ex -> routine?.exercises?.find { it.exercise.id == ex.id }?.repetitions?.toString() ?: "12" })) }
    var attempted by rememberSaveable(key) { mutableStateOf(false) }
    val valid = name.isNotBlank() && selected.isNotEmpty() && catalog.indices.all { i ->
        catalog[i].id !in selected || (sets[i].toIntOrNull() in 1..20 && reps[i].toIntOrNull() in 1..100)
    }
    RoutinePage(if (routine == null) "Crear rutina" else "Editar rutina", onBack, modifier) {
        OutlinedTextField(name, { name = it }, label = { Text("Nombre de la rutina") },
            isError = attempted && name.isBlank(), singleLine = true, modifier = Modifier.fillMaxWidth())
        Text("Selecciona ejercicios", style = MaterialTheme.typography.titleLarge)
        Text("Define de 1 a 20 series y de 1 a 100 repeticiones por ejercicio.")
        if (catalog.isEmpty()) Text("No hay ejercicios disponibles.")
        catalog.forEachIndexed { index, exercise ->
            val checked = exercise.id in selected
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row {
                        Checkbox(checked, { enabled -> selected = ArrayList(selected).apply {
                            if (enabled) add(exercise.id) else remove(exercise.id)
                        } })
                        Column(Modifier.weight(1f).padding(top = 10.dp)) {
                            Text(exercise.name, style = MaterialTheme.typography.titleMedium)
                            Text(exercise.muscle)
                        }
                    }
                    if (checked) {
                        OutlinedTextField(sets[index], { value -> sets = ArrayList(sets).apply { set(index, value) } },
                            label = { Text("Series de ${exercise.name}") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = sets[index].toIntOrNull() !in 1..20, singleLine = true, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(reps[index], { value -> reps = ArrayList(reps).apply { set(index, value) } },
                            label = { Text("Repeticiones de ${exercise.name}") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = reps[index].toIntOrNull() !in 1..100, singleLine = true, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
        if (attempted && !valid) Text("Escribe un nombre, selecciona ejercicios y revisa las series y repeticiones.", color = MaterialTheme.colorScheme.error)
        Text("Los cambios se guardan solo durante esta sesión simulada.")
        Button(onClick = {
            attempted = true
            if (valid) onSaveRoutine(Routine(id, name.trim(), selected.map { exerciseId ->
                val index = catalog.indexOfFirst { it.id == exerciseId }
                RoutineExercise(catalog[index], sets[index].toInt(), reps[index].toInt())
            }))
        }, modifier = Modifier.fillMaxWidth()) { Text("Guardar rutina") }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Preview(showBackground = true, widthDp = 600, heightDp = 900)
@Composable
private fun RoutinesPreview() { GymMateTheme { RoutinesScreen({}, {}, {}) } }

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Preview(showBackground = true, widthDp = 600, heightDp = 900)
@Composable
private fun RoutineEditorPreview() { GymMateTheme { RoutineEditorScreen({}, {}) } }

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Preview(showBackground = true, widthDp = 600, heightDp = 900)
@Composable
private fun RoutineDetailPreview() { GymMateTheme { RoutineDetailScreen(RoutineSamples.routines.first(), {}, {}, {}) } }
