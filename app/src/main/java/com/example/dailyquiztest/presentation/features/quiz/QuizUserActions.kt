package com.example.dailyquiztest.presentation.features.quiz

import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.presentation.features.quiz.model.QuizUi

interface QuizUserActions {
    fun onFiltersPhaseNextButtonClicked(): (Category, Difficulty) -> Unit
    fun onNextClicked(): (QuizUi) -> Unit
    fun onBackClicked(): () -> Unit
    fun onResultClicked(): (QuizUi) -> Unit
    fun onStartNewQuizClicked(): () -> Unit

    companion object {
        val previewQuizUserActions = object : QuizUserActions {
            override fun onBackClicked(): () -> Unit = {}

            override fun onFiltersPhaseNextButtonClicked(): (Category, Difficulty) -> Unit =
                { _, _ -> }

            override fun onNextClicked(): (QuizUi) -> Unit = {}

            override fun onResultClicked(): (QuizUi) -> Unit = {}

            override fun onStartNewQuizClicked(): () -> Unit = {}
        }
    }
}