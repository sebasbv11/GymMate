---
name: gymmate-entrenamiento
description: Implementar o mantener el entrenamiento activo de GymMate, asignado a José Luis Sarabia Calderón, integrante 4.
---

Lee AGENTS.md, .agents/CONTEXTO_PROYECTO.md y .agents/TAREAS.md antes de editar. Revisa WorkoutScreen.kt y docs/ENTRENAMIENTO.md para conocer el contrato vigente.

Trabaja en ui/screens/workout. Mantén Kotlin, Compose, Material 3 y estado con rememberSaveable. No modifiques navegación, tema, Gradle ni modelos del integrante 3. routineId identifica el estado local; la rutina actual es explícitamente de demostración hasta que el integrador conecte los datos compartidos.

Cada pulsación completa una serie. Hay 3 ejercicios, 3 series por ejercicio y 12 repeticiones por serie. Abre descanso solo entre series, nunca después de la última. Conserva el progreso al volver del descanso y al recrear la pantalla. El resumen se entrega una sola vez mediante onFinish(duracion, series, repeticiones). La duración incluye descansos y tiempo fuera de primer plano; usa reloj monotónico.

Comprueba inicio, cambio de ejercicio, novena serie, restauración y confirmación de salida. Ejecuta :app:assembleDebug y las pruebas del módulo. Prueba las vistas previas pequeñas, horizontales y con texto ampliado; documenta expresamente cualquier validación que no pudo ejecutarse. No hagas commits ni pushes sin autorización vigente del usuario.
