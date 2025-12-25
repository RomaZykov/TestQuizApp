package com.example.dailyquiztest.presentation

import com.example.dailyquiztest.core.FakeWelcomeRouteProvider
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.domain.model.QuestionTypes
import com.example.dailyquiztest.domain.repository.HistoryQuizRepository
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState
import com.example.dailyquiztest.presentation.features.quiz.QuizViewModel
import com.example.dailyquiztest.presentation.features.quiz.model.QuizResultUi
import com.example.dailyquiztest.presentation.features.quiz.model.QuizUi
import com.example.dailyquiztest.presentation.main_navigation.WelcomeRouteProvider
import com.example.testing.di.FakeDispatcherList
import com.example.testing.dummy.dummyTrueFalseQuizes
import com.example.testing.repository.FakeHistoryRepository
import com.example.testing.repository.FakeQuizRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class QuizViewModelTest {

    private lateinit var viewModel: QuizViewModel
    private lateinit var fakeQuizRepository: QuizRepository
    private lateinit var fakeHistoryRepository: HistoryQuizRepository
    private lateinit var fakeWelcomeRouteProvider: WelcomeRouteProvider
    private lateinit var dispatchers: FakeDispatcherList
    private lateinit var stateFlow: StateFlow<QuizUiState>

    @Before
    fun setup() {
        fakeQuizRepository = FakeQuizRepository()
        fakeHistoryRepository = FakeHistoryRepository()
        fakeWelcomeRouteProvider = FakeWelcomeRouteProvider()
        dispatchers = FakeDispatcherList()
        viewModel = QuizViewModel.Base(
            quizRepository = fakeQuizRepository,
            historyRepository = fakeHistoryRepository,
            welcomeRouteProvider = fakeWelcomeRouteProvider,
            dispatcherList = dispatchers
        )
        stateFlow = viewModel.quizUiStateFlow()
    }

    @Test
    fun `show correct state (4 answered correct and 1 failed) when all questions accidentally were true or false options`() =
        runTest {
            (fakeQuizRepository as FakeQuizRepository).shouldSimulateOnlyTrueFalseOptions = true

            var currentQuestion = 0
            val expectedQuizResults = mutableListOf<QuizUi>()
            viewModel.prepareQuizGame(
                CategoriesTypes.CARTOON_AND_ANIMATIONS,
                DifficultiesTypes.EASY
            )
            repeat(4) {
                val testAnswer = QuizUi(
                    currentNumberQuestion = currentQuestion,
                    question = dummyTrueFalseQuizes[currentQuestion].question,
                    incorrectAnswers = dummyTrueFalseQuizes[currentQuestion].incorrectAnswers,
                    correctAnswer = dummyTrueFalseQuizes[currentQuestion].correctAnswer,
                    questionType = QuestionTypes.BOOLEAN,
                    totalQuestions = 5,
                    category = CategoriesTypes.CARTOON_AND_ANIMATIONS,
                    difficulty = DifficultiesTypes.EASY
                )
                assertEquals(testAnswer, stateFlow.value)

                val correctAnsweredQuestion = testAnswer.copy(
                    userAnswers = listOf("true"),
                    isAnsweredCorrect = true
                )
                viewModel.saveQuizAnswers(correctAnsweredQuestion)
                expectedQuizResults.add(correctAnsweredQuestion)
                currentQuestion++

                viewModel.retrieveNextAnswer()
            }
            val testAnswer = QuizUi(
                currentNumberQuestion = currentQuestion,
                question = dummyTrueFalseQuizes[currentQuestion].question,
                incorrectAnswers = dummyTrueFalseQuizes[currentQuestion].incorrectAnswers,
                correctAnswer = dummyTrueFalseQuizes[currentQuestion].correctAnswer,
                questionType = QuestionTypes.BOOLEAN,
                totalQuestions = 5,
                category = CategoriesTypes.CARTOON_AND_ANIMATIONS,
                difficulty = DifficultiesTypes.EASY
            )
            assertEquals(testAnswer, stateFlow.value)

            val incorrectAnsweredQuestion = testAnswer.copy(
                userAnswers = listOf("false"),
                isAnsweredCorrect = false
            )
            viewModel.saveQuizAnswers(incorrectAnsweredQuestion)
            expectedQuizResults.add(incorrectAnsweredQuestion)

            viewModel.showResult()
            val expectedResultUiState = QuizResultUi(expectedQuizResults)
            assertEquals(expectedResultUiState, stateFlow.value)
        }
}
