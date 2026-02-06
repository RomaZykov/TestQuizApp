package com.example.dailyquiztest.presentation.features.quiz

import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.presentation.features.quiz.model.QuizUi

interface QuizUserActions {
    fun onFiltersPhaseNextButtonClicked(): (CategoryDomain, DifficultyDomain) -> Unit
    fun onNextClicked(): (QuizUi) -> Unit
    fun onBackClicked(): () -> Unit
    fun onResultClicked(): (QuizUi) -> Unit
    fun onStartNewQuizClicked(): () -> Unit

    object ForPreview : QuizUserActions {
        override fun onBackClicked(): () -> Unit = {}
        override fun onFiltersPhaseNextButtonClicked(): (CategoryDomain, DifficultyDomain) -> Unit =
            { _, _ -> }
        override fun onNextClicked(): (QuizUi) -> Unit = {}
        override fun onResultClicked(): (QuizUi) -> Unit = {}
        override fun onStartNewQuizClicked(): () -> Unit = {}
    }
}