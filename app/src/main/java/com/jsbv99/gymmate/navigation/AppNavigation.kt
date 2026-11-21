package com.jsbv99.gymmate.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jsbv99.gymmate.di.LocalAppContainer
import com.jsbv99.gymmate.di.exercisesFactory
import com.jsbv99.gymmate.di.routinesFactory
import com.jsbv99.gymmate.ui.screens.auth.*
import com.jsbv99.gymmate.ui.screens.exercises.*
import com.jsbv99.gymmate.ui.screens.routines.*
import com.jsbv99.gymmate.ui.screens.workout.*
import com.jsbv99.gymmate.ui.screens.progress.*
import com.jsbv99.gymmate.ui.screens.settings.ConfiguracionScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val container = LocalAppContainer.current
    val auth: AuthViewModel = viewModel(factory = container.factory { AuthViewModel(container.authRepository) })
    val exercises: ExercisesViewModel = viewModel(factory = exercisesFactory(container.exerciseRepository))
    val routinesModel: RoutinesViewModel = viewModel(factory = routinesFactory(container.routineRepository))
    val routines by routinesModel.routines.collectAsStateWithLifecycle()
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
            LoginRoute(
                model = auth,
                onBack = { navController.popBackStack() },
                onLogin = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Splash) { inclusive = true }
                    }
                },
                onRegister = { navController.navigate(Routes.Register) }
            )
        }
        composable(Routes.Register) {
            RegisterRoute(
                model = auth,
                onBack = { navController.popBackStack() },
                onRegister = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Splash) { inclusive = true }
                    }
                },
                onLogin = { navController.navigate(Routes.Login) }
            )
        }
        composable(Routes.Home) {
            HomeRoute(
                model = auth,
                onExercises = { navController.navigate(Routes.Exercises) },
                onRoutines = { navController.navigate(Routes.Routines) },
                onHistory = { navController.navigate(Routes.History) },
                onProgress = { navController.navigate(Routes.Progress) },
                onSettings = { navController.navigate(Routes.Settings) },
                onSignOut = {
                    navController.navigate(Routes.Splash) {
                        popUpTo(Routes.Home) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.Exercises) {
            ExercisesRoute(exercises, { navController.popBackStack() }, { navController.navigate("exercise/$it") })
        }
        composable(Routes.ExerciseDetail) { entry ->
            ExerciseDetailRoute(exercises, entry.arguments?.getString("exerciseId").orEmpty(), { navController.popBackStack() })
        }
        composable(Routes.Routines) {
            RoutinesRoute(routinesModel, { navController.popBackStack() }, { navController.navigate(Routes.CreateRoutine) },
                { navController.navigate("routine/$it") })
        }
        composable(Routes.CreateRoutine) {
            val editId = navController.previousBackStackEntry?.savedStateHandle?.get<String>("editRoutineId")
            RoutineEditorRoute(routinesModel, exercises, editId,
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() })
        }
        composable(Routes.RoutineDetail) { entry ->
            RoutineDetailRoute(routinesModel, entry.arguments?.getString("routineId").orEmpty(),
                onBack = { navController.popBackStack() },
                onEdit = {
                    entry.savedStateHandle["editRoutineId"] = it
                    navController.navigate(Routes.CreateRoutine)
                }, onStart = ::startWorkout)
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
        composable(Routes.History) {
            HistorialScreen(onBack = { navController.popBackStack() }, onProgress = {
                if (!navController.popBackStack(Routes.Progress, false)) navController.navigate(Routes.Progress)
            })
        }
        composable(Routes.Progress) {
            ProgresoScreen(onBack = { navController.popBackStack() }, onHistory = {
                if (!navController.popBackStack(Routes.History, false)) navController.navigate(Routes.History)
            })
        }
        composable(Routes.Settings) {
            ConfiguracionScreen(onBack = { navController.popBackStack() })
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
