package com.example.testing.repository

import com.example.dailyquiztest.domain.model.QuizQuestion
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.testing.dummy.stubQuizes
import com.example.testing.dummy.dummyTrueFalseQuizes
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeQuizRepository @Inject constructor() : QuizRepository {
    var shouldSimulateError = false
    var shouldSimulateOnlyTrueFalseOptions = false
    var shouldSimulateFiveSecDelay = false

    val savedQuizes = mutableListOf<QuizQuestion>()

    override suspend fun retrieveQuizQuestions(
        amount: Int,
        category: Int,
        difficulty: String
    ): Result<List<QuizQuestion>> {
        return if (shouldSimulateError) {
            Result.failure(Exception())
        } else {
            if (shouldSimulateFiveSecDelay) {
                delay(5000)
            }
            val source = if (shouldSimulateOnlyTrueFalseOptions) {
                dummyTrueFalseQuizes
            } else {
                stubQuizes
            }
            savedQuizes.addAll(source.take(amount))
            Result.success(savedQuizes)
        }
    }
}