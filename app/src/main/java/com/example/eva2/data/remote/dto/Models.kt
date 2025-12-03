package com.example.eva2.data.remote.dto

// --- LOGIN ---
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean,
    val token: String,
    val user: UserDto
)

// --- REGISTRO ---
data class RegisterRequest(
    val name: String,
    val last_name: String, // Importante: Tu backend pide 'last_name' con guion bajo
    val email: String,
    val password: String
)

data class RegisterResponse(
    val message: String,
    val user: UserDto?
)

// --- USUARIO ---
data class UserDto(
    val id: Int,
    val name: String,
    val email: String
)

// --- IOT (SENSORES) ---
// (Necesario para que no te de error el ApiService)
data class SensorResponse(
    val temperature: Double,
    val humidity: Double,
    val timestamp: String
)
