package com.jsbv99.gymmate.ui.screens.progress

import com.jsbv99.gymmate.model.WorkoutSession

data class ProgressUiState(
    val sessions: List<WorkoutSession> = emptyList()
) {
    val sessionCount: Int get() = sessions.size
    val totalDurationSeconds: Long get() = sessions.sumOf { it.durationSeconds.toLong() }
    val completedSets: Int get() = sessions.sumOf { it.completedSets }
    val completedReps: Int get() = sessions.sumOf { it.completedReps }
    val longestSessionSeconds: Int get() = sessions.maxOfOrNull { it.durationSeconds } ?: 0
    val isEmpty: Boolean get() = sessions.isEmpty()
}
