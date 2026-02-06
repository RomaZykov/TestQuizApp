package com.example.dailyquiztest.presentation.feature.quiz.model.small_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

interface ErrorUiState {
    @Composable
    fun Display(modifier: Modifier = Modifier)

    object EmptyUi : ErrorUiState {
        @Composable
        override fun Display(modifier: Modifier) = Unit
    }

    data class ErrorUi(private val errorMessage: String) : ErrorUiState {
        @Composable
        override fun Display(modifier: Modifier) {
            Snackbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .then(modifier),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(errorMessage)
            }
        }
    }
}
