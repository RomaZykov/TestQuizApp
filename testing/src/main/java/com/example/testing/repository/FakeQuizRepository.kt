package com.example.testing.repository

import com.example.dailyquiztest.core.DispatcherList
import com.example.dailyquiztest.domain.model.QuizQuestion
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.testing.di.FakeDispatcherList
import com.example.testing.dummy.dummyQuizes
import com.example.testing.dummy.dummyTrueFalseQuizes
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakeQuizRepository : QuizRepository {
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
                dummyQuizes
            }
            savedQuizes.addAll(source.take(amount))
            Result.success(savedQuizes)
        }
    }
}