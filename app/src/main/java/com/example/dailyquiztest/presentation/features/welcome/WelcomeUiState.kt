package com.example.dailyquiztest.presentation.features.welcome

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

interface WelcomeUiState {
    @Composable
    fun Display(
        innerPaddingValues: PaddingValues,
        onStartQuizClicked: () -> Unit
    )

    companion object {
        const val INITIAL_WELCOME_SCREEN = "initialWelcomeScreen"
    }
}
