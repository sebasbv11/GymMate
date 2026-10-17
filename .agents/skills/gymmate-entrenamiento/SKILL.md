---
name: gymmate-entrenamiento
description: Entrenamiento, descanso y resumen de GymMate para José, integrante 4.
---

# Responsable y alcance

José Luis Sarabia Calderón. Rama prevista: `feature/entrenamiento`.

Leer AGENTS.md, .agents/CONTEXTO_PROYECTO.md y .agents/TAREAS.md antes de editar. Confirmar responsable y revisar código actual. Esta skill no autoriza cambios Git ni tareas de otros integrantes.

Implementar en ui/screens/workout. Coordinar contrato de rutina con Joel. Mantener avance de series con rememberSaveable; detener el temporizador al salir usando efectos de Compose. Probar finalizar, omitir descanso y completar la última serie sin índices fuera de rango. No modificar navegación central.

# Entrega

Usar Kotlin, Compose y Material 3 con el tema existente, datos locales y textos en español. Las pantallas reciben lambdas y no NavController. Agregar previews, desplazamiento y estados básicos. No agregar servicios, ViewModels ni dependencias innecesarias.

Ejecutar :app:assembleDebug y las pruebas del módulo. Revisar visualmente y probar callbacks en un dispositivo o Preview; registrar cualquier verificación pendiente. Informar archivos y contrato para Jordy. Commit, push, merge y cambios de rama requieren autorización expresa actual; nunca reutilizar la autorización de una entrega pasada.
