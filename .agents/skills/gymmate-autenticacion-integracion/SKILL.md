---
name: gymmate-autenticacion-integracion
description: Autenticación e integración de GymMate para Jordy, integrante 1.
---

# Responsable y alcance

Jordy Sebastián Bravo Véliz. Rama prevista: `feature/autenticacion-integracion`.

Leer AGENTS.md, .agents/CONTEXTO_PROYECTO.md y .agents/TAREAS.md antes de editar. Confirmar responsable y revisar código actual. Esta skill no autoriza cambios Git ni tareas de otros integrantes.

Bienvenida, login y registro. Mantener tema, ícono, MainActivity y navegación central. Conectar las pantallas de cada integrante mediante callbacks; integrar ramas una a una, solo con autorización. Validar campos y probar acceso simulado hasta Inicio.

# Entrega

Usar Kotlin, Compose y Material 3 con el tema existente, datos locales y textos en español. Las pantallas reciben lambdas y no NavController. Agregar previews, desplazamiento y estados básicos. No agregar servicios, ViewModels ni dependencias innecesarias.

Ejecutar :app:assembleDebug y las pruebas del módulo. Revisar visualmente y probar callbacks en un dispositivo o Preview; registrar cualquier verificación pendiente. Informar archivos y contrato para Jordy. Commit, push, merge y cambios de rama requieren autorización expresa actual; nunca reutilizar la autorización de una entrega pasada.

## Contratos actualizados

RegisterScreen entrega el nombre validado en onRegisterSuccess(String); AppNavigation lo conserva con rememberSaveable y lo pasa a HomeScreen. MainActivity pinta Surface con el fondo del tema. Las rutinas serializables se guardan con rememberSaveable y se reemplaza la lista al editar. No volver a remember para ese estado. Historial, Progreso y Configuración están conectados; el cierre de sesión vuelve a Bienvenida retirando Inicio. Consultar docs/CORRECCIONES_REPORTE.md para verificaciones pendientes.
