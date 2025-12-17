package com.example.dailyquiztest.presentation.common.answers_group

import androidx.compose.runtime.Composable

interface AnswersGroup {
    @Composable
    fun DisplayGroup(shouldShowBorder: Boolean, updateQuiz: (List<String>) -> Unit)
}