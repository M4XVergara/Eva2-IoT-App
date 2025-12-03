package com.example.eva2.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eva2.data.remote.RetrofitClient
import com.example.eva2.data.remote.dto.RegisterRequest
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    // Variables para los campos de texto
    var name by mutableStateOf("")
    var lastName by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    // Estado de la pantalla
    var state by mutableStateOf<RegisterState>(RegisterState.Idle)

    fun register() {
        // Validaciones
        if (name.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            state = RegisterState.Error("Faltan datos")
            return
        }
        if (password != confirmPassword) {
            state = RegisterState.Error("Las contraseñas no coinciden")
            return
        }

        // Llamada a la API
        viewModelScope.launch {
            state = RegisterState.Loading
            try {
                val request = RegisterRequest(name, lastName, email, password)
                val response = RetrofitClient.apiService.register(request)

                if (response.isSuccessful) {
                    state = RegisterState.Success
                } else {
                    state = RegisterState.Error("Error: ${response.code()} - Posiblemente el correo ya existe")
                }
            } catch (e: Exception) {
                state = RegisterState.Error("Error de conexión: ${e.message}")
            }
        }
    }
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}
