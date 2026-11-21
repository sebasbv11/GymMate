# Etapa 3 — Entrenamiento de José

Implementación desarrollada en `feature/entrenamiento`. José confirmó que funciona y autorizó el commit y su publicación en `main` el 21 de noviembre de 2026.

## Contratos e integración

- Se reutiliza `RoutineRepository.routines` de Joel, sin cambiar sus modelos ni su repositorio. Al iniciar se captura una instantánea de la rutina seleccionada: los ejercicios, series y repeticiones no cambian durante la sesión.
- `WorkoutSession`: ID único, rutina capturada, fecha de finalización en milisegundos Unix, duración en segundos, series y repeticiones completadas. El ID identifica la sesión, no la rutina.
- `WorkoutRepository.sessions`: `StateFlow<List<WorkoutSession>>`, orden descendente por finalización. `save(session)` es suspendido e idempotente por ID. La implementación simulada usa `Dispatchers.IO` y demora asíncrona; no utiliza base de datos ni red.
- `AppContainer.workoutRepository` es la única instancia para entrenamiento e historial. Jandry puede observar ese mismo flujo para Progreso; sus estadísticas actuales siguen siendo ejemplos independientes.
- `WorkoutViewModel` recibe ambos repositorios, `SavedStateHandle` y relojes inyectables. Su `WorkoutUiState` expone rutina, avance, duración, descanso, guardado, errores y resultado.
- Las rutas recogen el estado con `collectAsStateWithLifecycle()`. Las pantallas reciben datos y callbacks, sin ViewModels ni `NavController`.
- Se realizó la conexión mínima en `AppNavigation` y `AppContainer` para que el flujo sea accesible desde la aplicación. No se cambiaron las rutas centralizadas, Gradle ni las pantallas de rutinas de Joel.
- Historial recibe las sesiones compartidas y conserva aparte sus ejemplos existentes. Esta conexión no representa un acuerdo confirmado con Jandry: debe revisar el contrato antes de su integración de Progreso.

## Comportamiento

1. Completar serie avanza una vez y abre descanso únicamente si quedan series. Las pulsaciones durante descanso o guardado se ignoran.
2. El descanso empieza en 60 segundos. Su coroutine pertenece al efecto de la ruta y se cancela al salir o dejar el estado RESUMED. Se pausa en segundo plano, no navega automáticamente al llegar a cero y se puede omitir con el botón o Atrás.
3. La duración de la sesión usa reloj monotónico e incluye descansos y segundo plano. Al completar todas las series, Ver resumen guarda la sesión antes de abrir el resumen. El guardado bloquea pulsaciones duplicadas y ofrece reintento si falla.
4. La navegación se deriva de la fase y el destino actual. Recrear una pantalla no consume un evento repetible ni agrega otro descanso/resumen.
5. El estado de la sesión se conserva con `SavedStateHandle`. Restaurar un resumen vuelve a registrar el mismo ID si se recreó el repositorio en memoria. Esto no es persistencia: las demás sesiones se pierden al finalizar el proceso.
6. Repetir genera un ID nuevo, conserva la rutina capturada y reinicia progreso y tiempo. Salir antes del resumen descarta la sesión tras confirmación.
7. Las calorías continúan siendo un ejemplo explícitamente simulado.

## Prueba física a cargo de José

La aplicación no se ejecutará automáticamente en un dispositivo ni emulador.

- Iniciar sesión → Rutinas → Día de piernas → Iniciar entrenamiento. Debe comenzar en 0 de 4 series.
- Completar una serie, comprobar descanso de 60 segundos, omitir y verificar 1 de 4 series. Volver a completar: debe abrir un descanso nuevo desde 60.
- Enviar la aplicación a segundo plano durante un descanso y volver: el descanso debe estar pausado; la duración de la sesión incluye ese intervalo.
- Dejar un descanso llegar a cero: debe esperar Finalizar descanso. Probar también Atrás como omisión.
- Rotar o recrear durante entrenamiento y descanso: conservar avance y tiempo restante, sin abrir pantallas duplicadas.
- Completar las cuatro series. No debe abrir otro descanso. Ver resumen debe mostrar 4 series y 60 repeticiones.
- Volver a Inicio → Historial: debe aparecer una sola sesión de Día de piernas con 60 repeticiones. Recrear el resumen antes de volver y comprobar que no duplica la sesión.
- Repetir entrenamiento: empezar desde cero y guardar otra sesión independiente al finalizar.
- Salir a mitad de sesión: comprobar Continuar y Salir; no guardar una sesión incompleta.
- Crear una rutina con distintas series/repeticiones, entrenarla y comprobar el total. Revisar pantallas en horizontal, pantalla pequeña, texto ampliado y ambos temas.

## Verificación local

- `:app:assembleDebug`: correcto; APK de depuración generado.
- `:app:testDebugUnitTest`: 16 pruebas, cero fallos y cero errores. Incluye cómputo de avance, serialización y guardado concurrente/idempotente.
- `:app:assembleDebugAndroidTest`: correcto; pruebas instrumentadas compiladas. No ejecutadas.
- `git diff --check`: correcto.
- José confirmó que funciona tras su revisión física. No se atribuye esa confirmación a la ejecución de las pruebas instrumentadas ni a una comprobación exhaustiva de todos los tamaños y temas.

## Archivos de la entrega

Rutas relativas a `app/src/main/java/com/jsbv99/gymmate/`:

- Nuevos: `model/WorkoutSession.kt`, `data/workout/WorkoutRepository.kt`, `data/workout/FakeWorkoutRepository.kt`, `di/WorkoutFactory.kt`, `ui/screens/workout/WorkoutUiState.kt`, `ui/screens/workout/WorkoutViewModel.kt`, `ui/screens/workout/WorkoutRoutes.kt`.
- Modificados: `di/AppContainer.kt`, `navigation/AppNavigation.kt`, `ui/screens/workout/WorkoutScreen.kt`, `ui/screens/workout/RestScreen.kt`, `ui/screens/workout/WorkoutSummaryScreen.kt`, `ui/screens/progress/HistorialScreen.kt`.
- Pruebas unitarias nuevas: `app/src/test/java/com/jsbv99/gymmate/WorkoutRepositoryTest.kt`, `app/src/test/java/com/jsbv99/gymmate/ui/screens/workout/WorkoutUiStateTest.kt`.
- Pruebas instrumentadas: se actualizaron `AppIntegrationTest.kt`, `WorkoutScreensTest.kt` y `WorkoutDeviceTest.kt`; se agregó `WorkoutTestContent.kt`, en sus directorios actuales bajo `app/src/androidTest/java/com/jsbv99/gymmate/`.
- Documentación nueva: este archivo, `docs/ETAPA3_ENTRENAMIENTO.md`.
