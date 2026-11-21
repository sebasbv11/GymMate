package com.jsbv99.gymmate.data.workout

import com.jsbv99.gymmate.model.WorkoutSession
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class FakeWorkoutRepository(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WorkoutRepository {
    private val data = MutableStateFlow<List<WorkoutSession>>(emptyList())
    override val sessions = data.asStateFlow()

    override suspend fun save(session: WorkoutSession) = withContext(dispatcher) {
        delay(100) // Operación asíncrona simulada; sin red ni persistencia.
        data.update { current ->
            (current.filterNot { it.id == session.id } + session)
                .sortedByDescending { it.finishedAtMillis }
        }
    }
}
