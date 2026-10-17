# Contexto del proyecto GymMate

## Descripción

GymMate es una aplicación móvil que funciona como asistente personal de gimnasio. Permite consultar ejercicios, organizar rutinas, acompañar al usuario durante sus entrenamientos y visualizar su progreso.

## Problema identificado

Las personas que comienzan a entrenar suelen tener dificultades para saber qué ejercicios realizar, organizar una rutina, recordar series y repeticiones, controlar los descansos, registrar sus sesiones y observar su evolución. GymMate reúne estas funciones en una experiencia sencilla y visual.

## Usuarios principales

- Personas principiantes en el gimnasio.
- Estudiantes y adultos jóvenes.
- Usuarios que entrenan sin entrenador personal.
- Personas que necesitan organizar y registrar sus rutinas.

## Alcance funcional del MVP

- Mostrar una pantalla de bienvenida.
- Permitir el acceso y registro simulado de un usuario.
- Mostrar un resumen en la pantalla principal.
- Consultar ejercicios y sus detalles.
- Consultar, crear y editar rutinas.
- Mostrar el detalle de una rutina.
- Guiar un entrenamiento por ejercicios y series.
- Controlar el tiempo de descanso.
- Mostrar un resumen de la sesión.
- Consultar historial y progreso.
- Cambiar configuraciones locales básicas.

## Pantallas

1. Bienvenida.
2. Inicio de sesión.
3. Registro.
4. Inicio.
5. Ejercicios.
6. Detalle del ejercicio.
7. Rutinas.
8. Crear o editar rutina.
9. Detalle de rutina.
10. Entrenamiento activo.
11. Temporizador de descanso.
12. Resumen del entrenamiento.
13. Historial.
14. Progreso.
15. Configuración.

## Flujo principal

`Bienvenida → Inicio de sesión o registro → Inicio`

Desde Inicio se puede acceder a:

- `Ejercicios → Detalle del ejercicio`.
- `Rutinas → Detalle → Entrenamiento → Descanso → Resumen`.
- `Historial`.
- `Progreso`.
- `Configuración`.

## Identidad visual

GymMate debe transmitir energía, progreso, acompañamiento y confianza.

Paleta inicial obtenida de la identidad entregada:

- Azul oscuro principal: `#011C39`.
- Verde energético: `#63F155`.
- Blanco suave: `#FAFAFA`.
- Gris para textos secundarios: `#667085`.

Características visuales:

- Material Design 3.
- Estilo moderno, deportivo y limpio.
- Tarjetas con esquinas redondeadas.
- Iconografía sencilla y legible.
- Contraste suficiente.
- Espaciado uniforme.
- Uso consistente del tema en todas las pantallas.

## Recursos de marca

- `design/brand/gymmate_app_icon_source.png`: fuente visual del ícono.
- `design/brand/gymmate_logo_source.png`: fuente visual del logotipo completo.

Los archivos originales tienen el patrón cuadriculado integrado en los píxeles; no poseen transparencia real. Deben limpiarse o vectorizarse antes de usarlos como recursos finales de Android.

## Alcance de la segunda etapa

Incluye:

- Las 15 pantallas construidas con Jetpack Compose.
- Navegación funcional con `NavController`.
- Material Design 3.
- Ícono personalizado y adaptable.
- Interfaz adaptable a distintos tamaños.
- Interacciones mínimas del MVP.
- Datos simulados.
- Dos o tres capturas del MVP en el documento Word.

No exige:

- Base de datos.
- API o servicios externos.
- Autenticación real.
- Arquitectura completa por capas.
- Repositorios de datos.
- ViewModels.
- Publicación en Play Store.

## Condiciones académicas

- Mantener el mismo equipo y repositorio durante la etapa.
- Todos los integrantes deben registrar participación en Git.
- El documento Word debe conservar la primera etapa e incorporar la segunda.
- Debe incluirse la URL funcional del repositorio.
- Deben agregarse dos o tres capturas del MVP.
- Cada integrante debe subir el documento individualmente.

## Integrantes

1. Jordy Sebastián Bravo Véliz.
2. Sebastian Esteban Álvarez De La Rosa.
3. Joel Alejandro Barrera Vaca.
4. José Luis Sarabia Calderón.
5. Jandry Paul Sánchez Murillo.

