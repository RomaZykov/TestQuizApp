package com.example.testing.stub

import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.domain.model.ResultDomain

val stubHistories = listOf(
    ResultDomain.Result(
        number = 1,
        stars = 0,
        categoryDomain = CategoryDomain.CARTOON_AND_ANIMATIONS,
        difficultyDomain = DifficultyDomain.EASY,
        lastTime = "00:00",
        lastDate = "2025"
    ),
    ResultDomain.Result(
        number = 2,
        stars = 0,
        categoryDomain = CategoryDomain.GENERAL_KNOWLEDGE,
        difficultyDomain = DifficultyDomain.EASY,
        lastTime = "00:00",
        lastDate = "2025"
    ),
    ResultDomain.Result(
        number = 3,
        stars = 2,
        categoryDomain = CategoryDomain.CARTOON_AND_ANIMATIONS,
        difficultyDomain = DifficultyDomain.EASY,
        lastTime = "00:00",
        lastDate = "2025"
    ),
    ResultDomain.Result(
        number = 4,
        stars = 3,
        categoryDomain = CategoryDomain.BOARD_GAMES,
        difficultyDomain = DifficultyDomain.MEDIUM,
        lastTime = "00:00",
        lastDate = "2025"
    ),
    ResultDomain.Result(
        number = 5,
        stars = 5,
        categoryDomain = CategoryDomain.HISTORY,
        difficultyDomain = DifficultyDomain.HARD,
        lastTime = "00:00",
        lastDate = "2025"
    ),
    ResultDomain.Result(
        number = 6,
        stars = 5,
        categoryDomain = CategoryDomain.HISTORY,
        difficultyDomain = DifficultyDomain.HARD,
        lastTime = "00:00",
        lastDate = "2025"
    ),
    ResultDomain.Result(
        number = 7,
        stars = 5,
        categoryDomain = CategoryDomain.BOARD_GAMES,
        difficultyDomain = DifficultyDomain.EASY,
        lastTime = "00:00",
        lastDate = "2025"
    )
)