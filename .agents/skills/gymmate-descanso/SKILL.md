---
name: gymmate-descanso
description: Implementar o revisar el temporizador local de descanso de GymMate y su cancelación al abandonar la pantalla.
---

Lee las reglas del proyecto y RestScreen.kt. Este archivo pertenece al integrante 4. Conserva callbacks onSkip y onFinish; las pantallas no controlan NavController.

Mantén el tiempo restante en rememberSaveable. Usa LaunchedEffect vinculado al ciclo de vida RESUMED para pausar al dejar primer plano y cancelar al salir de composición. Calcula intervalos con SystemClock.elapsedRealtime, no con la hora civil. Nunca crees un servicio, temporizador global o coroutine sin propietario. El tiempo no debe volverse negativo.

Al llegar a cero muestra el estado completado y habilita Finalizar descanso; no navegues automáticamente. Omitir y el botón Atrás vuelven por onSkip y detienen el conteo inmediatamente. Bloquea callbacks duplicados. Cada nuevo descanso necesita una entrada de navegación nueva; Jordy debe retirar la anterior con popBackStack.

Valida cero, omisión, restauración, cambio a segundo plano, eliminación de composición y un segundo descanso que empiece desde 60 segundos. Ejecuta las pruebas instrumentadas cuando haya dispositivo y reporta las limitaciones. Consulta docs/ENTRENAMIENTO.md para la integración.
