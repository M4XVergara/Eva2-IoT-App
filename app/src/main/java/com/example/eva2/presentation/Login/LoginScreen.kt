package com.example.eva2.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// Aquí definimos los DOS caminos que puede tomar esta pantalla: Login Exitoso o Ir a Registro
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit // <--- ESTA LÍNEA ES CRÍTICA PARA ARREGLAR EL ERROR
) {
    val state = viewModel.state

    LaunchedEffect(state) {
        if (state is LoginState.Success) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bienvenido a Eva2", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (state is LoginState.Loading) {
            CircularProgressIndicator()
        } else {
            Button(onClick = { viewModel.login() }, modifier = Modifier.fillMaxWidth()) {
                Text("Ingresar")
            }

            // Botón para ir al registro
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { onNavigateToRegister() }) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }

        if (state is LoginState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = state.message, color = Color.Red)
        }
    }
}