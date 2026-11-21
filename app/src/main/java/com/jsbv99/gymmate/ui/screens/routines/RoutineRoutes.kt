package com.jsbv99.gymmate.ui.screens.routines

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
    // No convertir un ID de edición inexistente en una rutina nueva.
    val routine = routines.find { it.id == editId }
    if (editId != null && routine == null) {
        RoutineDetailScreen(null, onBack, {}, {})
    } else {
        RoutineEditorScreen(onBack, { value -> scope.launch { if (model.save(value)) onSaved() } },
            routine, exercises.map { Exercise(it.id, it.name, it.muscle) }, errorMessage = error)
    }
}
