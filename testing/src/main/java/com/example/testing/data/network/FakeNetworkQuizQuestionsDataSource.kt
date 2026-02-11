package com.example.testing.data.network

import com.example.dailyquiztest.data.model.network.NetworkQuizQuestionsDataSource
import com.example.dailyquiztest.data.model.network.exception.NoInternetConnection
import com.example.dailyquiztest.data.model.network.exception.ServiceUnavailableException
import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestion
import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain

class FakeNetworkQuizQuestionsDataSource : NetworkQuizQuestionsDataSource {
    var shouldSimulateNetworkError = false
    var shouldSimulateServiceUnavailableError = false

    override suspend fun retrieveQuizQuestions(
        amount: Int,
        category: Int,
        difficulty: String
    ): Result<List<NetworkQuizQuestion>> {
        return if (shouldSimulateNetworkError) {
            Result.failure(NoInternetConnection("No connection"))
        } else if (shouldSimulateServiceUnavailableError) {
            Result.failure(ServiceUnavailableException("Service unavailable"))
        } else {
            val result =
                networkQuestions.filter { it.key.first == category && it.key.second.toString() == difficulty }.values.first()
            Result.success(result)
        }
    }

    private val networkQuestions = mutableMapOf(
        Pair(CategoryDomain.FILM.apiId, DifficultyDomain.EASY) to listOf(
            NetworkQuizQuestion(
                type = "multiple",
                question = "Question 1",
                correctAnswer = "correct",
                incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3")
            ),
            NetworkQuizQuestion(
                type = "multiple",
                question = "Question 2",
                correctAnswer = "correct",
                incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3")
            ),
            NetworkQuizQuestion(
                type = "multiple",
                question = "Question 3",
                correctAnswer = "correct",
                incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3")
            ),
            NetworkQuizQuestion(
                type = "boolean",
                question = "Question 4",
                correctAnswer = "True",
                incorrectAnswers = listOf("False")
            ),
            NetworkQuizQuestion(
                type = "boolean",
                question = "Question 5",
                correctAnswer = "False",
                incorrectAnswers = listOf("True")
            ),
            NetworkQuizQuestion(
                type = "boolean",
                question = "Question 6",
                correctAnswer = "False",
                incorrectAnswers = listOf("True")
            )
        ),
        Pair(CategoryDomain.VIDEO_GAMES.apiId, DifficultyDomain.MEDIUM) to listOf(
            NetworkQuizQuestion()
        ),
        Pair(CategoryDomain.BOARD_GAMES.apiId, DifficultyDomain.HARD) to listOf(
            NetworkQuizQuestion()
        )
    )
}