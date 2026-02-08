package com.example.testing.data.mapper

import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.ResultDomain

class FakeDomainToLocalQuizResultMapper : ResultDomain.MapTo<LocalQuizResult> {
    var mapCalledCount = 0

    override fun mapToResult(result: ResultDomain.Result): LocalQuizResult {
        mapCalledCount++
        return LocalQuizResult(
            number = 0,
            stars = result.stars,
            category = result.categoryDomain.name,
            difficulty = result.difficultyDomain.name,
            lastTime = result.lastTime,
            lastDate = result.lastDate
        )
    }
}