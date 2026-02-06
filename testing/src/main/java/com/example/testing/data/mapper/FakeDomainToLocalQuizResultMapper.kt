package com.example.testing.data.mapper

import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.domain.model.ResultDomain

class FakeDomainToLocalQuizResultMapper : ResultDomain.Mapper<LocalQuizResult> {
        var mapCalledCount = 0

        override fun mappedValue(
            id: Int,
            stars: Int,
            categoryDomain: CategoryDomain,
            difficultyDomain: DifficultyDomain,
            lastTime: String,
            lastDate: String
        ): LocalQuizResult {
            mapCalledCount++
            return LocalQuizResult(
                quizResultNumber = id,
                stars = stars,
                category = categoryDomain.name,
                difficulty = difficultyDomain.name,
                lastTime = lastTime,
                lastDate = lastDate
            )
        }
    }