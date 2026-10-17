# Instrucciones del proyecto GymMate

Este archivo contiene las reglas permanentes de trabajo para cualquier integrante o asistente de IA que modifique el repositorio.

## Lectura obligatoria

Antes de realizar cambios:

1. Leer este archivo completamente.
2. Leer `.agents/CONTEXTO_PROYECTO.md`.
3. Leer `.agents/TAREAS.md`.
4. Identificar al integrante responsable y el alcance de su tarea.
5. Revisar el código existente antes de modificarlo.
6. No comenzar una tarea perteneciente a otro integrante.

## Objetivo técnico

Construir el MVP de GymMate como aplicación móvil nativa usando Kotlin, Jetpack Compose y Material Design 3.

## Reglas obligatorias

- Usar Kotlin y Jetpack Compose para todas las pantallas.
- No crear interfaces con XML.
- Utilizar componentes de Material Design 3.
- Implementar la navegación con `NavController` y rutas centralizadas.
- Mantener el paquete `com.jsbv99.gymmate`.
- Utilizar datos locales simulados en esta etapa.
- No incorporar base de datos, API, repositorios de datos o ViewModels salvo que el equipo cambie explícitamente el alcance.
- Evitar dependencias que no sean necesarias para el MVP.
- Mantener textos visibles en español.
- Respetar la identidad visual y el prototipo de GymMate.
- No borrar ni reemplazar trabajo ajeno para resolver conflictos.
- No realizar commits, pushes, merges o cambios de rama sin autorización expresa de la persona que está usando Codex.

## Organización del código

Las pantallas se organizarán bajo `ui/screens` en los siguientes módulos:

- `auth`
- `home`
- `exercises`
- `routines`
- `workout`
- `progress`
- `settings`

Los componentes reutilizables estarán en `ui/components`, las rutas en `navigation` y los modelos o datos simulados en `model` y `data`.

## Archivos compartidos

Los siguientes archivos son responsabilidad de Jordy Sebastián Bravo Véliz como integrador. Otro integrante solo debe modificarlos después de coordinarlo:

- `MainActivity.kt`
- `navigation/AppNavigation.kt`
- `navigation/Routes.kt`
- `ui/theme/Color.kt`
- `ui/theme/Theme.kt`
- `ui/theme/Type.kt`
- `app/build.gradle.kts`
- `AndroidManifest.xml`
- recursos del ícono de lanzamiento

## Contrato de las pantallas

Cada pantalla debe:

- Ser una función `@Composable`.
- Tener una función `@Preview` cuando sea posible.
- Recibir las acciones de navegación mediante funciones lambda.
- No recibir ni controlar directamente un `NavController`.
- Utilizar componentes y colores del tema, evitando valores repetidos manualmente.
- Admitir desplazamiento cuando el contenido pueda exceder la pantalla.
- Evitar tamaños rígidos que provoquen recortes.
- Mostrar datos simulados coherentes con GymMate.
- Incluir estados básicos para formularios, selecciones o botones.

## Rutas previstas

- `splash`
- `login`
- `register`
- `home`
- `exercises`
- `exercise/{exerciseId}`
- `routines`
- `routine/create`
- `routine/{routineId}`
- `workout/{routineId}`
- `rest`
- `summary`
- `history`
- `progress`
- `settings`

No se deben duplicar o renombrar rutas sin coordinarlo con el integrador.

## Verificación mínima

Antes de entregar una tarea:

- Confirmar que el proyecto compile.
- Revisar las pantallas asignadas en una vista previa o emulador.
- Probar los botones, formularios y navegación relacionados.
- Confirmar que el contenido no se corte en distintos tamaños.
- Informar qué archivos se crearon o modificaron.
- Registrar los problemas pendientes sin ocultarlos.

