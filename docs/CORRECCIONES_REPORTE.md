# Correcciones del reporte de dispositivo — 18/10/2026

Base actualizada con `git pull --ff-only origin main`: d823d4e. Se conserva el reporte anterior como evidencia de la versión 23b5206; sus resultados no verifican estos cambios nuevos.

## Cambios

- MainActivity.kt: Surface raíz con el fondo y color de contenido del tema. Evita texto claro sobre el fondo blanco de ventana en Login y Registro cuando el sistema está en modo oscuro.
- WorkoutLayout.kt: rótulo con onBackground, adecuado al fondo en ambos temas.
- model/Routine.kt y navigation/AppNavigation.kt: modelos serializables y lista reemplazada mediante rememberSaveable. Android puede conservar las rutinas creadas/editadas al recrear actividad; no se incorpora almacenamiento en disco, base de datos ni dependencias.
- RegisterScreen.kt: onRegisterSuccess ahora recibe el nombre validado. AppNavigation guarda ese nombre y lo entrega a HomeScreen; el acceso simulado sin registro usa “deportista”. No existe consulta de una cuenta real.
- HomeScreen.kt: callback onSignOut y botón Cerrar sesión; la navegación retira Inicio y vuelve a Bienvenida.
- AppNavigation.kt: conecta HistorialScreen, ProgresoScreen y ConfiguracionScreen incorporadas en el último pull. Los enlaces entre Historial y Progreso reutilizan la entrada existente cuando corresponde. Las estadísticas y preferencias siguen siendo simuladas, como indican las pantallas.

## Pruebas y límites

- :app:assembleDebug correcto.
- :app:testDebugUnitTest correcto: 8 pruebas, sin fallos. Incluye serialización y restauración de una rutina personalizada con sus ejercicios, series y repeticiones.
- :app:assembleDebugAndroidTest correcto.
- AppIntegrationTest.kt incorpora las comprobaciones de acceso, registro, catálogo, entrenamiento completo y recreación de actividad usadas en el diagnóstico. Añade nombre registrado tras recreación, cierre de sesión y acceso a los módulos de Jandry.
- Dispositivo conectado posteriormente: Redmi Note 9 Pro, Android 12. Se instaló la APK corregida.
- Modo oscuro: pasaron las ocho pruebas existentes de workout, la prueba de contexto y tres recorridos de integración; el recorrido de registro/configuración falló inicialmente porque el test pulsaba Volver sin desplazarlo a la vista. Se corrigió ese desplazamiento en el test y la repetición de ese caso pasó, incluidos saludo conservado al recrear y cierre de sesión.
- Modo claro: los cuatro casos de AppIntegrationTest pasaron en una ejecución completa (OK, 4 tests).
- Se revisaron capturas reales de Login con texto ingresado en ambos temas y de Registro; el contenido es legible. Se verificó también el rótulo de workout y la rutina editada conservada después de recrear actividad.
- Evidencias y salidas: build/qa-fixes/device-tests.txt, register-dark-retest.txt, light-tests.txt; capturas en build/qa-fixes/dark/qa-fixes y light/qa-fixes.
- Para las pruebas se habilitó temporalmente MIUIOP(10021) y se cambió el modo del sistema. Al terminar se restauraron los valores originales: ignore y modo oscuro. No se alteraron otras preferencias.

Publicación autorizada por Sebastian con la cuenta SebastianAlvarez-dev. La conservación con rememberSaveable cubre la restauración de sesión gestionada por Android; no promete conservar rutinas tras cerrar explícitamente la tarea, borrar datos o reiniciar una sesión desde cero. El MVP mantiene datos simulados.
