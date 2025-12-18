package com.example.dailyquiztest.data.mapper

import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.domain.model.QuizResult

class DomainToLocalQuizResultMapper() : QuizResult.Mapper<LocalQuizResult> {
    override fun mappedValue(
        id: Int,
        stars: Int,
        categoriesTypes: CategoriesTypes,
        difficultiesTypes: DifficultiesTypes,
        lastTime: String,
        lastDate: String
    ): LocalQuizResult {
        return LocalQuizResult(
            stars = stars,
            lastTime = lastTime,
            lastDate = lastDate,
            categoriesTypes = categoriesTypes.toString(),
            difficultiesTypes = difficultiesTypes.toString()
        )
    }
}