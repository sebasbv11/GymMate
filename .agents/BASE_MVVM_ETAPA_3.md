# Base MVVM de la etapa 3

La aplicación proporciona un único `AppContainer` durante la vida del proceso mediante `GymMateApplication`. Desde una ruta Compose se obtiene con `LocalAppContainer.current`. El contenedor aún no tiene repositorios: cada responsable y Jordy deben acordar y registrar una sola instancia compartida por fuente de datos. Los datos en memoria no sobreviven al cierre del proceso.

Cuando exista un repositorio y un ViewModel con constructor, Jordy puede registrar el repositorio como propiedad del contenedor y crear la fábrica desde la ruta:

```kotlin
val container = LocalAppContainer.current
val model: ExampleViewModel = viewModel(factory = container.factory {
    ExampleViewModel(container.exampleRepository)
})
val state by model.uiState.collectAsStateWithLifecycle()
```

Los nombres del ejemplo son ilustrativos: no existen todavía. La ruta entrega datos y lambdas a los componentes hijos. No crear repositorios dentro del ViewModel ni una instancia nueva en cada pantalla. Acordar entre Joel y José el contrato de rutinas, y entre José y Jandry el de sesiones terminadas; progreso debe observar las mismas sesiones que guarda entrenamiento.

La navegación y sus estados actuales se mantienen mientras los compañeros crean sus módulos. Jordy debe conectarlos y retirar los estados anteriores durante la integración, conservando y probando sus comportamientos. Este documento no certifica acuerdos del equipo, integración, publicación ni pruebas visuales.
