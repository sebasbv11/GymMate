# Entrega del integrante 2

Responsable: Sebastian Esteban Álvarez De La Rosa.
Rama: feature/inicio-ejercicios.

## Archivos y contrato para Jordy

- ui/screens/home/HomeScreen.kt: HomeScreen(onExercises, onRoutines, onHistory, onProgress, onSettings, modifier, userName).
  Conectar respectivamente a exercises, routines, history, progress y settings usando Routes desde AppNavigation. userName admite el nombre del usuario simulado.
- ui/screens/exercises/ExercisesScreen.kt: ExercisesScreen(onBack, onExerciseClick, modifier).
  onBack vuelve a la pantalla anterior. onExerciseClick recibe el ID String para exercise/{exerciseId}.
- ui/screens/exercises/ExerciseDetailScreen.kt: ExerciseDetailScreen(exerciseId, onBack, modifier).
  Pasar el argumento de ruta. Un ID desconocido muestra un mensaje y permite volver.
- ui/components/ExerciseComponents.kt: ExerciseCard y ScreenHeading reutilizables.
- ui/screens/exercises/ExerciseSamples.kt: catálogo interno provisional; IDs sentadilla, flexiones, remo, plancha, press y curl. Coordinar con Joel la unificación de modelos y datos; no se modificaron sus archivos.
- Pruebas: ui/screens/exercises/ExerciseSamplesTest.kt en src/test.
- .agents/skills: cinco skills, una por área del equipo; no implementan tareas de otros integrantes ni conceden autorizaciones Git.

## Comportamiento

Inicio presenta un saludo, resumen semanal explícitamente simulado y cinco accesos.
Catálogo con búsqueda por nombre o músculo, filtros combinables, contador y limpieza cuando no hay resultados. Estado conservado mediante rememberSaveable.
Detalle con músculo, equipo, descripción, indicaciones y aviso de video próximo, sin servicios externos.
Las tres pantallas tienen previews de 360×640 y 320×480 con texto ampliado. Los contenedores permiten desplazamiento y respetan las áreas del sistema.

## Verificación

:app:assembleDebug y :app:testDebugUnitTest ejecutados correctamente.
Dos pruebas del catálogo verifican filtros combinados, espacios, mayúsculas, resultados vacíos, IDs únicos e indicaciones.
Las cinco skills pasaron quick_validate.py.
No se modificaron MainActivity, navegación, Gradle, manifest ni tema. La aplicación conserva el placeholder de Inicio hasta la integración de Jordy.
La navegación completa del MVP debe probarse después de conectar las rutas; no hay emulador o dispositivo disponible en este entorno.

## Asistencia de IA

17/10/2026: Codex apoyó las tres pantallas, componentes, pruebas y skills del equipo. Revisión humana pendiente por Sebastian y Jordy. No se declara una revisión humana realizada.

Revisión visual: las tres pantallas renderizadas en Android Studio Preview, 360×640 y 320×480 con fontScale 1.3. Se verificaron distribución y ajuste de texto en los segmentos visibles. El contenido restante requiere desplazamiento; no se ha probado la interacción en un dispositivo. Capturas de inspección guardadas temporalmente fuera del repositorio.
