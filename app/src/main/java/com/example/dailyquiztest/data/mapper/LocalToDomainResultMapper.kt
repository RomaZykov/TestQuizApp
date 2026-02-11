package com.example.dailyquiztest.data.mapper

import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.domain.model.ResultDomain

class LocalToDomainResultMapper() : LocalQuizResult.Mapper<ResultDomain.Result> {
    override fun mappedValue(
        number: Int,
        stars: Int,
        categoryDomain: CategoryDomain,
        difficultyDomain: DifficultyDomain,
        lastTime: String,
        lastDate: String
    ): ResultDomain.Result {
        return ResultDomain.Result(
            number = number,
            stars = stars,
            categoryDomain = categoryDomain,
            difficultyDomain = difficultyDomain,
            lastTime = lastTime,
            lastDate = lastDate
        )
    }
}