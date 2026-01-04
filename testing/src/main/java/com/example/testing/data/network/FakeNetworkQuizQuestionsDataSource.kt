package com.example.testing.data.network

import com.example.dailyquiztest.data.model.network.NetworkQuizQuestionsDataSource
import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestion
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import java.net.UnknownHostException

class FakeNetworkQuizQuestionsDataSource : NetworkQuizQuestionsDataSource {
    var shouldSimulateNetworkError = false

    override suspend fun retrieveQuizQuestions(
        amount: Int,
        category: Int,
        difficulty: String
    ): Result<List<NetworkQuizQuestion>> {
        return if (shouldSimulateNetworkError) {
            Result.failure(UnknownHostException())
        } else {
            val result =
                networkQuestions.filter { it.key.first == category && it.key.second == difficulty }.values.first()
            Result.success(result)
        }
    }

    private val networkQuestions = mutableMapOf(
        Pair(CategoriesTypes.FILM.apiId, DifficultiesTypes.EASY.levelApi) to listOf(
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
        Pair(CategoriesTypes.VIDEO_GAMES.apiId, DifficultiesTypes.MEDIUM.levelApi) to listOf(
            NetworkQuizQuestion()
        ),
        Pair(CategoriesTypes.BOARD_GAMES.apiId, DifficultiesTypes.HARD.levelApi) to listOf(
            NetworkQuizQuestion()
        )
    )
}