package com.jsbv99.gymmate.ui.screens.routines

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jsbv99.gymmate.model.Exercise
import com.jsbv99.gymmate.ui.screens.exercises.ExercisesViewModel
import kotlinx.coroutines.launch

@Composable
fun RoutinesRoute(model: RoutinesViewModel, onBack: () -> Unit, onCreate: () -> Unit, onSelect: (String) -> Unit) {
    val routines by model.routines.collectAsStateWithLifecycle()
    RoutinesScreen(onBack, onCreate, onSelect, routines)
}

@Composable
fun RoutineDetailRoute(model: RoutinesViewModel, id: String, onBack: () -> Unit, onEdit: (String) -> Unit, onStart: (String) -> Unit) {
    val routines by model.routines.collectAsStateWithLifecycle()
    RoutineDetailScreen(routines.find { it.id == id }, onBack, onEdit, onStart)
}

@Composable
fun RoutineEditorRoute(model: RoutinesViewModel, exercisesModel: ExercisesViewModel, editId: String?, onBack: () -> Unit, onSaved: () -> Unit) {
    val routines by model.routines.collectAsStateWithLifecycle()
    val exercises by exercisesModel.exercises.collectAsStateWithLifecycle()
    val error by model.error.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    var saving by remember { mutableStateOf(false) }
    var saved by remember { mutableStateOf(false) }
    // No convertir un ID de edición inexistente en una rutina nueva.
    val routine = routines.find { it.id == editId }
    if (editId != null && routine == null) {
        RoutineDetailScreen(null, onBack, {}, {})
    } else {
        RoutineEditorScreen(onBack, { value ->
            if (!saving) {
                saving = true
                scope.launch {
                    try { if (model.save(value)) saved = true }
                    finally { saving = false }
                }
            }
        }, routine, exercises.map { Exercise(it.id, it.name, it.muscle) }, errorMessage = error, saving = saving)
        if (saved) AlertDialog(
            onDismissRequest = { saved = false; onSaved() },
            title = { Text("Rutina guardada") },
            text = { Text("Tu rutina está disponible en tu cuenta.") },
            confirmButton = { TextButton(onClick = { saved = false; onSaved() }) { Text("Entendido") } }
        )
    }
}
