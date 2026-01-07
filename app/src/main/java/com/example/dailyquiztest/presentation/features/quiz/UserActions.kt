package com.example.dailyquiztest.presentation.features.quiz

import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.presentation.features.quiz.model.QuizUi

interface UserActions {
    fun onFiltersPhaseNextButtonClicked(): (CategoriesTypes, DifficultiesTypes) -> Unit
    fun onNextClicked(): (QuizUi) -> Unit
    fun onBackClicked(): () -> Unit
    fun onResultClicked(): (QuizUi) -> Unit
    fun onStartNewQuizClicked(): () -> Unit

    companion object {
        val previewUserActions = object : UserActions {
            override fun onBackClicked(): () -> Unit = {}

            override fun onFiltersPhaseNextButtonClicked(): (CategoriesTypes, DifficultiesTypes) -> Unit =
                { _, _ -> }

            override fun onNextClicked(): (QuizUi) -> Unit = {}

            override fun onResultClicked(): (QuizUi) -> Unit = {}

            override fun onStartNewQuizClicked(): () -> Unit = {}
        }
    }
}