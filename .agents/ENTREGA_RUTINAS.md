# Entrega de rutinas — integrante 3

Joel Alejandro Barrera Vaca · feature/rutinas · 17/10/2026.

## Archivos

- model/Routine.kt: Exercise (id, name, muscle), RoutineExercise (exercise, sets, repetitions), Routine (id, name, exercises).
- data/RoutineSamples.kt: cinco ejercicios y tres rutinas locales. Conserva IDs sentadilla, flexiones, remo, press y curl del catálogo de Sebastian. No incorpora plancha porque su prescripción requiere segundos, fuera del editor de repeticiones.
- ui/screens/routines/RoutineScreens.kt: lista, editor y detalle, con previews a 360×800 y 600×900, desplazamiento y estado del formulario conservado con rememberSaveable.
- src/test/.../model/RoutineTest.kt: integridad de muestras, límites de series/repeticiones, rechazo de nombre vacío, rutina vacía y ejercicios duplicados.
- .agents/skills: las cinco skills del equipo recuperadas de feature/inicio-ejercicios; la de Joel documenta esta entrega.

## Contrato para Jordy

RoutinesScreen(onBack, onCreateRoutine, onRoutineClick, routines, modifier): conectar volver, routine/create y routine/{routineId}.
RoutineEditorScreen(onBack, onSaveRoutine, routine = null, exercises, modifier): null crea; pasar Routine existente edita. onSaveRoutine recibe la rutina completa con ID estable y nombre sin espacios extremos. Series válidas 1..20, repeticiones 1..100; exige nombre y al menos un ejercicio.
RoutineDetailScreen(routine, onBack, onEditRoutine, onStartWorkout, modifier): admite null con mensaje recuperable. Editar e iniciar reciben el ID; iniciar conecta workout/{routineId}.

Mantener una lista observable local de rutinas en el propietario común de las rutas, inicializada con RoutineSamples.routines. En onSaveRoutine agregar por ID nuevo o reemplazar por ID existente, y pasar esa misma lista a lista/detalle/editor. El editor entrega el valor; no modifica las muestras ni implementa almacenamiento. Los datos se pierden al cerrar la sesión/proceso. Resolver el ID de la ruta con firstOrNull. Coordinar la futura edición sin inventar otra ruta central.

La rama de Joel incorpora main en d810a1b, que ya contiene el merge del módulo de Sebastian. Se conservaron sus pantallas, componentes y pruebas sin modificaciones. Una prueba de compatibilidad comprueba que cada ejercicio de rutinas coincide en ID, nombre y músculo con el catálogo integrado. La futura unificación del catálogo provisional con Exercise requiere coordinación; los modelos compartidos actuales solo contienen los campos necesarios para rutinas.

## Verificación y pendientes

:app:assembleDebug y :app:testDebugUnitTest correctos. Las pruebas validan los modelos y las muestras; no prueban interacciones Compose.
Android Studio abierto con GymMate. Las tres pantallas se renderizaron y revisaron visualmente en Preview a 360×800 y 600×900 usando captura alternativa del escritorio. Se comprobó distribución, ajuste de texto, tarjetas y acciones visibles. El editor pequeño requiere desplazamiento para guardar. Capturas temporales de revisión: codex-shot-2026-10-17_20-40-40.png (detalle), codex-shot-2026-10-17_20-40-58.png (editor) y codex-shot-2026-10-17_20-41-05.png (lista), en la carpeta Temp del usuario; no se versionan. No hay dispositivo ni AVD disponible. Pendiente probar texto ampliado, selección, errores, guardar/editar y callbacks en Preview interactiva o emulador.
No se tocaron MainActivity, navegación, Gradle, manifest, tema ni otros módulos. Hasta conectar callbacks las pantallas no aparecen en la navegación de la app. El main actualizado también conserva HomeIntegrationPlaceholder en AppNavigation: el merge de Sebastian incorporó sus archivos, pero todavía falta que Jordy conecte Inicio y Ejercicios además de Rutinas.

## Asistencia de IA

Codex apoyó implementación, pruebas y documentación. Revisión humana pendiente por Joel y Jordy.
