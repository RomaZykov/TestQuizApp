package com.example.dailyquiztest.data.model.network

import com.example.dailyquiztest.R
import com.example.dailyquiztest.core.StringProvider
import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestion
import java.net.UnknownHostException
import javax.inject.Inject

interface NetworkQuizQuestionsDataSource {

    suspend fun retrieveQuizQuestions(
        amount: Int,
        category: Int,
        difficulty: String
    ): Result<List<NetworkQuizQuestion>>

    class Base @Inject constructor(
        private val quizApi: QuizApi,
        private val stringProvider: StringProvider
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
                    throw UnknownHostException(
                        stringProvider.string(
                            R.string.network_error_exception,
                            code
                        )
                    )
                }
                val questions = questionsResponse.body()!!.result!!
                Result.success(questions)
            } catch (e: UnknownHostException) {
                Result.failure(e)
            }
        }
    }

    private companion object {
        private const val SUCCESS_CODE = 0
    }
}
