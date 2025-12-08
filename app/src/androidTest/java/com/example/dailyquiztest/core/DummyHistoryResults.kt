package com.example.dailyquiztest.core

import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.domain.model.QuizResult

val dummyHistoryResults = listOf(
    QuizResult(
        id = 0,
        stars = 0,
        categoriesTypes = CategoriesTypes.CARTOON_AND_ANIMATIONS,
        difficultiesTypes = DifficultiesTypes.EASY,
        lastTime = "00:00",
        lastDate = "2025"
    ),
        QuizResult(
            id = 1,
            stars = 0,
            categoriesTypes = CategoriesTypes.GENERAL_KNOWLEDGE,
            difficultiesTypes = DifficultiesTypes.EASY,
            lastTime = "00:00",
            lastDate = "2025"
        ),
        QuizResult(
            id = 2,
            stars = 2,
            categoriesTypes = CategoriesTypes.CARTOON_AND_ANIMATIONS,
            difficultiesTypes = DifficultiesTypes.EASY,
            lastTime = "00:00",
            lastDate = "2025"
        ),
        QuizResult(
            id = 3,
            stars = 3,
            categoriesTypes = CategoriesTypes.BOARD_GAMES,
            difficultiesTypes = DifficultiesTypes.MEDIUM,
            lastTime = "00:00",
            lastDate = "2025"
        ),
        QuizResult(
            id = 4,
            stars = 5,
            categoriesTypes = CategoriesTypes.HISTORY,
            difficultiesTypes = DifficultiesTypes.HARD,
            lastTime = "00:00",
            lastDate = "2025"
        )
    )