package com.example.dailyquiztest.data.mapper

import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.domain.model.ResultDomain

class DomainToLocalQuizResultMapper() : ResultDomain.Mapper<LocalQuizResult> {
    override fun mappedValue(
        id: Int,
        stars: Int,
        categoryDomain: CategoryDomain,
        difficultyDomain: DifficultyDomain,
        lastTime: String,
        lastDate: String
    ): LocalQuizResult {
        return LocalQuizResult(
            quizResultNumber = id,
            stars = stars,
            lastTime = lastTime,
            lastDate = lastDate,
            category = categoryDomain.toString(),
            difficulty = difficultyDomain.toString()
        )
    }
}