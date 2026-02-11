package com.example.dailyquiztest.data.mapper

import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.ResultDomain

class DomainToLocalResultMapper() : ResultDomain.MapTo<LocalQuizResult> {

    override fun mapToResult(result: ResultDomain.Result): LocalQuizResult {
        return LocalQuizResult(
            number = result.number,
            stars = result.stars,
            lastTime = result.lastTime,
            lastDate = result.lastDate,
            category = result.categoryDomain.toString(),
            difficulty = result.difficultyDomain.toString()
        )
    }
}