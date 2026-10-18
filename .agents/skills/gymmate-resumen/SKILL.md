---
name: gymmate-resumen
description: Mantener el resumen de entrenamiento de GymMate y sus callbacks para volver al inicio o repetir una sesión.
---

Lee las reglas del proyecto, WorkoutSummaryScreen.kt y docs/ENTRENAMIENTO.md. Trabaja solo en el módulo workout del integrante 4.

Recibe duración en segundos, series y repeticiones del callback de entrenamiento. Los valores por defecto son para demostraciones y previews, no sustituyen los resultados de la sesión durante la integración. Identifica claramente las calorías como simuladas y aclara que no se guarda historial.

Usa el tema existente y contenido desplazable, sin tamaños que recorten texto ampliado. Atrás y Volver al inicio ejecutan onHome; Repetir entrenamiento ejecuta onRepeat. El integrador debe retirar la sesión anterior antes de repetir para obtener estado nuevo.

Valida el formato de duración (incluidas sesiones de más de una hora), datos recibidos y ambos callbacks. Revisa tamaños pequeños y horizontal. No añadas persistencia, Firebase, APIs, ViewModels ni dependencias. Compila con :app:assembleDebug y comunica qué comprobaciones se ejecutaron realmente.
