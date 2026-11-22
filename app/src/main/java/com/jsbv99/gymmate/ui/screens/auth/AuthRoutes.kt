package com.jsbv99.gymmate.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jsbv99.gymmate.ui.screens.home.HomeScreen
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.CustomCredential
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.jsbv99.gymmate.R
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

@Composable
fun LoginRoute(model: AuthViewModel, onBack: () -> Unit, onLogin: () -> Unit, onRegister: () -> Unit) {
    val state by model.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var resetSent by rememberSaveable { mutableStateOf(false) }
    var googleLoading by remember { mutableStateOf(false) }
    LoginScreen(onBack = onBack, onLoginSuccess = {}, onRegister = onRegister,
        loading = state.loading || googleLoading, error = state.error,
        onSubmit = { email, password -> scope.launch { if (model.login(email, password)) onLogin() } },
        onResetPassword = { email -> scope.launch { if (model.resetPassword(email)) resetSent = true } },
        onResetDismiss = { resetSent = false; model.clearError() }, resetSent = resetSent,
        onGoogleSignIn = {
            scope.launch {
                if (googleLoading) return@launch
                googleLoading = true
                try {
                    model.clearError()
                    val option = GetSignInWithGoogleOption.Builder(context.getString(R.string.default_web_client_id)).build()
                    val request = GetCredentialRequest.Builder().addCredentialOption(option).build()
                    val credential = CredentialManager.create(context).getCredential(context, request).credential
                    if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val token = GoogleIdTokenCredential.createFrom(credential.data).idToken
                        if (model.signInWithGoogle(token)) onLogin()
                    } else model.showGoogleError()
                } catch (_: GetCredentialCancellationException) {
                    // El usuario canceló la selección de cuenta.
                } catch (cancelled: CancellationException) { throw cancelled }
                catch (_: Exception) { model.showGoogleError() }
                finally { googleLoading = false }
            }
        })
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
    val context = LocalContext.current
    HomeScreen(onExercises, onRoutines, onHistory, onProgress, onSettings,
        userName = state.user?.name ?: "deportista",
        onSignOut = { scope.launch {
            if (model.signOut()) {
                onSignOut()
                try { CredentialManager.create(context).clearCredentialState(ClearCredentialStateRequest()) }
                catch (cancelled: CancellationException) { throw cancelled }
                catch (_: Exception) { }
            }
        } })
}
