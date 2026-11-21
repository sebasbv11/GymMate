package com.jsbv99.gymmate.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jsbv99.gymmate.di.LocalAppContainer
import com.jsbv99.gymmate.di.exercisesFactory
import com.jsbv99.gymmate.di.workoutFactory
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
    val workout: WorkoutViewModel = viewModel(factory = workoutFactory(container.routineRepository, container.workoutRepository))
    val workoutState by workout.uiState.collectAsStateWithLifecycle()
    val sessions by container.workoutRepository.sessions.collectAsStateWithLifecycle()
    val entry by navController.currentBackStackEntryAsState()
    // El estado es la fuente de verdad: recrear la ruta no consume ni repite eventos.
    LaunchedEffect(workoutState.phase, entry) {
        val route = entry?.destination?.route
        when {
            workoutState.phase == WorkoutPhase.REST && route == Routes.Workout ->
                navController.navigate(Routes.Rest) { launchSingleTop = true }
            workoutState.phase == WorkoutPhase.ACTIVE && route == Routes.Rest -> navController.popBackStack()
            workoutState.phase == WorkoutPhase.SUMMARY && route == Routes.Workout ->
                navController.navigate(Routes.Summary) {
                    popUpTo(Routes.Workout) { inclusive = true }
                    launchSingleTop = true
                }
        }
    }
    fun startWorkout(id: String) {
        if (workout.start(id)) navController.navigate("workout/$id") { launchSingleTop = true }
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
        composable(Routes.Workout) {
            if (workoutState.routine == null) PendingScreen("Rutina no disponible", { navController.popBackStack() })
            else WorkoutRoute(workout, onExit = { workout.clear(); navController.popBackStack() })
        }
        composable(Routes.Rest) { RestRoute(workout) }
        composable(Routes.Summary) {
            if (workoutState.result == null) PendingScreen("Sesión no disponible", { navController.popBackStack(Routes.Home, false) })
            else WorkoutSummaryRoute(workout,
                onHome = { workout.clear(); navController.popBackStack(Routes.Home, false) },
                onRepeat = {
                    val id = workoutState.routine?.id.orEmpty()
                    if (workout.repeat()) {
                        navController.popBackStack()
                        navController.navigate("workout/$id") { launchSingleTop = true }
                    }
                })
        }
        composable(Routes.History) {
            HistorialScreen(sessions = sessions, onBack = { navController.popBackStack() }, onProgress = {
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
