package com.example.dailyquiztest.data.mapper

import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.domain.model.QuizResult

class LocalToDomainQuizResultMapper() : LocalQuizResult.Mapper<QuizResult> {
    override fun mappedValue(
        quizResultNumber: Int,
        stars: Int,
        category: Category,
        difficulty: Difficulty,
        lastTime: String,
        lastDate: String
    ): QuizResult {
        return QuizResult(
            id = quizResultNumber,
            stars = stars,
            category = category,
            difficulty = difficulty,
            lastTime = lastTime,
            lastDate = lastDate
        )
    }
}