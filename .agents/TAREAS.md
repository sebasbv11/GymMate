# Distribución de tareas de GymMate

## Reglas de colaboración

- Cada integrante trabajará en su propia rama.
- Cada integrante modificará únicamente sus pantallas y archivos asignados.
- Todos deberán realizar commits propios, claros y verificables.
- Antes de comenzar, cada integrante debe actualizar su rama desde `main`.
- Las ramas se integrarán una a una siguiendo el orden indicado en este archivo.
- Solamente el responsable de integración modificará la navegación central y los archivos compartidos.
- Una tarea se considera terminada cuando compila, puede probarse y cumple sus criterios de aceptación.
- Cada integrante debe comprender y poder explicar el código generado con asistencia de IA.

## Integrante 1 — Jordy Sebastián Bravo Véliz

**Rama:** `feature/autenticacion-integracion`

### Pantallas

- Bienvenida.
- Inicio de sesión.
- Registro.

### Responsabilidades adicionales

- Configurar colores, tipografía y tema general.
- Preparar el ícono adaptable de GymMate.
- Crear la estructura de navegación y registrar todas las rutas.
- Mantener `MainActivity.kt`.
- Integrar las ramas de los demás integrantes.
- Resolver los conflictos de los archivos compartidos con conocimiento del equipo.

### Criterios de aceptación

- La bienvenida muestra la identidad de GymMate y permite continuar.
- Inicio de sesión valida que los campos obligatorios no estén vacíos.
- Registro solicita los datos básicos definidos para el MVP.
- Se puede cambiar entre inicio de sesión y registro.
- El acceso simulado conduce a Inicio.
- El tema y la navegación central están preparados para recibir las demás pantallas.

## Integrante 2 — Sebastian Esteban Álvarez De La Rosa

**Rama:** `feature/inicio-ejercicios`

### Pantallas

- Inicio.
- Lista de ejercicios.
- Detalle del ejercicio.

### Responsabilidades adicionales

- Crear tarjetas y componentes visuales reutilizables.
- Preparar barras superiores o inferiores que necesite su módulo.
- Entregar callbacks de navegación sin modificar `AppNavigation.kt`.

### Criterios de aceptación

- Inicio muestra saludo, resumen y accesos principales.
- La lista presenta varios ejercicios simulados.
- Los ejercicios pueden filtrarse o seleccionarse.
- El detalle muestra nombre, músculo, descripción e indicaciones.
- Todas las acciones de navegación se exponen mediante lambdas.

## Integrante 3 — Joel Alejandro Barrera Vaca

**Rama:** `feature/rutinas`

### Pantallas

- Lista de rutinas.
- Crear o editar rutina.
- Detalle de rutina.

### Responsabilidades adicionales

- Crear modelos simples para ejercicios y rutinas.
- Preparar datos simulados compartidos.
- Mantener consistencia entre rutinas, ejercicios, series y repeticiones.

### Criterios de aceptación

- Se muestran varias rutinas simuladas.
- Se puede abrir el detalle de una rutina.
- Crear o editar permite seleccionar ejercicios y establecer series o repeticiones.
- El detalle muestra los ejercicios de la rutina.
- El botón para iniciar el entrenamiento ejecuta el callback correspondiente.

## Integrante 4 — José Luis Sarabia Calderón

**Rama:** `feature/entrenamiento`

### Pantallas

- Entrenamiento activo.
- Temporizador de descanso.
- Resumen del entrenamiento.

### Responsabilidades adicionales

- Controlar el avance simulado entre ejercicios y series.
- Implementar un temporizador local.
- Manejar estados básicos con `remember` o `rememberSaveable`.

### Criterios de aceptación

- Se muestra el ejercicio actual, sus series y repeticiones.
- Se puede marcar una serie como completada.
- El descanso presenta una cuenta regresiva.
- Se puede omitir o finalizar el descanso.
- El resumen muestra duración y resultados de la sesión.

## Integrante 5 — Jandry Paul Sánchez Murillo

**Rama:** `feature/progreso-configuracion`

### Pantallas

- Historial.
- Progreso.
- Configuración.

### Responsabilidades adicionales

- Revisar la adaptabilidad de las 15 pantallas.
- Ejecutar la prueba funcional final junto al integrador.
- Registrar errores encontrados.
- Obtener dos o tres capturas representativas del MVP.
- Apoyar la incorporación de la segunda etapa al documento Word.

### Criterios de aceptación

