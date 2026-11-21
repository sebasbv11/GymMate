package com.jsbv99.gymmate.data.routines

import com.jsbv99.gymmate.model.Routine
import kotlinx.coroutines.flow.StateFlow

interface RoutineRepository {
    val routines: StateFlow<List<Routine>>
    suspend fun save(routine: Routine)
}
