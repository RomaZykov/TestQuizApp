package com.example.dailyquiztest.data.model.network

import com.example.dailyquiztest.core.exception.QuizHandleError
import com.example.dailyquiztest.data.model.network.exception.ServiceUnavailableException
import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestion
import javax.inject.Inject

interface NetworkQuizQuestionsDataSource {

    suspend fun retrieveQuizQuestions(
        amount: Int,
        category: Int,
        difficulty: String
    ): Result<List<NetworkQuizQuestion>>

    class Base @Inject constructor(
        private val quizApi: QuizApi,
        private val handleException: QuizHandleError,
    ) : NetworkQuizQuestionsDataSource {
        override suspend fun retrieveQuizQuestions(
            amount: Int,
            category: Int,
            difficulty: String
        ): Result<List<NetworkQuizQuestion>> {
            return try {
                val questionsResponse = quizApi.fetchQuizQuestions(amount, category, difficulty)
                val code = questionsResponse.body()!!.responseCode
                if (code != SUCCESS_CODE) {
                    throw ServiceUnavailableException(code.toString())
                }
                val questions = questionsResponse.body()!!.result!!
                Result.success(questions)
            } catch (e: Exception) {
                Result.failure(handleException.handle(e))
            }
        }
    }

    private companion object {
        private const val SUCCESS_CODE = 0
    }
}
