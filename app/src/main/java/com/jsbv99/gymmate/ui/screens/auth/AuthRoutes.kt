package com.jsbv99.gymmate.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jsbv99.gymmate.ui.screens.home.HomeScreen
import kotlinx.coroutines.launch

@Composable
fun LoginRoute(model: AuthViewModel, onBack: () -> Unit, onLogin: () -> Unit, onRegister: () -> Unit) {
    val state by model.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    LoginScreen(onBack = onBack, onLoginSuccess = {}, onRegister = onRegister,
        loading = state.loading, error = state.error,
        onSubmit = { email, password -> scope.launch { if (model.login(email, password)) onLogin() } })
}

@Composable
fun RegisterRoute(model: AuthViewModel, onBack: () -> Unit, onRegister: () -> Unit, onLogin: () -> Unit) {
    val state by model.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    RegisterScreen(onBack = onBack, onRegisterSuccess = {}, onLogin = onLogin,
        loading = state.loading, error = state.error,
        onSubmit = { name, email, password -> scope.launch { if (model.register(name, email, password)) onRegister() } })
}

@Composable
fun HomeRoute(
    model: AuthViewModel, onExercises: () -> Unit, onRoutines: () -> Unit,
    onHistory: () -> Unit, onProgress: () -> Unit, onSettings: () -> Unit, onSignOut: () -> Unit
) {
    val state by model.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    HomeScreen(onExercises, onRoutines, onHistory, onProgress, onSettings,
        userName = state.user?.name ?: "deportista",
        onSignOut = { scope.launch { if (model.signOut()) onSignOut() } })
}
