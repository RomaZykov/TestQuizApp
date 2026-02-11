package com.example.dailyquiztest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.dailyquiztest.presentation.feature.welcome.navigation.WelcomeRoute
import com.example.dailyquiztest.presentation.main_navigation.MainNavigation
import com.example.dailyquiztest.presentation.ui.DailyQuizeTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
