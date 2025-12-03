package com.example.eva2.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    onNavigateToCrud: () -> Unit,
    onNavigateToSensors: () -> Unit,
    onNavigateToDeveloper: () -> Unit
) {
    // Estado para la fecha/hora
    var currentTime by remember { mutableStateOf("") }

    // Efecto para actualizar la hora cada segundo (Requerimiento )
    LaunchedEffect(Unit) {
        while (true) {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            currentTime = sdf.format(Date())
            delay(1000L) // Esperar 1 segundo
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo o Título
        Text(text = "Menú Principal", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // Reloj en tiempo real
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Text(
                text = "Fecha/Hora: $currentTime",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Botones del Menú
        MenuButton(
            text = "CRUD Usuarios",
            icon = Icons.Default.Person,
            onClick = onNavigateToCrud
        )

        Spacer(modifier = Modifier.height(16.dp))

        MenuButton(
            text = "Datos Sensor",
            icon = Icons.Default.Sensors,
            onClick = onNavigateToSensors
        )

        Spacer(modifier = Modifier.height(16.dp))

        MenuButton(
            text = "Datos Desarrollador",
            icon = Icons.Default.Code,
            onClick = onNavigateToDeveloper
        )
    }
}

// Componente reutilizable para los botones
@Composable
fun MenuButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 18.sp)
    }
}