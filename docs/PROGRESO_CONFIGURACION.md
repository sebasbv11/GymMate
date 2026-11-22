# Etapa 3 — Historial, Progreso y Configuración

Responsable: Jandry Paul Sánchez Murillo. Rama de trabajo: `etapa3/progress-docs-jandry`.

## Historial

`HistorialScreen` presenta las sesiones recibidas desde el `WorkoutRepository` compartido con entrenamiento. Cada fila utiliza los datos guardados en `WorkoutSession`: fecha, rutina capturada, duración, series y repeticiones. Al expandir una tarjeta se muestran los ejercicios de la rutina. Si no hay sesiones, se muestra un estado vacío. Se retiraron las sesiones de ejemplo; los datos no sobreviven al cierre del proceso porque el repositorio integrado es en memoria.

La pantalla solo recibe datos y callbacks. La conexión actual de `AppNavigation` ya entrega `sessions` desde el repositorio compartido.

## Progreso

`ProgressViewModel` observa `WorkoutRepository.sessions` y transforma cada emisión en `ProgressUiState`. El estado deriva el número de sesiones, duración total, series y repeticiones completadas, y la mayor duración registrada. `ProgresoScreen` representa esos agregados y muestra una barra por sesión; la escala de las barras usa la sesión más larga de la lista actual. Con una lista vacía se presenta un estado vacío.

`ProgresoRoute` recoge el `StateFlow` usando `collectAsStateWithLifecycle()` y entrega el estado a la pantalla. No se creó otro repositorio ni se añadieron cifras de ejemplo.

## Configuración

`ConfiguracionViewModel` publica `ConfiguracionUiState` mediante `StateFlow`. Conserva las opciones existentes: recordatorios simulados, sonido de descanso simulado, kilogramos o libras, descanso entre 30 y 180 segundos en intervalos de 15 segundos y restablecimiento a los valores iniciales. `ConfiguracionRoute` observa el flujo con `collectAsStateWithLifecycle()`; `ConfiguracionScreen` recibe estado y callbacks.

Las opciones siguen siendo locales a la instancia del ViewModel y no se guardan en disco ni afectan otros módulos. No se agregó un repositorio de preferencias.

## Integración y revisión de Jordy

No se modificó `navigation/AppNavigation.kt` ni el contenedor compartido. Las llamadas existentes a `ProgresoScreen(onBack, onHistory)` y `ConfiguracionScreen(onBack)` conservan su firma: los adaptadores compatibles obtienen sus ViewModels mediante `LocalAppContainer` y `container.factory`. Progreso observa `container.workoutRepository`; Configuración conserva su estado local en su ViewModel. Así las rutas actuales pueden mostrar los módulos sin cambiar `Routes.kt`, Gradle, `AppContainer` ni `AppNavigation.kt`.

Historial ya recibe `container.workoutRepository.sessions`. Para una integración más explícita, Jordy puede sustituir las llamadas compatibles por `ProgresoRoute` y `ConfiguracionRoute`, creando los modelos en el ámbito de cada destino con las mismas fábricas. No es necesario para conectar la llamada actual. La comprobación manual de que una sesión finalizada aparezca en las tres pantallas sigue pendiente.

## Archivos del módulo

- Nuevos: `ProgressUiState.kt`, `ProgressViewModel.kt`, `ProgressRoute.kt`, `ConfiguracionUiState.kt`, `ConfiguracionViewModel.kt`, `ConfiguracionRoute.kt`.
- Modificados: `HistorialScreen.kt`, `ProgresoScreen.kt`, `ConfiguracionScreen.kt`.
- Pruebas nuevas: `ProgressUiStateTest.kt`, `ConfiguracionViewModelTest.kt`.

## Verificación

`git diff --check`: correcto. `:app:assembleDebug` y `:app:testDebugUnitTest`: BUILD SUCCESSFUL; 20 pruebas, 0 fallos y 0 errores. No se encontró `adb` en el entorno, por lo que no se ejecutó emulador ni dispositivo. Queda pendiente la revisión visual de pantallas, sus callbacks y el flujo manual de finalizar una sesión y verla reflejada en Historial y Progreso.

## Documento Word de etapa 3

La guía solicita agregar la etapa 3 al Word existente, con integrantes, URL del repositorio y dos o tres capturas legibles del código integrado. Esta actualización de Markdown documenta el módulo; no reemplaza la actualización del Word ni las capturas, que deben prepararse después de integrar y verificar el flujo completo.
