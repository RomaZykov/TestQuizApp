package com.example.dailyquiztest.data.model.network

import com.example.dailyquiztest.R
import com.example.dailyquiztest.core.Const
import com.example.dailyquiztest.core.ProvideString
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
        private val provideString: ProvideString
    ) : NetworkQuizQuestionsDataSource {
        override suspend fun retrieveQuizQuestions(
            amount: Int,
            category: Int,
            difficulty: String
        ): Result<List<NetworkQuizQuestion>> {
            return try {
                val questionsResponse = quizApi.fetchQuizQuestions(amount, category, difficulty)
                val code = questionsResponse.body()!!.responseCode
                if (code.toString() != SuccessCode.toString()) {
                    throw UnknownHostException(
                        provideString.string(
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

    private object SuccessCode : Const.Base(0.toString())
}
