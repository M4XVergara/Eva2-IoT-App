package com.example.eva2.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.eva2.R

// Definimos que esta pantalla recibe UNA función llamada onAnimationFinished
@Composable
fun SplashScreen(onAnimationFinished: () -> Unit) {

    // 1. Cargar animación (Asegúrate de tener animacion_splash.json en res/raw)
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animacion_splash))
    val progress by animateLottieCompositionAsState(composition)

    // 2. Navegar cuando termina
    LaunchedEffect(progress) {
        if (progress == 1f) {
            onAnimationFinished()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(300.dp)
        )
    }
}