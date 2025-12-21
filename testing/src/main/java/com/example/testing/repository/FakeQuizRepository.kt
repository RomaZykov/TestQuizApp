package com.example.testing.repository

import com.example.dailyquiztest.domain.model.QuizQuestion
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.testing.dummy.dummyQuizes
import com.example.testing.dummy.dummyTrueFalseQuizes

class FakeQuizRepository : QuizRepository {
    var shouldSimulateError = false
    var shouldSimulateOnlyTrueFalseOptions = false

    val savedQuizes = mutableListOf<QuizQuestion>()

    override suspend fun retrieveQuizQuestions(
        amount: Int,
        category: Int,
        difficulty: String
    ): Result<List<QuizQuestion>> {
        return if (shouldSimulateError) {
            Result.failure(Exception())
        } else {
            val source = if (shouldSimulateOnlyTrueFalseOptions) {
                dummyTrueFalseQuizes
            } else {
                dummyQuizes
            }
            savedQuizes.addAll(source.take(amount))
            Result.success(savedQuizes)
        }
    }
}