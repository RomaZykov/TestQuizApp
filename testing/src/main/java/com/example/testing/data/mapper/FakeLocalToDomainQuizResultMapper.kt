package com.example.testing.data.mapper

import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.domain.model.ResultDomain

class FakeLocalToDomainQuizResultMapper : LocalQuizResult.Mapper<ResultDomain> {
    var mapCalledCount = 0

    override fun mappedValue(
        number: Int,
        stars: Int,
        categoryDomain: CategoryDomain,
        difficultyDomain: DifficultyDomain,
        lastTime: String,
        lastDate: String
    ): ResultDomain {
        mapCalledCount++
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