package com.example.testing.repository

import com.example.dailyquiztest.domain.model.QuizQuestion
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.testing.dummy.dummyQuizes

class FakeQuizRepository : QuizRepository {
    override suspend fun retrieveQuizQuestions(
        amount: Int,
        category: Int,
        difficulty: String
    ): Result<List<QuizQuestion>> {
        return Result.success(dummyQuizes.take(amount))
    }
}