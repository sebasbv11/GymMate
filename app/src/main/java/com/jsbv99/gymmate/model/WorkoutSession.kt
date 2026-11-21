package com.jsbv99.gymmate.model

import java.io.Serializable

/** Contrato compartido con Historial/Progreso. Instantánea de la rutina al entrenar. */
data class WorkoutSession(
    val id: String,
    val routine: Routine,
    val finishedAtMillis: Long,
    val durationSeconds: Int,
    val completedSets: Int,
    val completedReps: Int
) : Serializable {
    init {
        require(id.isNotBlank())
        require(durationSeconds >= 0 && completedSets >= 0 && completedReps >= 0)
    }
}
