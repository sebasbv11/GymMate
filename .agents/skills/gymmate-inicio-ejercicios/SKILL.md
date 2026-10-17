---
name: gymmate-inicio-ejercicios
description: Inicio, catálogo y detalle de ejercicios de GymMate para Sebastian, integrante 2.
---

# Responsable y alcance

Sebastian Esteban Álvarez De La Rosa. Rama prevista: `feature/inicio-ejercicios`.

Leer AGENTS.md, .agents/CONTEXTO_PROYECTO.md y .agents/TAREAS.md antes de editar. Confirmar responsable y revisar código actual. Esta skill no autoriza cambios Git ni tareas de otros integrantes.

Trabajar en ui/screens/home, ui/screens/exercises y ui/components. Reutilizar ExerciseCard y ScreenHeading. Consultar .agents/ENTREGA_INICIO_EJERCICIOS.md para firmas e IDs. Mantener búsqueda, filtros combinados y estado vacío. No modificar navegación ni modelos compartidos de Joel. El catálogo local es provisional hasta coordinar su unificación.

# Entrega

Usar Kotlin, Compose y Material 3 con el tema existente, datos locales y textos en español. Las pantallas reciben lambdas y no NavController. Agregar previews, desplazamiento y estados básicos. No agregar servicios, ViewModels ni dependencias innecesarias.

Ejecutar :app:assembleDebug y las pruebas del módulo. Revisar visualmente y probar callbacks en un dispositivo o Preview; registrar cualquier verificación pendiente. Informar archivos y contrato para Jordy. Commit, push, merge y cambios de rama requieren autorización expresa actual; nunca reutilizar la autorización de una entrega pasada.