- Historial muestra entrenamientos anteriores simulados.
- Progreso presenta indicadores o estadísticas legibles.
- Configuración permite cambiar opciones locales básicas.
- Las pantallas no presentan cortes evidentes en diferentes tamaños.
- Se preparan las capturas requeridas para el documento.

## Propiedad de archivos compartidos

| Área o archivo | Responsable principal |
|---|---|
| `MainActivity.kt` | Jordy Sebastián Bravo Véliz |
| `navigation/AppNavigation.kt` | Jordy Sebastián Bravo Véliz |
| `navigation/Routes.kt` | Jordy Sebastián Bravo Véliz |
| Tema e ícono | Jordy Sebastián Bravo Véliz |
| Componentes visuales comunes | Sebastian Esteban Álvarez De La Rosa |
| Modelos y datos simulados | Joel Alejandro Barrera Vaca |
| Estados del entrenamiento | José Luis Sarabia Calderón |
| Adaptabilidad, pruebas y evidencia | Jandry Paul Sánchez Murillo |

## Orden de implementación e integración

1. Jordy crea la base visual, la navegación y las pantallas de autenticación.
2. El equipo actualiza sus ramas desde la base común.
3. Sebastian entrega Inicio y Ejercicios.
4. Joel entrega Rutinas.
5. José entrega Entrenamiento.
6. Jandry entrega Progreso y Configuración.
7. Jordy integra las ramas una a una.
8. Jandry y Jordy realizan la prueba completa.
9. El equipo corrige errores dentro de sus respectivos módulos.
10. Se obtienen capturas y se completa el documento.

## Estado de las tareas

| Integrante | Estado inicial | Rama integrada | Verificación |
|---|---|---|---|
| Jordy Sebastián Bravo Véliz | En progreso | No | Compilación correcta; revisión visual pendiente |
| Sebastian Esteban Álvarez De La Rosa | Implementado; código integrado en main | Sí | assembleDebug y pruebas unitarias correctos; conexión de navegación central pendiente |
| Joel Alejandro Barrera Vaca | Implementado; código integrado en main | Sí | assembleDebug y pruebas unitarias; Preview revisada a dos tamaños; interacción pendiente |
| José Luis Sarabia Calderón | Implementado e integrado con las rutinas seleccionadas | Sí | assembleDebug y pruebas unitarias correctos; instrumentación compilada, ejecución y revisión visual pendientes |
| Jandry Paul Sánchez Murillo | Pendiente | No | Pendiente |

## Registro de asistencia de IA

Cada integrante registrará aquí, de forma breve, la tarea apoyada por IA y la revisión humana realizada. No se deben incluir conversaciones completas, credenciales ni información sensible.

| Fecha | Integrante | Herramienta | Tarea apoyada | Revisión humana |
|---|---|---|---|---|
| 17/10/2026 | Jordy Sebastián Bravo Véliz | Codex | Documentación del equipo, identidad visual, tema, navegación y pantallas de acceso | Pendiente de revisión visual por Jordy |
| 17/10/2026 | Sebastian Esteban Álvarez De La Rosa | Codex | Inicio, catálogo, detalle, componentes, pruebas y skills por integrante | Pendiente de revisión humana por Sebastian y Jordy |
| 17/10/2026 | Joel Alejandro Barrera Vaca | Codex | Modelos, datos simulados, lista, editor y detalle de rutinas; pruebas y skills | Pendiente de revisión humana por Joel y Jordy |

## Lista general de verificación

- [ ] Las 15 pantallas están implementadas.
- [ ] Todas las pantallas utilizan Jetpack Compose.
- [ ] No existe XML de interfaz innecesario.
- [ ] La navegación utiliza `NavController`.
- [ ] No existen pantallas huérfanas.
- [ ] La aplicación no se cierra durante la navegación.
- [ ] Se utiliza Material Design 3.
- [ ] El diseño es consistente con la identidad de GymMate.
- [ ] El ícono está vectorizado o preparado como ícono adaptable.
- [ ] La interfaz se adapta a distintos tamaños.
- [ ] Todos los integrantes tienen commits verificables.
- [ ] La URL del repositorio funciona.
- [ ] El Word contiene la primera y segunda etapa.
- [ ] El Word contiene dos o tres capturas del MVP.
- [ ] Cada integrante realizó su entrega individual.

Integración autorizada de entrenamiento: navegación central conectada con Inicio, Ejercicios y Rutinas. Skills de entrenamiento, descanso y resumen disponibles. Prueba física del flujo integrado pendiente por ausencia de dispositivo y AVD.
