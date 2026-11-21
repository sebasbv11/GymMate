package com.jsbv99.gymmate.ui.screens.workout

import com.jsbv99.gymmate.model.Routine
import com.jsbv99.gymmate.model.WorkoutSession
import java.io.Serializable

enum class WorkoutPhase { ACTIVE, REST, SUMMARY }

data class WorkoutUiState(
    val routine: Routine? = null,
    val sessionId: String = "",
    val startedAtMillis: Long = 0,
    val completedSets: Int = 0,
    val durationSeconds: Int = 0,
    val remainingRestMillis: Long = 60_000,
    val phase: WorkoutPhase = WorkoutPhase.ACTIVE,
    val saving: Boolean = false,
    val error: String? = null,
    val result: WorkoutSession? = null
) : Serializable {
    val totalSets: Int get() = routine?.exercises?.sumOf { it.sets } ?: 0
    val complete: Boolean get() = totalSets > 0 && completedSets >= totalSets
    val exerciseIndex: Int get() {
        val items = routine?.exercises ?: return 0
        return items.indices.firstOrNull { completedSets < items.take(it + 1).sumOf { e -> e.sets } }
            ?: items.lastIndex
    }
    val previousSets: Int get() = routine?.exercises?.take(exerciseIndex)?.sumOf { it.sets } ?: 0
    val completedReps: Int get() {
        var remaining = completedSets
        return routine?.exercises?.sumOf {
            val done = remaining.coerceIn(0, it.sets)
            remaining -= done
            done * it.repetitions
        } ?: 0
    }
}
