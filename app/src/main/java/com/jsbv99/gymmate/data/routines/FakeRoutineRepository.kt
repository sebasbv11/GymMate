package com.jsbv99.gymmate.data.routines

import com.jsbv99.gymmate.data.RoutineSamples
import com.jsbv99.gymmate.model.Routine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class FakeRoutineRepository(initial: List<Routine> = RoutineSamples.routines) : RoutineRepository {
    private val data = MutableStateFlow(initial.toList())
    override val routines = data.asStateFlow()
    override suspend fun save(routine: Routine) = withContext(Dispatchers.IO) {
        delay(100)
        data.update { current ->
            if (current.any { it.id == routine.id }) current.map { if (it.id == routine.id) routine else it }
            else current + routine
        }
    }
}
