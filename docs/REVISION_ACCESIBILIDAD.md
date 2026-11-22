# Revisión de accesibilidad y usabilidad de GymMate

Fecha: 22 de noviembre de 2026.

## Alcance y método

Revisión exploratoria del código y del flujo en un Redmi Note 9 Pro. Se consultaron criterios de WCAG 2.0 nivel A y AA como referencia para las pantallas móviles y las diez heurísticas de Nielsen. La guía de W3C para móviles ayuda a aplicar esos criterios; esta revisión no constituye una certificación ni demuestra conformidad completa. No se realizaron pruebas con usuarios, TalkBack, dispositivos de entrada alternativos ni una auditoría pantalla por pantalla.

## Cambios comprobados

- Apariencia: se añadió una selección entre seguir el dispositivo, tema claro y oscuro. El ajuste se conserva al cerrar y abrir la aplicación; se comprobaron ambos temas en el teléfono.
- Acceso: el botón de Google incluye el símbolo oficial y texto descriptivo. El nombre acepta letras y espacios. El formulario ofrece recuperación de contraseña.
- Lectura: se marcaron como encabezados semánticos títulos de acceso, configuración, inicio, ejercicios, rutinas y entrenamiento. Su lectura efectiva con TalkBack sigue pendiente de prueba.
- Tamaño de texto: los botones de acceso y registro usan altura mínima, en lugar de altura fija. Se revisaron Inicio con escala 1,3 y Bienvenida y acceso con escala 1,5 en el teléfono; acceso requiere desplazamiento para ver las acciones inferiores. Faltan pruebas con aumento de texto en los demás flujos.
- Contraste: para las combinaciones de colores explícitas del tema se calcularon aproximadamente 14,03:1 para texto principal claro, 11,82:1 para verde sobre fondo oscuro y 10,26:1 para error sobre fondo oscuro. Estos cálculos no cubren todos los componentes, estados ni colores generados por Material.

## Referencia WCAG 2.0

| Criterio | Observación y siguiente verificación |
|---|---|
| 1.1.1 Alternativas de texto | El símbolo de Google es decorativo y el botón tiene texto. Revisar iconos e imágenes de las demás pantallas con TalkBack. |
| 1.3.1 Información y relaciones | Se añadieron encabezados semánticos a pantallas principales. Verificar orden de lectura, agrupación y nombres con TalkBack. |
| 1.4.3 Contraste mínimo | Se comprobaron las combinaciones indicadas arriba; medir también campos, botones, estados deshabilitados y ambos temas. |
| 1.4.4 Cambio de tamaño del texto | Las pantallas principales son desplazables y se eliminaron alturas fijas de los botones de autenticación. Probar escalado alto y orientación horizontal en todas las pantallas. |
| 2.1.1 Teclado | Pendiente probar navegación con teclado o dispositivo externo y visibilidad del foco. |
| 2.4.6 Encabezados y etiquetas | Se agregaron encabezados y los formularios tienen etiquetas. Revisar que todos se anuncien correctamente. |
| 3.3.1 y 3.3.3 Errores y sugerencias | Registro y recuperación presentan mensajes de validación. Revisar si TalkBack anuncia errores nuevos y si el foco facilita corregirlos. |
| 4.1.2 Nombre, función y valor | Los controles Material y las opciones de apariencia exponen estado de selección; verificar manualmente con TalkBack. |

## Heurísticas de Nielsen

1. **Visibilidad del estado:** las acciones de acceso y el guardado de rutinas muestran espera. Al guardar la rutina se muestra una confirmación. Sin conexión, el guardado puede permanecer esperando hasta recuperar internet; la explicación específica de sincronización queda pendiente.
2. **Lenguaje familiar:** los formularios y acciones usan textos en español. Los valores de ejemplo están identificados como tales.
3. **Control y libertad:** la recuperación se puede cancelar y al salir del editor con cambios se ofrece continuar editando o descartarlos. El aviso fue comprobado en el teléfono.
4. **Consistencia:** se usan controles Material y el botón de Google muestra su símbolo oficial. Revisar de forma integral formas, espaciado y colores en todas las pantallas.
5. **Prevención de errores:** se filtran caracteres no admitidos en el nombre, se validan los campos y se confirma el cierre de sesión y el restablecimiento de opciones. Revisar pegado, datos extensos y desconexiones en el resto de formularios.
6. **Reconocimiento:** los botones revisados incluyen texto, no dependen solo de iconos. Revisar nombres accesibles y opciones en el resto de rutas.
7. **Flexibilidad:** se permite elegir apariencia clara, oscura o del dispositivo. El selector está dentro de configuración y requiere haber accedido; podría ofrecerse antes del inicio de sesión.
8. **Diseño sencillo:** los flujos revisados presentan opciones agrupadas y desplazamiento. La pantalla de configuración combina opciones reales con opciones de demostración, que se identifican como tales y deben simplificarse al implementarse.
9. **Recuperación de errores:** existe recuperación de contraseña y mensajes de validación. Faltan pruebas de errores de red, cuentas vinculadas y lectores de pantalla.
10. **Ayuda y documentación:** se ofrecen instrucciones de recuperación; no existe una ayuda integrada completa. Definir apoyo para cuentas y sincronización antes de publicar.

## Verificación adicional

- Actualización visual: se definieron colores propios para contenedores, superficies y bordes en los temas claro y oscuro; Inicio limita el ancho del contenido en pantallas grandes. La compilación y las pruebas unitarias finalizaron correctamente. En el Redmi se revisaron Inicio y detalle de ejercicios en modo oscuro. La revisión de todas las pantallas en ambos temas, con texto ampliado y en horizontal sigue pendiente.
- Después de la actualización visual quedan pendientes las pruebas completas de navegación, formularios, recuperación de contraseña, acceso con Google y confirmaciones. La revisión previa de heurísticas no reemplaza esas pruebas.
- Se probó en el teléfono el aviso al abandonar una rutina modificada y la confirmación de cierre de sesión. El diálogo de guardado y el restablecimiento requieren prueba funcional adicional.
- Por decisión del usuario no se activó ni se probará el lector de pantalla durante esta revisión.

- Se intentó ejecutar la batería instrumentada en el Redmi, pero Android canceló la instalación y no ejecutó pruebas. La aplicación se reinstaló posteriormente.
- La preferencia de tamaño de texto del dispositivo se restauró después de la comprobación.

## Prioridades pendientes

1. Probar toda la aplicación con TalkBack, aumento de texto, orientación horizontal y teclado; corregir los problemas observados.
2. Mostrar estado claro de sincronización o espera cuando no haya conexión.
3. Revisar contraste de todos los estados y componentes y medir áreas táctiles.
4. Sustituir o conectar las opciones y estadísticas de demostración antes de presentarlas como funciones reales.
5. Verificar que las credenciales de publicación de Google estén configuradas y probar recuperación en un buzón real.

Fuentes: https://www.w3.org/TR/WCAG20/ ; https://www.w3.org/TR/mobile-accessibility-mapping/ ; https://www.w3.org/TR/wcag2mobile-22/ ; https://www.nngroup.com/articles/ten-usability-heuristics/ ; https://developers.google.com/identity/branding-guidelines
