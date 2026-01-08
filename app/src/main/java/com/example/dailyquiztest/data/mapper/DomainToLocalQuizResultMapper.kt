package com.example.dailyquiztest.data.mapper

import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.domain.model.QuizResult

class DomainToLocalQuizResultMapper() : QuizResult.Mapper<LocalQuizResult> {
    override fun mappedValue(
        id: Int,
        stars: Int,
        category: Category,
        difficulty: Difficulty,
        lastTime: String,
        lastDate: String
    ): LocalQuizResult {
        return LocalQuizResult(
            quizResultNumber = id,
            stars = stars,
            lastTime = lastTime,
            lastDate = lastDate,
            category = category.toString(),
            difficulty = difficulty.toString()
        )
    }
}