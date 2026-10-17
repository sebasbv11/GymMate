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
