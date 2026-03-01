package com.example.dailyquiztest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.dailyquiztest.presentation.feature.welcome.navigation.WelcomeRoute
import com.example.dailyquiztest.presentation.main_navigation.MainNavigation
import com.example.dailyquiztest.presentation.ui.DailyQuizeTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lightTransparentStyle = SystemBarStyle.light(
            scrim = Color.Transparent.value.toInt(),
            darkScrim = Color.Transparent.value.toInt()
        )
        enableEdgeToEdge(navigationBarStyle = lightTransparentStyle)
        setContent {
            DailyQuizeTestTheme {
                MainNavigation(
                    navController = rememberNavController(),
                    startDestination = WelcomeRoute
                )
            }
        }
    }
}
