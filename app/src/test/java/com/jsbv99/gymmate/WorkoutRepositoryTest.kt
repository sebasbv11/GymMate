package com.jsbv99.gymmate

import com.jsbv99.gymmate.data.RoutineSamples
import com.jsbv99.gymmate.data.workout.FakeWorkoutRepository
import com.jsbv99.gymmate.model.WorkoutSession
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Test

class WorkoutRepositoryTest {
    @Test fun concurrentRetriesAreIdempotentAndNewestSessionsAppearFirst() = runBlocking {
        val repo = FakeWorkoutRepository()
        val first = WorkoutSession("one", RoutineSamples.routines.first(), 100, 10, 1, 12)
        val second = first.copy(id = "two", finishedAtMillis = 200)
        coroutineScope { listOf(first, second, first, second).map { launch { repo.save(it) } }.joinAll() }
        assertEquals(listOf("two", "one"), repo.sessions.value.map { it.id })
        repo.save(first.copy(durationSeconds = 20))
        assertEquals(2, repo.sessions.value.size)
        assertEquals(20, repo.sessions.value.last().durationSeconds)
        assertTrue(FakeWorkoutRepository().sessions.value.isEmpty())
    }
}
