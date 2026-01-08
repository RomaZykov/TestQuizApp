package com.example.testing.data.mapper

import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.domain.model.QuizResult

class FakeDomainToLocalQuizResultMapper : QuizResult.Mapper<LocalQuizResult> {
        var mapCalledCount = 0

        override fun mappedValue(
            id: Int,
            stars: Int,
            category: Category,
            difficulty: Difficulty,
            lastTime: String,
            lastDate: String
        ): LocalQuizResult {
            mapCalledCount++
            return LocalQuizResult(
                quizResultNumber = id,
                stars = stars,
                category = category.name,
                difficulty = difficulty.name,
                lastTime = lastTime,
                lastDate = lastDate
            )
        }
    }