package com.example.eva2.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eva2.data.remote.RetrofitClient
import com.example.eva2.data.remote.dto.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var state by mutableStateOf<LoginState>(LoginState.Idle)

    fun login() {
        if (email.isBlank() || password.isBlank()) {
            state = LoginState.Error("Por favor completa los campos")
            return
        }

        viewModelScope.launch {
            state = LoginState.Loading
            try {
                val response = RetrofitClient.apiService.login(LoginRequest(email, password))
                if (response.isSuccessful && response.body()?.success == true) {
                    state = LoginState.Success(response.body()!!.token)
                } else {
                    state = LoginState.Error("Credenciales incorrectas")
                }
            } catch (e: Exception) {
                state = LoginState.Error("Error de conexión: ${e.message}")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}