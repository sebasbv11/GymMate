package com.jsbv99.gymmate.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jsbv99.gymmate.ui.screens.auth.LoginScreen
import com.jsbv99.gymmate.ui.screens.auth.RegisterScreen
import com.jsbv99.gymmate.ui.screens.auth.WelcomeScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

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
            HomeIntegrationPlaceholder(
                onSignOut = {
                    navController.navigate(Routes.Splash) {
                        popUpTo(Routes.Home) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
private fun HomeIntegrationPlaceholder(onSignOut: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("¡Bienvenido a GymMate!", style = MaterialTheme.typography.headlineMedium)
        Text(
            text = "La pantalla de Inicio se integrará desde el módulo de ejercicios.",
            modifier = Modifier.padding(vertical = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Button(onClick = onSignOut) {
            Text("Cerrar sesión")
        }
    }
}
