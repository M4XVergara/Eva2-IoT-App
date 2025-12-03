package com.example.eva2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.eva2.presentation.navigation.AppNavigation // Importa tu navegación
import com.example.eva2.ui.theme.Eva2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Eva2Theme {
                AppNavigation() // <--- ¡Solo esto!
            }
        }
    }
}