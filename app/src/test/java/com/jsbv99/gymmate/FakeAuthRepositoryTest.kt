package com.jsbv99.gymmate.data.auth

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.fail
import org.junit.Test

class FakeAuthRepositoryTest {
    @Test
    fun registrationAndSignOutUpdateTheSharedUser() = runBlocking {
        val repository = FakeAuthRepository()
        repository.register("  Sebastian  ", "s@example.com", "123456")
        assertEquals(AuthUser("Sebastian", "s@example.com"), repository.currentUser.value)
        repository.signOut()
        assertNull(repository.currentUser.value)
        repository.login("s@example.com", "123456")
        assertEquals("deportista", repository.currentUser.value?.name)
    }

    @Test
    fun invalidDataDoesNotCreateASession() = runBlocking {
        val repository = FakeAuthRepository()
        try {
            repository.login("incorrecto", "123")
            fail("Se esperaba un error de validación")
        } catch (_: IllegalArgumentException) {
            assertNull(repository.currentUser.value)
        }
    }
}
