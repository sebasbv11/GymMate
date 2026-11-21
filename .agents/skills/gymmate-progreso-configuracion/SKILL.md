---
name: gymmate-progreso-configuracion
description: Historial, progreso, configuración y verificación visual de GymMate para Jandry, integrante 5.
---

# Responsable y alcance

Jandry Paul Sánchez Murillo. Rama prevista: `feature/progreso-configuracion`.

Leer AGENTS.md, .agents/CONTEXTO_PROYECTO.md y .agents/TAREAS.md antes de editar. Confirmar responsable y revisar código actual. Esta skill no autoriza cambios Git ni tareas de otros integrantes.

Implementar historial y progreso en ui/screens/progress y configuración en ui/screens/settings. Etiquetar estadísticas simuladas. Revisar pantallas pequeñas, texto ampliado y desplazamiento con Jordy. Registrar fallos reales y capturas; no corregir módulos ajenos sin coordinar. No afirmar pruebas visuales que no se hayan ejecutado.

# Entrega

Usar Kotlin, Compose y Material 3 con el tema existente, datos locales y textos en español. Las pantallas reciben lambdas y no NavController. Agregar previews, desplazamiento y estados básicos. No agregar servicios, ViewModels ni dependencias innecesarias.

Ejecutar :app:assembleDebug y las pruebas del módulo. Revisar visualmente y probar callbacks en un dispositivo o Preview; registrar cualquier verificación pendiente. Informar archivos y contrato para Jordy. Commit, push, merge y cambios de rama requieren autorización expresa actual; nunca reutilizar la autorización de una entrega pasada.
