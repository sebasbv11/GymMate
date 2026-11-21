package com.jsbv99.gymmate.data.workout

import com.jsbv99.gymmate.model.WorkoutSession
import kotlinx.coroutines.flow.StateFlow

interface WorkoutRepository {
    val sessions: StateFlow<List<WorkoutSession>>
    /** Idempotente por ID: reintentar/restaurar nunca duplica una sesión. */
    suspend fun save(session: WorkoutSession)
}
