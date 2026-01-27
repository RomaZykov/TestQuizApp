package com.example.dailyquiztest.presentation.common.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dailyquiztest.presentation.ui.DailyQuizTheme

@Composable
fun Loading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DailyQuizTheme.colorScheme.primary)
            .padding(top = 48.dp),
        verticalArrangement = Arrangement.Top
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            strokeWidth = 4.dp,
            color = DailyQuizTheme.colorScheme.secondary,
            trackColor = DailyQuizTheme.colorScheme.background
        )
    }
}
