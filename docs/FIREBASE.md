# Firebase en GymMate

Esta integración fue acordada por el equipo. El proyecto de Firebase es `gymmate-jsbv99`, la aplicación Android es `com.jsbv99.gymmate` y Firestore está en `us-east1`.

El acceso usa correo y contraseña o una cuenta de Google con Firebase Authentication; también permite solicitar la recuperación de contraseña por correo. Las rutinas personales y las sesiones completadas se guardan en Firestore bajo `users/{uid}/routines` y `users/{uid}/sessions`. El catálogo de ejercicios y las rutinas iniciales continúan incluidos en la aplicación; guardar cambios en una rutina inicial crea una copia propia. Historial y progreso consultan las sesiones guardadas. Los datos simulados de la versión anterior vivían solo en memoria y no se migran automáticamente.

El archivo `app/google-services.json` es configuración del cliente y se excluye de Git. Cada integrante con acceso al proyecto Firebase puede obtenerlo ejecutando en la raíz del proyecto:

```powershell
firebase login
firebase apps:sdkconfig ANDROID --project gymmate-jsbv99 --out app/google-services.json
```

Si hay varias aplicaciones Android en el proyecto, especificar el identificador de la aplicación de GymMate como indica `firebase apps:sdkconfig --help`. No añadir contraseñas ni tokens al repositorio. Las reglas de acceso están en `firestore.rules` y pueden publicarse con `firebase deploy --only firestore:rules --project gymmate-jsbv99`. Para desplegar la configuración de autenticación, usar Firebase CLI 15.6.0 o posterior y `firebase deploy --only auth --project gymmate-jsbv99`.

La integración requiere conexión para crear cuentas, iniciar sesión y confirmar escrituras. El acceso con Google requiere registrar en Firebase la huella SHA-1 de cada certificado de firma; la configuración de desarrollo está registrada y la firma de publicación se debe registrar por separado. Firebase envía los correos de recuperación según la configuración del proyecto; la recepción depende del proveedor de correo. No se ha implementado verificación de correo. Antes de distribuir la aplicación, revisar las reglas y completar pruebas de dispositivo, recuperación de cuenta, costes y seguridad. El plan de Gemini de una cuenta no implica automáticamente facturación ni cuotas adicionales en Firebase.
