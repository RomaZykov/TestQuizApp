package com.example.testing.data.mapper

import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.domain.model.QuizResult

class FakeLocalToDomainQuizResultMapper : LocalQuizResult.Mapper<QuizResult> {
    var mapCalledCount = 0

    override fun mappedValue(
        quizResultNumber: Int,
        stars: Int,
        categoriesTypes: CategoriesTypes,
        difficultiesTypes: DifficultiesTypes,
        lastTime: String,
        lastDate: String
    ): QuizResult {
        mapCalledCount++
        return QuizResult(
            id = quizResultNumber,
            stars = stars,
            categoriesTypes = categoriesTypes,
            difficultiesTypes = difficultiesTypes,
            lastTime = lastTime,
            lastDate = lastDate
        )
    }
}