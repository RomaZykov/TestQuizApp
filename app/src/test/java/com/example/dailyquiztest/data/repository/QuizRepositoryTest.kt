package com.example.dailyquiztest.data.repository

import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.domain.model.QuestionType
import com.example.dailyquiztest.domain.model.QuizQuestion
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.testing.data.mapper.FakeNetworkToDomainQuizQuestionMapper
import com.example.testing.data.network.FakeNetworkQuizQuestionsDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException
import kotlin.jvm.Throws
import kotlin.test.assertEquals

class QuizRepositoryTest {
    private lateinit var quizRepository: QuizRepository
    private lateinit var fakeNetworkQuizDataSource: FakeNetworkQuizQuestionsDataSource
    private lateinit var fakeNetworkToDomainQuizQuestionMapper: FakeNetworkToDomainQuizQuestionMapper

    @Before
    fun setup() {
        fakeNetworkQuizDataSource = FakeNetworkQuizQuestionsDataSource()
        fakeNetworkToDomainQuizQuestionMapper = FakeNetworkToDomainQuizQuestionMapper()
        quizRepository = QuizRepositoryImpl(
            networkQuizQuestionsDataSource = fakeNetworkQuizDataSource,
            networkToDomainQuizQuestionMapper = fakeNetworkToDomainQuizQuestionMapper
        )
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    @Throws(UnknownHostException::class)
    fun `quizRepository receives UnknownHostException`() = runTest(
        UnconfinedTestDispatcher()
    ) {
        fakeNetworkQuizDataSource.shouldSimulateNetworkError = true

        quizRepository.retrieveQuizQuestions(
            5, Category.GENERAL_KNOWLEDGE.apiId,
            Difficulty.HARD.toString()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `quizRepository retrieve needed count of correct related questions`() = runTest(
        UnconfinedTestDispatcher()
    ) {
        val actual = quizRepository.retrieveQuizQuestions(
            5, Category.FILM.apiId,
            Difficulty.EASY.toString()
        )

        assertEquals(5, fakeNetworkToDomainQuizQuestionMapper.mapCalledCount)
        assertEquals(
            listOf(
                QuizQuestion(
                    type = QuestionType.MULTIPLE,
                    question = "Question 1",
                    correctAnswer = "correct",
                    incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3")
                ),
                QuizQuestion(
                    type = QuestionType.MULTIPLE,
                    question = "Question 2",
                    correctAnswer = "correct",
                    incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3")
                ),
                QuizQuestion(
                    type = QuestionType.MULTIPLE,
                    question = "Question 3",
                    correctAnswer = "correct",
                    incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3")
                ),
                QuizQuestion(
                    type = QuestionType.BOOLEAN,
                    question = "Question 4",
                    correctAnswer = "True",
                    incorrectAnswers = listOf("False")
                ),
                QuizQuestion(
                    type = QuestionType.BOOLEAN,
                    question = "Question 5",
                    correctAnswer = "False",
                    incorrectAnswers = listOf("True")
                ),
            ),
            actual.getOrNull()
        )
    }
}