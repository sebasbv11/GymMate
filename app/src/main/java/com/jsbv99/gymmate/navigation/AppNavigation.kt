package com.jsbv99.gymmate.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.jsbv99.gymmate.data.RoutineSamples
import com.jsbv99.gymmate.model.Routine
import com.jsbv99.gymmate.ui.screens.auth.*
import com.jsbv99.gymmate.ui.screens.home.HomeScreen
import com.jsbv99.gymmate.ui.screens.exercises.*
import com.jsbv99.gymmate.ui.screens.routines.*
import com.jsbv99.gymmate.ui.screens.workout.*

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    // shortcut: routines live in memory for this MVP; add persistence only when scope changes.
    val routines = remember { mutableStateListOf<Routine>().apply { addAll(RoutineSamples.routines) } }
    var workoutId by rememberSaveable { mutableStateOf("") }
    var duration by rememberSaveable { mutableIntStateOf(0) }
    var sets by rememberSaveable { mutableIntStateOf(0) }
    var reps by rememberSaveable { mutableIntStateOf(0) }
    fun startWorkout(id: String) {
        workoutId = id
        navController.navigate("workout/$id")
    }

    NavHost(
        navController = navController,
        startDestination = Routes.Splash,
        modifier = modifier
    ) {
        composable(Routes.Splash) {
            WelcomeScreen(
                onLogin = { navController.navigate(Routes.Login) },
                onRegister = { navController.navigate(Routes.Register) }
            )
        }
        composable(Routes.Login) {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onLoginSuccess = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Splash) { inclusive = true }
                    }
                },
                onRegister = { navController.navigate(Routes.Register) }
            )
        }
        composable(Routes.Register) {
            RegisterScreen(
                onBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Splash) { inclusive = true }
                    }
                },
                onLogin = { navController.navigate(Routes.Login) }
            )
        }
        composable(Routes.Home) {
            HomeScreen(
                onExercises = { navController.navigate(Routes.Exercises) },
                onRoutines = { navController.navigate(Routes.Routines) },
                onHistory = { navController.navigate(Routes.History) },
                onProgress = { navController.navigate(Routes.Progress) },
                onSettings = { navController.navigate(Routes.Settings) }
            )
        }
        composable(Routes.Exercises) {
            ExercisesScreen({ navController.popBackStack() }, { navController.navigate("exercise/$it") })
        }
        composable(Routes.ExerciseDetail) { entry ->
            ExerciseDetailScreen(entry.arguments?.getString("exerciseId").orEmpty(), { navController.popBackStack() })
        }
        composable(Routes.Routines) {
            RoutinesScreen({ navController.popBackStack() }, { navController.navigate(Routes.CreateRoutine) },
                { navController.navigate("routine/$it") }, routines)
        }
        composable(Routes.CreateRoutine) {
            val editId = navController.previousBackStackEntry?.savedStateHandle?.get<String>("editRoutineId")
            RoutineEditorScreen(
                onBack = { navController.popBackStack() },
                onSaveRoutine = { routine ->
                    val index = routines.indexOfFirst { it.id == routine.id }
                    if (index < 0) routines.add(routine) else routines[index] = routine
                    navController.popBackStack()
                },
                routine = routines.find { it.id == editId }
            )
        }
        composable(Routes.RoutineDetail) { entry ->
            RoutineDetailScreen(routines.find { it.id == entry.arguments?.getString("routineId") },
                onBack = { navController.popBackStack() },
                onEditRoutine = {
                    entry.savedStateHandle["editRoutineId"] = it
                    navController.navigate(Routes.CreateRoutine)
                }, onStartWorkout = ::startWorkout)
        }
        composable(Routes.Workout) { entry ->
            val id = entry.arguments?.getString("routineId").orEmpty()
            val routine = routines.find { it.id == id }
            if (routine == null) {
                PendingScreen("Rutina no disponible", { navController.popBackStack() })
            } else WorkoutScreen(id,
                onRest = { navController.navigate(Routes.Rest) { launchSingleTop = true } },
                onFinish = { seconds, completedSets, completedReps ->
                    duration = seconds; sets = completedSets; reps = completedReps
                    navController.navigate(Routes.Summary) {
                        popUpTo(Routes.Workout) { inclusive = true }
                    }
                },
                onExit = { navController.popBackStack() }, routine = routine)
        }
        composable(Routes.Rest) {
            RestScreen(onSkip = { navController.popBackStack() }, onFinish = { navController.popBackStack() })
        }
        composable(Routes.Summary) {
            WorkoutSummaryScreen(
                onHome = { navController.popBackStack(Routes.Home, false) },
                onRepeat = {
                    navController.popBackStack()
                    startWorkout(workoutId)
                }, durationSeconds = duration, completedSets = sets, completedReps = reps,
                routineName = routines.find { it.id == workoutId }?.name ?: "Rutina local")
        }
        listOf(Routes.History to "Historial", Routes.Progress to "Progreso", Routes.Settings to "Configuración").forEach { (route, title) ->
            composable(route) { PendingScreen("$title: pendiente de integrar", { navController.popBackStack() }) }
        }
    }
}

@Composable
private fun PendingScreen(title: String, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        TextButton(onClick = onBack) { Text("Volver") }
    }
}
