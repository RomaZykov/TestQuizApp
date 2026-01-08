package com.example.testing.data.network

import com.example.dailyquiztest.data.model.network.NetworkQuizQuestionsDataSource
import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestion
import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
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
                networkQuestions.filter { it.key.first == category && it.key.second.toString() == difficulty }.values.first()
            Result.success(result)
        }
    }

    private val networkQuestions = mutableMapOf(
        Pair(Category.FILM.apiId, Difficulty.EASY) to listOf(
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
        Pair(Category.VIDEO_GAMES.apiId, Difficulty.MEDIUM) to listOf(
            NetworkQuizQuestion()
        ),
        Pair(Category.BOARD_GAMES.apiId, Difficulty.HARD) to listOf(
            NetworkQuizQuestion()
        )
    )
}