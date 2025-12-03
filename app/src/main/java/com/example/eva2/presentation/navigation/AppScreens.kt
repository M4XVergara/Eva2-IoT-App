package com.example.eva2.presentation.navigation

sealed class AppScreens(val route: String) {
    object Splash : AppScreens("splash_screen")
    object Login : AppScreens("login_screen")
    object Home : AppScreens("home_screen")
    object Register : AppScreens("register_screen") // <--- Línea agregada
    object CrudUsers : AppScreens("crud_users_screen")
    object Sensors : AppScreens("sensors_screen")
    object Developer : AppScreens("developer_screen")
}