package com.jsbv99.gymmate.data.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class FakeAuthRepository : AuthRepository {
    private val user = MutableStateFlow<AuthUser?>(null)
    override val currentUser = user.asStateFlow()

    override suspend fun login(email: String, password: String) = withContext(Dispatchers.IO) {
        require(email.contains("@") && email.substringAfter("@", "").contains(".") && password.length >= 6) { "Revisa el correo y la contraseña" }
        delay(300)
        user.value = AuthUser("deportista", email.trim())
    }

    override suspend fun register(name: String, email: String, password: String) = withContext(Dispatchers.IO) {
        require(isValidName(name.trim()) && email.contains("@") && email.substringAfter("@", "").contains(".") && password.length >= 6) { "Revisa los datos ingresados" }
        delay(300)
        user.value = AuthUser(name.trim(), email.trim())
    }

    override suspend fun signInWithGoogle(idToken: String) {
        require(idToken.isNotBlank())
        user.value = AuthUser("deportista", "")
    }

    override suspend fun resetPassword(email: String) = withContext(Dispatchers.IO) {
        require(email.contains("@")) { "Ingresa un correo válido" }
        delay(300)
    }

    override suspend fun signOut() = withContext(Dispatchers.IO) {
        delay(300)
        user.value = null
    }
}
