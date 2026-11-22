package com.jsbv99.gymmate.ui.screens.routines

import androidx.lifecycle.ViewModel
import com.jsbv99.gymmate.data.routines.RoutineRepository
import com.jsbv99.gymmate.model.Routine
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoutinesViewModel(private val repository: RoutineRepository) : ViewModel() {
    val routines = repository.routines
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    suspend fun save(routine: Routine): Boolean = try {
        _error.value = null
        repository.save(routine)
        true
    } catch (cancelled: CancellationException) {
        throw cancelled
    } catch (failure: Exception) {
        _error.value = "No se pudo guardar la rutina. Revisa tu conexión e inténtalo de nuevo."
        false
    }
}
