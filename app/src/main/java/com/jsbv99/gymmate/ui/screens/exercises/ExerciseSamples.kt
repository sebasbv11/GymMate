package com.jsbv99.gymmate.ui.screens.exercises

// shortcut: catálogo propio del módulo; unificar con los datos de Joel al integrar rutinas.
internal data class ExerciseSample(
    val id: String, val name: String, val muscle: String,
    val equipment: String, val description: String, val steps: List<String>
)

internal val exerciseSamples = listOf(
    ExerciseSample("sentadilla", "Sentadilla", "Piernas", "Peso corporal",
        "Trabaja cuádriceps y glúteos mediante la flexión de las piernas.",
        listOf("Separa los pies al ancho de los hombros.", "Lleva la cadera hacia atrás y baja de forma controlada.", "Mantén las rodillas alineadas con los pies y vuelve a subir.")),
    ExerciseSample("flexiones", "Flexiones", "Pecho", "Peso corporal",
        "Fortalece el pecho y los tríceps con el peso de tu cuerpo.",
        listOf("Apoya las manos debajo de los hombros.", "Mantén el cuerpo alineado al bajar el pecho.", "Empuja el suelo sin bloquear los codos; apoya las rodillas si lo necesitas.")),
    ExerciseSample("remo", "Remo con mancuerna", "Espalda", "Mancuerna",
        "Trabaja la espalda con un movimiento de tracción controlado.",
        listOf("Apoya una mano en un banco estable.", "Con la espalda neutra, acerca la mancuerna a la cadera.", "Baja lentamente sin girar el tronco.")),
    ExerciseSample("plancha", "Plancha", "Abdomen", "Colchoneta",
        "Activa la zona media mediante una posición estable.",
        listOf("Coloca los codos debajo de los hombros.", "Extiende las piernas y mantén la cadera alineada.", "Respira con normalidad y termina cuando pierdas la postura.")),
    ExerciseSample("press", "Press de hombros", "Hombros", "Mancuernas",
        "Fortalece los hombros elevando las mancuernas de forma controlada.",
        listOf("Sostén las mancuernas a la altura de los hombros.", "Eleva los brazos sin arquear la espalda.", "Regresa lentamente a la posición inicial.")),
    ExerciseSample("curl", "Curl de bíceps", "Brazos", "Mancuernas",
        "Trabaja los bíceps mediante la flexión del codo.",
        listOf("Mantén los codos cerca del cuerpo.", "Eleva las mancuernas sin balancear el tronco.", "Baja lentamente y conserva las muñecas alineadas."))
)

internal fun filterExercises(query: String, muscle: String): List<ExerciseSample> =
    exerciseSamples.filter {
        (muscle == "Todos" || it.muscle == muscle) &&
            (it.name.contains(query.trim(), ignoreCase = true) || it.muscle.contains(query.trim(), ignoreCase = true))
    }
