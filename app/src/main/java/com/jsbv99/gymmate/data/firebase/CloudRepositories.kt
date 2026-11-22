package com.jsbv99.gymmate.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.jsbv99.gymmate.data.RoutineSamples
import com.jsbv99.gymmate.data.routines.RoutineRepository
import com.jsbv99.gymmate.data.workout.WorkoutRepository
import com.jsbv99.gymmate.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import org.json.JSONArray
import org.json.JSONObject

private fun Routine.json(): JSONObject = JSONObject().put("id", id).put("name", name).put("exercises", JSONArray().apply {
    exercises.forEach { put(JSONObject().put("id", it.exercise.id).put("name", it.exercise.name)
        .put("muscle", it.exercise.muscle).put("sets", it.sets).put("repetitions", it.repetitions)) }
})
private fun JSONObject.routine(): Routine {
    val items = getJSONArray("exercises")
    require(items.length() in 1..100)
    return Routine(getString("id"), getString("name"), (0 until items.length()).map { index ->
        val item = items.getJSONObject(index)
        RoutineExercise(Exercise(item.getString("id"), item.getString("name"), item.getString("muscle")), item.getInt("sets"), item.getInt("repetitions"))
    })
}
private fun WorkoutSession.json(): JSONObject = JSONObject().put("routine", routine.json()).put("finishedAtMillis", finishedAtMillis)
    .put("durationSeconds", durationSeconds).put("completedSets", completedSets).put("completedReps", completedReps)
private fun JSONObject.session(id: String) = WorkoutSession(id, getJSONObject("routine").routine(), getLong("finishedAtMillis"),
    getInt("durationSeconds"), getInt("completedSets"), getInt("completedReps"))

class CloudRoutineRepository(private val auth: FirebaseAuth, private val db: FirebaseFirestore) : RoutineRepository {
    private val data = MutableStateFlow<List<Routine>>(emptyList())
    override val routines = data.asStateFlow()
    private var listener: ListenerRegistration? = null
    init {
        auth.addAuthStateListener { state ->
            listener?.remove()
            data.value = emptyList()
            val uid = state.currentUser?.uid
            if (uid != null) listener = db.collection("users").document(uid).collection("routines").addSnapshotListener { snapshot, error ->
                if (auth.currentUser?.uid == uid && error == null && snapshot != null) {
                    val saved = snapshot.documents.mapNotNull { doc ->
                        runCatching { JSONObject(requireNotNull(doc.getString("data"))).routine().also { require(it.id == doc.id) } }.getOrNull()
                    }
                    data.value = RoutineSamples.routines.filterNot { sample -> saved.any { it.id == sample.id } } + saved
                }
            }
        }
    }
    override suspend fun save(routine: Routine) {
        val uid = requireNotNull(auth.currentUser?.uid) { "Inicia sesión para guardar la rutina." }
        db.collection("users").document(uid).collection("routines").document(routine.id)
            .set(mapOf("data" to routine.json().toString())).await()
    }
}

class CloudWorkoutRepository(private val auth: FirebaseAuth, private val db: FirebaseFirestore) : WorkoutRepository {
    private val data = MutableStateFlow<List<WorkoutSession>>(emptyList())
    override val sessions = data.asStateFlow()
    private var listener: ListenerRegistration? = null
    init {
        auth.addAuthStateListener { state ->
            listener?.remove()
            data.value = emptyList()
            val uid = state.currentUser?.uid
            if (uid != null) listener = db.collection("users").document(uid).collection("sessions").addSnapshotListener { snapshot, error ->
                if (auth.currentUser?.uid == uid && error == null && snapshot != null) {
                    data.value = snapshot.documents.mapNotNull { doc ->
                        runCatching { JSONObject(requireNotNull(doc.getString("data"))).session(doc.id) }.getOrNull()
                    }.sortedByDescending { it.finishedAtMillis }
                }
            }
        }
    }
    override suspend fun save(session: WorkoutSession) {
        val uid = requireNotNull(auth.currentUser?.uid) { "Inicia sesión para guardar el entrenamiento." }
        db.collection("users").document(uid).collection("sessions").document(session.id)
            .set(mapOf("data" to session.json().toString())).await()
    }
}
