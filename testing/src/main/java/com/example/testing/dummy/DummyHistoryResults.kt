package com.example.testing.dummy

import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.domain.model.QuizResult

val dummyHistoryResults = listOf(
    QuizResult(
        id = 0,
        stars = 0,
        category = Category.CARTOON_AND_ANIMATIONS,
        difficulty = Difficulty.EASY,
        lastTime = "00:00",
        lastDate = "2025"
    ),
    QuizResult(
        id = 1,
        stars = 0,
        category = Category.GENERAL_KNOWLEDGE,
        difficulty = Difficulty.EASY,
        lastTime = "00:00",
        lastDate = "2025"
    ),
    QuizResult(
        id = 2,
        stars = 2,
        category = Category.CARTOON_AND_ANIMATIONS,
        difficulty = Difficulty.EASY,
        lastTime = "00:00",
        lastDate = "2025"
    ),
    QuizResult(
        id = 3,
        stars = 3,
        category = Category.BOARD_GAMES,
        difficulty = Difficulty.MEDIUM,
        lastTime = "00:00",
        lastDate = "2025"
    ),
    QuizResult(
        id = 4,
        stars = 5,
        category = Category.HISTORY,
        difficulty = Difficulty.HARD,
        lastTime = "00:00",
        lastDate = "2025"
    ),
    QuizResult(
        id = 5,
        stars = 5,
        category = Category.HISTORY,
        difficulty = Difficulty.HARD,
        lastTime = "00:00",
        lastDate = "2025"
    ),
    QuizResult(
        id = 6,
        stars = 5,
        category = Category.BOARD_GAMES,
        difficulty = Difficulty.EASY,
        lastTime = "00:00",
        lastDate = "2025"
    )
)