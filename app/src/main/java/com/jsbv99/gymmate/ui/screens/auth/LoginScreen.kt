package com.jsbv99.gymmate.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.jsbv99.gymmate.R
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsbv99.gymmate.ui.brand.GymMateMark
import com.jsbv99.gymmate.ui.theme.GymMateTheme

@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onLoginSuccess: () -> Unit,
    onRegister: () -> Unit,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    error: String? = null,
    onSubmit: (String, String) -> Unit = { _, _ -> onLoginSuccess() },
    onResetPassword: (String) -> Unit = {},
    onResetDismiss: () -> Unit = {},
    resetSent: Boolean = false,
    onGoogleSignIn: () -> Unit = {}
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var attempted by rememberSaveable { mutableStateOf(false) }
    var showReset by rememberSaveable { mutableStateOf(false) }
    var resetEmail by rememberSaveable { mutableStateOf("") }
    var resetAttempted by rememberSaveable { mutableStateOf(false) }
    val emailValid = email.contains("@") && email.substringAfter("@", "").contains(".")
    val passwordValid = password.length >= 6

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = onBack) { Text("← Volver") }
        }
        Spacer(Modifier.height(12.dp))
        GymMateMark(size = 92.dp)
        Spacer(Modifier.height(20.dp))
        Text("Bienvenido de nuevo", modifier = Modifier.semantics { heading() }, style = MaterialTheme.typography.headlineMedium)
        Text(
            text = "Ingresa tus datos para continuar",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 6.dp, bottom = 28.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 460.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Correo electrónico") },
                singleLine = true,
                isError = attempted && !emailValid,
                supportingText = {
                    if (attempted && !emailValid) Text("Ingresa un correo válido")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Contraseña") },
                singleLine = true,
                isError = attempted && !passwordValid,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(onClick = { showPassword = !showPassword }) {
                        Text(if (showPassword) "Ocultar" else "Mostrar")
                    }
                },
                supportingText = {
                    if (attempted && !passwordValid) Text("Debe tener al menos 6 caracteres")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )
            TextButton(onClick = { onResetDismiss(); resetEmail = email.trim(); resetAttempted = false; showReset = true }, enabled = !loading) {
                Text("¿Olvidaste tu contraseña?")
            }
            if (error != null && !showReset) Text(error, color = MaterialTheme.colorScheme.error)
            Button(
                enabled = !loading,
                onClick = {
                    attempted = true
                    if (emailValid && passwordValid) onSubmit(email.trim(), password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 52.dp)
            ) {
                Text(if (loading) "Espera..." else "Entrar")
            }
            OutlinedButton(
                onClick = onGoogleSignIn, enabled = !loading,
                modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
                colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White, contentColor = Color(0xFF1F1F1F)
                )
            ) {
                Image(painterResource(R.drawable.google_g), contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.size(10.dp))
                Text("Continuar con Google")
            }
            OutlinedButton(
                onClick = onRegister,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 52.dp)
            ) {
                Text("Crear una cuenta")
            }
        }
    }
    if (showReset) {
        AlertDialog(
            onDismissRequest = { if (!loading) { showReset = false; onResetDismiss() } },
            title = { Text("Recuperar contraseña") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (resetSent) Text("Si existe una cuenta con ese correo, recibirás instrucciones para recuperar tu contraseña.")
                    else {
                        Text("Ingresa tu correo para recibir las instrucciones.")
                        OutlinedTextField(value = resetEmail, onValueChange = { resetEmail = it },
                            label = { Text("Correo electrónico") }, singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            isError = resetAttempted && !(resetEmail.contains("@") && resetEmail.substringAfter("@", "").contains(".")),
                            supportingText = { if (resetAttempted && !(resetEmail.contains("@") && resetEmail.substringAfter("@", "").contains("."))) Text("Ingresa un correo válido") })
                        if (error != null) Text(error, color = MaterialTheme.colorScheme.error)
                    }
                }
            },
            confirmButton = {
                if (resetSent) TextButton(onClick = { showReset = false; onResetDismiss() }) { Text("Entendido") }
                else TextButton(enabled = !loading, onClick = {
                    resetAttempted = true
                    if (resetEmail.contains("@") && resetEmail.substringAfter("@", "").contains(".")) onResetPassword(resetEmail.trim())
                }) { Text(if (loading) "Enviando..." else "Enviar") }
            },
            dismissButton = { if (!resetSent) TextButton(enabled = !loading, onClick = { showReset = false; onResetDismiss() }) { Text("Cancelar") } }
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    GymMateTheme(darkTheme = false) {
        LoginScreen(onBack = {}, onLoginSuccess = {}, onRegister = {})
    }
}
