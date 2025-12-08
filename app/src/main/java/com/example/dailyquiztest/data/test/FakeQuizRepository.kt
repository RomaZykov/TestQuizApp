package com.example.dailyquiztest.data.test

import com.example.dailyquiztest.domain.model.QuizQuestion
import com.example.dailyquiztest.domain.repository.QuizRepository

class FakeQuizRepository : QuizRepository {
    override suspend fun retrieveQuizQuestions(
        amount: Int,
        category: Int,
        difficulty: String
    ): Result<List<QuizQuestion>> {
        return Result.success(listOf())
    }
}