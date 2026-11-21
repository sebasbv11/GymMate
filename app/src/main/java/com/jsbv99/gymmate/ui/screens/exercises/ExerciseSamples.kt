package com.jsbv99.gymmate.ui.screens.exercises

// Compatibilidad con las pruebas existentes; los datos viven en la capa de datos.
internal val exerciseSamples get() = com.jsbv99.gymmate.data.exercises.exerciseSamples
internal fun filterExercises(query: String, muscle: String) =
    com.jsbv99.gymmate.data.exercises.filterExercises(query, muscle)
