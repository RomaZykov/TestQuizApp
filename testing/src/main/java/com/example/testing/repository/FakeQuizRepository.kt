package com.example.testing.repository

import com.example.dailyquiztest.domain.model.QuizQuestion
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.testing.dummy.dummyQuizes

class FakeQuizRepository : QuizRepository {
    var shouldSimulateError = false
    override suspend fun retrieveQuizQuestions(
        amount: Int,
        category: Int,
        difficulty: String
    ): Result<List<QuizQuestion>> {
        return if (shouldSimulateError) {
            Result.failure(Exception())
        } else {
            Result.success(dummyQuizes.take(amount))
        }
    }
}