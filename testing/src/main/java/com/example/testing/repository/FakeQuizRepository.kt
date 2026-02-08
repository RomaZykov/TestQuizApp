package com.example.testing.repository

import com.example.dailyquiztest.data.model.network.exception.NoInternetConnection
import com.example.dailyquiztest.data.model.network.exception.ServiceUnavailableException
import com.example.dailyquiztest.domain.model.QuizDomain
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.testing.stub.stubTrueFalseQuizes
import com.example.testing.stub.stubDomainQuizes
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeQuizRepository @Inject constructor() : QuizRepository {
    var shouldSimulateNetworkError = false
    var shouldSimulateServiceUnavailableError = false
    var shouldSimulateOnlyTrueFalseOptions = false
    var shouldSimulateFiveSecDelay = false

    val savedQuizes = mutableListOf<QuizDomain.Quiz>()

    override suspend fun retrieveQuizQuestions(
        amount: Int,
        category: Int,
        difficulty: String
    ): Result<List<QuizDomain.Quiz>> {
        return if (shouldSimulateNetworkError) {
            Result.failure(NoInternetConnection("Check your connection!"))
        } else if (shouldSimulateServiceUnavailableError) {
            Result.failure(ServiceUnavailableException("Error with code: 1"))
        } else {
            if (shouldSimulateFiveSecDelay) {
                delay(5000)
            }
            val source = if (shouldSimulateOnlyTrueFalseOptions) {
                stubTrueFalseQuizes
            } else {
                stubDomainQuizes
            }
            savedQuizes.addAll(source.take(amount))
            Result.success(savedQuizes)
        }
    }
}