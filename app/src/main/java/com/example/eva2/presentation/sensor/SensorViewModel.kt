package com.example.eva2.presentation.sensor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eva2.data.remote.RetrofitClient
import com.example.eva2.data.remote.dto.SensorResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SensorViewModel : ViewModel() {

    var sensorData by mutableStateOf<SensorResponse?>(null)
    var isError by mutableStateOf(false)

    init {
        startPolling()
    }

    private fun startPolling() {
        viewModelScope.launch {
            while (true) {
                try {
                    val response = RetrofitClient.apiService.getSensorData()
                    if (response.isSuccessful) {
                        sensorData = response.body()
                        isError = false
                    }
                } catch (e: Exception) {
                    isError = true
                    println("Error obteniendo datos sensor: ${e.message}")
                }
                delay(3000) // Actualizar cada 3 segundos
            }
        }
    }
}