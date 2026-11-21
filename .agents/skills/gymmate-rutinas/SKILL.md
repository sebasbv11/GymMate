---
name: gymmate-rutinas
description: Rutinas y datos compartidos de GymMate para Joel, integrante 3.
---

# Responsable y alcance

Joel Alejandro Barrera Vaca. Rama prevista: `feature/rutinas`.

Leer AGENTS.md, .agents/CONTEXTO_PROYECTO.md y .agents/TAREAS.md antes de editar. Confirmar responsable y revisar código actual. Esta skill no autoriza cambios Git ni tareas de otros integrantes.

Implementar lista, creación o edición y detalle en ui/screens/routines. Mantener modelos simples en model y datos simulados en data. Coordinar IDs de ejercicios con el catálogo de Sebastian antes de unificarlo. Seleccionar ejercicios y series o repeticiones válidas. Exponer callback para iniciar entrenamiento; no modificar navegación central.

# Entrega

Usar Kotlin, Compose y Material 3 con el tema existente, datos locales y textos en español. Las pantallas reciben lambdas y no NavController. Agregar previews, desplazamiento y estados básicos. No agregar servicios, ViewModels ni dependencias innecesarias.

Ejecutar :app:assembleDebug y las pruebas del módulo. Revisar visualmente y probar callbacks en un dispositivo o Preview; registrar cualquier verificación pendiente. Informar archivos y contrato para Jordy. Commit, push, merge y cambios de rama requieren autorización expresa actual; nunca reutilizar la autorización de una entrega pasada.


# Continuidad de la implementación

Leer ../../ENTREGA_RUTINAS.md para firmas, integración y verificaciones pendientes. El código está en RoutineScreens.kt, model/Routine.kt y data/RoutineSamples.kt. El editor conserva un ID al guardar y entrega Routine mediante onSaveRoutine; el propietario común debe actualizar la misma lista observable que consumen las tres pantallas. Las muestras son inmutables y no representan persistencia.

Conservar los IDs compatibles con Sebastian; plancha requiere segundos y no debe tratarse como repeticiones. Validación actual: nombre no vacío, ejercicios únicos y no vacíos, series 1..20 y repeticiones 1..100. Ejecutar RoutineTest al cambiar contratos o muestras. Las previews se revisaron visualmente a 360×800 y 600×900; las interacciones y la navegación integrada siguen pendientes. Consultar la entrega para las verificaciones exactas.
