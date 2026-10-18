package com.jsbv99.gymmate

import android.graphics.Bitmap
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.graphics.asAndroidBitmap
import org.junit.Rule
import org.junit.Test
import java.io.File

class AppIntegrationTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()
    private fun click(text: String) { compose.onNodeWithText(text).performClick(); compose.waitForIdle() }
    private fun scrollClick(text: String) { compose.onNodeWithText(text).performScrollTo().performClick(); compose.waitForIdle() }
    private fun capture(name: String) {
        val dir = File(compose.activity.getExternalFilesDir(null), "qa-fixes").apply { mkdirs() }
        File(dir, "$name.png").outputStream().use {
            compose.onRoot().captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }
    private fun hideKeyboard() {
        compose.activityRule.scenario.onActivity { activity ->
            val imm = activity.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }
    private fun login() {
        click("Iniciar sesión")
        compose.onNodeWithText("Correo electrónico").performTextInput("prueba@example.com")
        compose.onNodeWithText("Contraseña").performTextInput("prueba123")
        hideKeyboard()
        capture("login-entered")
        click("Entrar")
        compose.onNodeWithText("Ver mis rutinas").assertExists()
    }
    @Test fun loginValidationAndCatalog() {
        click("Iniciar sesión")
        capture("login")
        click("Entrar")
        compose.onNodeWithText("Ingresa un correo válido").assertExists()
        compose.onNodeWithText("Debe tener al menos 6 caracteres").assertExists()
        click("← Volver")
        login()
        scrollClick("Explorar ejercicios")
        compose.onNodeWithText("Buscar por nombre o músculo").performTextInput("zzzzzz")
        compose.onNodeWithText("No encontramos ejercicios con esos filtros.").assertExists()
        hideKeyboard()
        scrollClick("Limpiar filtros")
        compose.onNodeWithText("Sentadilla").performScrollTo().performClick()
        capture("exercise-detail")
        compose.onNodeWithText("Sentadilla").assertExists()
    }
    @Test fun selectedRoutineRestSummaryRepeatAndExit() {
        login()
        scrollClick("Ver mis rutinas")
        scrollClick("Día de piernas")
        scrollClick("Iniciar entrenamiento")
        compose.onNodeWithText("0 de 4 series completadas").assertExists()
        capture("workout")
        repeat(4) { index ->
            scrollClick("Completar serie")
            if (index < 3) {
                compose.onNodeWithText("Tiempo de descanso").assertExists()
                if (index == 0) capture("rest")
                scrollClick("Omitir descanso")
                compose.onNodeWithText("${index + 1} de 4 series completadas").assertExists()
            }
        }
        scrollClick("Ver resumen")
        compose.onNodeWithText("Series completadas: 4").assertExists()
        compose.onNodeWithText("Repeticiones completadas: 60").assertExists()
        capture("summary")
        scrollClick("Repetir entrenamiento")
        compose.onNodeWithText("0 de 4 series completadas").assertExists()
        scrollClick("Salir del entrenamiento")
        click("Continuar")
        compose.onNodeWithText("0 de 4 series completadas").assertExists()
        scrollClick("Salir del entrenamiento")
        click("Salir")
        compose.onNodeWithText("Iniciar entrenamiento").assertExists()
    }
    @Test fun createEditAndRotateCustomRoutine() {
        login()
        scrollClick("Ver mis rutinas")
        scrollClick("Crear rutina")
        scrollClick("Guardar rutina")
        compose.onNodeWithText("Escribe un nombre, selecciona ejercicios y revisa las series y repeticiones.").assertExists()
        compose.onNodeWithText("Nombre de la rutina").performScrollTo().performTextInput("Rutina QA")
        compose.onAllNodes(isToggleable())[0].performScrollTo().performClick()
        compose.onNodeWithText("Series de Sentadilla").performScrollTo().performTextReplacement("2")
        compose.onNodeWithText("Repeticiones de Sentadilla").performScrollTo().performTextReplacement("8")
        hideKeyboard()
        scrollClick("Guardar rutina")
        scrollClick("Rutina QA")
        compose.onNodeWithText("2 series × 8 repeticiones").assertExists()
        scrollClick("Editar rutina")
        compose.onNodeWithText("Nombre de la rutina").performScrollTo().performTextReplacement("Rutina QA editada")
        hideKeyboard()
        scrollClick("Guardar rutina")
        compose.onNodeWithText("Rutina QA editada").assertExists()
        capture("routine-edited-before-recreate")
        compose.activityRule.scenario.recreate()
        compose.waitForIdle()
        capture("routine-after-recreate")
        compose.onNodeWithText("Rutina QA editada").assertExists()
    }

    @Test fun registerValidationAndSuccess() {
        click("Crear una cuenta")
        capture("register")
        scrollClick("Crear cuenta")
        compose.onNodeWithText("Ingresa tu nombre").assertExists()
        compose.onNodeWithText("Nombre completo").performScrollTo().performTextInput("Usuario Prueba")
        compose.onNodeWithText("Correo electrónico").performScrollTo().performTextInput("prueba@example.com")
        compose.onNodeWithText("Contraseña", substring = false).performScrollTo().performTextInput("prueba123")
        compose.onNodeWithText("Confirmar contraseña").performScrollTo().performTextInput("prueba123")
        hideKeyboard()
        scrollClick("Crear cuenta")
        compose.onNodeWithText("Ver mis rutinas").assertExists()
        capture("home")
        scrollClick("Historial de entrenamientos")
        compose.onNodeWithText("Historial").assertExists()
        click("Consultar progreso")
        compose.onNodeWithText("Tu progreso").assertExists()
        click("Semana del 5 al 11")
        compose.onNodeWithText("105 minutos de actividad").assertExists()
        scrollClick("Ver historial")
        scrollClick("Volver")
        scrollClick("Configuración")
        compose.onNodeWithContentDescription("Recordatorios simulados").performClick().assertIsOff()
        scrollClick("Libras (lb)")
        compose.onNodeWithText("Libras (lb)").assertIsSelected()
        scrollClick("Restablecer opciones")
        compose.onNodeWithContentDescription("Recordatorios simulados").performScrollTo().assertIsOn()
        scrollClick("Volver")
        compose.onNodeWithText("¡Hola, Usuario Prueba!").assertExists()
        compose.activityRule.scenario.recreate()
        compose.waitForIdle()
        compose.onNodeWithText("¡Hola, Usuario Prueba!").assertExists()
        scrollClick("Cerrar sesión")
        compose.onNodeWithText("Iniciar sesión").assertExists()
    }
}

