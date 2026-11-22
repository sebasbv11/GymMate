package com.jsbv99.gymmate.data.auth

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(private val auth: FirebaseAuth) : AuthRepository {
    private val user = MutableStateFlow(auth.currentUser?.toAuthUser())
    override val currentUser = user.asStateFlow()
    private val listener = FirebaseAuth.AuthStateListener { user.value = it.currentUser?.toAuthUser() }

    init { auth.addAuthStateListener(listener) }

    override suspend fun login(email: String, password: String) {
        try { auth.signInWithEmailAndPassword(email.trim(), password).await() }
        catch (error: FirebaseAuthException) { throw IllegalArgumentException("Correo o contraseña incorrectos, o acceso no disponible.", error) }
        catch (error: FirebaseNetworkException) { throw IllegalStateException("Comprueba tu conexión a internet.", error) }
        user.value = auth.currentUser?.toAuthUser()
    }

    override suspend fun register(name: String, email: String, password: String) {
        require(isValidName(name.trim())) { "Ingresa un nombre de al menos 3 letras." }
        val registered = try { auth.createUserWithEmailAndPassword(email.trim(), password).await().user }
        catch (error: FirebaseAuthException) { throw IllegalArgumentException("No se pudo crear la cuenta. Revisa el correo y usa una contraseña de al menos 6 caracteres.", error) }
        catch (error: FirebaseNetworkException) { throw IllegalStateException("Comprueba tu conexión a internet.", error) }
        ?: error("No se pudo crear la cuenta")
        try { registered.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name.trim()).build()).await() }
        catch (error: CancellationException) { throw error }
        catch (error: Exception) {
            auth.signOut()
            throw IllegalStateException("La cuenta se creó, pero no se pudo guardar el nombre. Inicia sesión para continuar.", error)
        }
        user.value = registered.toAuthUser()
    }

    override suspend fun signInWithGoogle(idToken: String) {
        try { auth.signInWithCredential(GoogleAuthProvider.getCredential(idToken, null)).await() }
        catch (error: FirebaseNetworkException) { throw IllegalStateException("Comprueba tu conexión a internet.", error) }
        catch (error: FirebaseAuthException) { throw IllegalArgumentException("No se pudo iniciar sesión con Google. Inténtalo de nuevo.", error) }
        user.value = auth.currentUser?.toAuthUser()
    }

    override suspend fun resetPassword(email: String) {
        try { auth.sendPasswordResetEmail(email.trim()).await() }
        catch (error: FirebaseNetworkException) { throw IllegalStateException("Comprueba tu conexión a internet.", error) }
        catch (error: FirebaseAuthException) {
            if (error.errorCode != "ERROR_USER_NOT_FOUND") throw IllegalArgumentException("No se pudo enviar la solicitud. Revisa el correo e inténtalo de nuevo.", error)
        }
    }

    override suspend fun signOut() { auth.signOut(); user.value = null }

    private fun com.google.firebase.auth.FirebaseUser.toAuthUser() =
        AuthUser(displayName?.takeIf { it.isNotBlank() } ?: "deportista", email.orEmpty())
}
