package com.example.testing.data.mapper

import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.domain.model.QuizResult

class FakeLocalToDomainQuizResultMapper : LocalQuizResult.Mapper<QuizResult> {
    var mapCalledCount = 0

    override fun mappedValue(
        quizResultNumber: Int,
        stars: Int,
        category: Category,
        difficulty: Difficulty,
        lastTime: String,
        lastDate: String
    ): QuizResult {
        mapCalledCount++
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