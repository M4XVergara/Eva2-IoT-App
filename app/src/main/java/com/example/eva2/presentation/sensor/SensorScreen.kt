package com.example.eva2.presentation.sensor

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FlashlightOff
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorScreen(
    viewModel: SensorViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val data = viewModel.sensorData
    val context = LocalContext.current

    // Estados para la linterna y la ampolleta visual
    var isFlashlightOn by remember { mutableStateOf(false) }
    var isBulbOn by remember { mutableStateOf(false) }

    // Función para prender/apagar linterna real
    fun toggleFlashlight(turnOn: Boolean) {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, turnOn)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Monitor IoT") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- TARJETA DE SENSORES ---
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Datos en tiempo real", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (data != null) {
                        // TEMPERATURA
                        val isHot = data.temperature > 20
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                if (isHot) Icons.Default.WbSunny else Icons.Default.AcUnit,
                                null,
                                tint = if (isHot) Color(0xFFFF5722) else Color(0xFF03A9F4),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Temperatura", style = MaterialTheme.typography.labelMedium)
                                Text("${data.temperature}°C", style = MaterialTheme.typography.headlineMedium)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(16.dp))

                        // HUMEDAD
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.WaterDrop, null, tint = Color.Blue, modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Humedad", style = MaterialTheme.typography.labelMedium)
                                Text("${data.humidity}%", style = MaterialTheme.typography.headlineMedium)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Ultima lectura: ${data.timestamp}", style = MaterialTheme.typography.bodySmall)
                    } else {
                        CircularProgressIndicator()
                        Text("Esperando datos del servidor...")
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- BOTONES DE CONTROL ---
            Text("Control Manual", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // LINTERNA
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    FilledTonalIconButton(
                        onClick = {
                            isFlashlightOn = !isFlashlightOn
                            toggleFlashlight(isFlashlightOn)
                        },
                        modifier = Modifier.size(80.dp),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = if (isFlashlightOn) Color(0xFFFFEB3B) else Color.LightGray
                        )
                    ) {
                        Icon(
                            if (isFlashlightOn) Icons.Default.FlashlightOn else Icons.Default.FlashlightOff,
                            null,
                            modifier = Modifier.size(40.dp),
                            tint = Color.Black
                        )
                    }
                    Text("Linterna")
                }

                // AMPOLLETA (Simulación)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    FilledTonalIconButton(
                        onClick = { isBulbOn = !isBulbOn },
                        modifier = Modifier.size(80.dp),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = if (isBulbOn) Color(0xFFFFEB3B) else Color.LightGray
                        )
                    ) {
                        Icon(
                            Icons.Default.Lightbulb,
                            null,
                            modifier = Modifier.size(40.dp),
                            tint = Color.Black
                        )
                    }
                    Text("Luz Sala")
                }
            }
        }
    }
}