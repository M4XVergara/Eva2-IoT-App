package com.example.eva2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eva2.presentation.crud.CrudScreen // <--- IMPORTANTE: Debe importar el archivo real
import com.example.eva2.presentation.developer.DeveloperScreen
import com.example.eva2.presentation.home.HomeScreen
import com.example.eva2.presentation.login.LoginScreen
import com.example.eva2.presentation.register.RegisterScreen
import com.example.eva2.presentation.sensor.SensorScreen
import com.example.eva2.presentation.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Splash.route
    ) {
        // 1. Splash
        composable(AppScreens.Splash.route) {
            SplashScreen(
                onAnimationFinished = {
                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(AppScreens.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // 2. Login
        composable(AppScreens.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppScreens.Home.route) {
                        popUpTo(AppScreens.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(AppScreens.Register.route)
                }
            )
        }

        // 3. Registro
        composable(AppScreens.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { navController.popBackStack() },
                onBackClick = { navController.popBackStack() }
            )
        }

        // 4. Home (Menú Principal)
        composable(AppScreens.Home.route) {
            HomeScreen(
                onNavigateToCrud = { navController.navigate(AppScreens.CrudUsers.route) },
                onNavigateToSensors = { navController.navigate(AppScreens.Sensors.route) },
                onNavigateToDeveloper = { navController.navigate(AppScreens.Developer.route) }
            )
        }

        // 5. Sensores
        composable(AppScreens.Sensors.route) {
            SensorScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // 6. Datos del Desarrollador
        composable(AppScreens.Developer.route) {
            DeveloperScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // 7. CRUD Usuarios (CONECTADO)
        composable(AppScreens.CrudUsers.route) {
            CrudScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

    } // Fin del NavHost
} // Fin de AppNavigation