package com.example.dailyquiztest.data.mapper

import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.domain.model.QuizResult

class LocalToDomainQuizResultMapper() : LocalQuizResult.Mapper<QuizResult> {
    override fun mappedValue(
        quizResultNumber: Int,
        stars: Int,
        categoriesTypes: CategoriesTypes,
        difficultiesTypes: DifficultiesTypes,
        lastTime: String,
        lastDate: String
    ): QuizResult {
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