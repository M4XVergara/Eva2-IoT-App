package com.example.eva2.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    // Si usas Emulador: 10.0.2.2
    // Si usas Celular Físico: IP de tu PC (ej: 192.168.1.50)
    private const val BASE_URL = "http://10.0.2.2:3000/"

    // Configuración especial para que Moshi entienda Kotlin
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // <--- Usamos la config aquí
            .build()
            .create(ApiService::class.java)
    }
}