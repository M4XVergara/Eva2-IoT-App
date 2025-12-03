package com.example.eva2.data.remote

import com.example.eva2.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    // Autenticación
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    // --- ¡ESTA ES LA LÍNEA QUE TE FALTA! ---
    @GET("iot/data")
    suspend fun getSensorData(): Response<SensorResponse>
}