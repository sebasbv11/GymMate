package com.jsbv99.gymmate.ui.screens.auth

import androidx.lifecycle.ViewModel
import com.jsbv99.gymmate.data.auth.AuthRepository
import com.jsbv99.gymmate.data.auth.AuthUser
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class AuthUiState(val user: AuthUser? = null, val loading: Boolean = false, val error: String? = null)

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    val currentUser = repository.currentUser
    private val loading = MutableStateFlow(false)
    private val error = MutableStateFlow<String?>(null)
    val uiState: StateFlow<AuthUiState> = combine(repository.currentUser, loading, error) { user, busy, message ->
        AuthUiState(user, busy, message)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AuthUiState())

    suspend fun login(email: String, password: String): Boolean = run { repository.login(email, password) }
    suspend fun register(name: String, email: String, password: String): Boolean = run { repository.register(name, email, password) }
    suspend fun signInWithGoogle(idToken: String): Boolean = run { repository.signInWithGoogle(idToken) }
    fun showGoogleError() { error.value = "No se pudo iniciar sesión con Google. Inténtalo de nuevo." }
    suspend fun resetPassword(email: String): Boolean = run { repository.resetPassword(email) }
    fun clearError() { error.value = null }
    suspend fun signOut(): Boolean = run { repository.signOut() }

    private suspend fun run(action: suspend () -> Unit): Boolean {
        if (loading.value) return false
        loading.value = true
        error.value = null
        return try {
            action()
            true
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (failure: Exception) {
            error.value = when (failure) {
                is IllegalArgumentException, is IllegalStateException -> failure.message
                else -> "No se pudo completar la operación. Inténtalo de nuevo."
            }
            false
        } finally {
            loading.value = false
        }
    }
}
