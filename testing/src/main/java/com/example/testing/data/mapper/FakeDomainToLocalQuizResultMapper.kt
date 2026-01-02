package com.example.testing.data.mapper

import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.domain.model.QuizResult

class FakeDomainToLocalQuizResultMapper : QuizResult.Mapper<LocalQuizResult> {
        var mapCalledCount = 0

        override fun mappedValue(
            id: Int,
            stars: Int,
            categoriesTypes: CategoriesTypes,
            difficultiesTypes: DifficultiesTypes,
            lastTime: String,
            lastDate: String
        ): LocalQuizResult {
            mapCalledCount++
            return LocalQuizResult(
                quizResultNumber = id,
                stars = stars,
                category = categoriesTypes.name,
                difficulty = difficultiesTypes.name,
                lastTime = lastTime,
                lastDate = lastDate
            )
        }
    }