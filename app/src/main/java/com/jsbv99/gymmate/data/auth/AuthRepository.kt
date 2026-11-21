package com.jsbv99.gymmate.data.auth

import kotlinx.coroutines.flow.StateFlow

data class AuthUser(val name: String, val email: String)

interface AuthRepository {
    val currentUser: StateFlow<AuthUser?>
    suspend fun login(email: String, password: String)
    suspend fun register(name: String, email: String, password: String)
    suspend fun signOut()
}
