package com.example.dailyquiztest.presentation

import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.fake.FakeFormatDate
import com.example.dailyquiztest.fake.FakeWelcomeRouteProvider
import com.example.dailyquiztest.presentation.feature.quiz.core.CalculateScore
import com.example.dailyquiztest.presentation.feature.quiz.QuizUiState
import com.example.dailyquiztest.presentation.feature.quiz.QuizViewModel
import com.example.dailyquiztest.presentation.feature.quiz.core.Timer
import com.example.dailyquiztest.presentation.feature.quiz.mapper.QuizMapper
import com.example.dailyquiztest.presentation.feature.quiz.model.FiltersUi
import com.example.dailyquiztest.presentation.feature.quiz.model.LoadingUi
import com.example.dailyquiztest.presentation.feature.quiz.model.QuizUi
import com.example.dailyquiztest.presentation.feature.quiz.model.ResultUi
import com.example.dailyquiztest.presentation.feature.quiz.model.small_screen.ErrorUiState
import com.example.dailyquiztest.presentation.feature.quiz.model.small_screen.QuizGroupUi
import com.example.testing.di.FakeDispatcherList
import com.example.testing.repository.FakeHistoryRepository
import com.example.testing.repository.FakeQuizRepository
import com.example.testing.stub.stubDomainQuizes
import com.example.testing.stub.stubTrueFalseQuizes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuizViewModelTest {
    private lateinit var fakeQuizRepository: FakeQuizRepository
    private lateinit var fakeHistoryRepository: FakeHistoryRepository
    private lateinit var fakeWelcomeRouteProvider: FakeWelcomeRouteProvider
    private lateinit var fakeFormatDate: FakeFormatDate
    private lateinit var dispatchers: FakeDispatcherList


    private lateinit var viewModel: QuizViewModel
    private lateinit var stateFlow: StateFlow<QuizUiState>
    private lateinit var score: CalculateScore.All
    private lateinit var mapper: QuizMapper

    @OptIn(ExperimentalCoroutinesApi::class)
    private var testDispatcher = UnconfinedTestDispatcher()

    @Before
    @OptIn(ExperimentalCoroutinesApi::class)
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeFormatDate = FakeFormatDate()
        fakeQuizRepository = FakeQuizRepository()
        fakeHistoryRepository = FakeHistoryRepository()
        fakeWelcomeRouteProvider = FakeWelcomeRouteProvider()
        dispatchers = FakeDispatcherList(testDispatcher)
        score = CalculateScore.Base()
        mapper = QuizMapper.Base()
        viewModel = QuizViewModel(
            quizRepository = fakeQuizRepository,
            historyRepository = fakeHistoryRepository,
            welcomeRouteProvider = fakeWelcomeRouteProvider,
            dispatcherList = dispatchers,
            score = score,
            formatDate = fakeFormatDate,
            mapper = mapper
        )
        stateFlow = viewModel.uiState
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `show loadingState when it takes a 5 sec time to show quizState`() =
        runTest(testDispatcher) {
            fakeQuizRepository.shouldSimulateFiveSecDelay = true

            viewModel.prepareQuizGame(CategoryDomain.FILM, DifficultyDomain.HARD)
            advanceTimeBy(2500)
            assertEquals(
                LoadingUi,
                stateFlow.value
            )
            advanceTimeBy(2000)
            assertEquals(
                LoadingUi,
                stateFlow.value
            )
            advanceTimeBy(505)

            val question = "Test question 1"
            val incorrectAnswers = listOf("InCorrect 1", "InCorrect 2", "InCorrect 3")
            val correctAnswer = "Correct"
            val expectedFinalState = QuizUi(
                number = 1,
                question = question,
                correctAnswer = correctAnswer,
                totalQuestions = stubDomainQuizes.size,
                quizGroupUi = QuizGroupUi.MultipleGroupUi(
                    question = question,
                    correctOption = correctAnswer,
                    inCorrectOptions = incorrectAnswers,
                    userAnswer = ""
                ),
                timer = Timer.Initial
            )
            assertEquals(expectedFinalState, stateFlow.value)
        }

    @Test
    fun `show failed filterState when there is NoConnection error`() =
        runTest {
            fakeQuizRepository.shouldSimulateNetworkError = true

            viewModel.prepareQuizGame(CategoryDomain.FILM, DifficultyDomain.HARD)

            val expectedState = FiltersUi(
                errorSnackBar = ErrorUiState.ErrorUi("Check your connection!"),
            )
            assertEquals(expectedState, stateFlow.value)
        }

    @Test
    fun `show failed filterState when there is ServiceUnavailable error`() =
        runTest {
            fakeQuizRepository.shouldSimulateServiceUnavailableError = true

            viewModel.prepareQuizGame(CategoryDomain.FILM, DifficultyDomain.HARD)

            val expectedState = FiltersUi(
                errorSnackBar = ErrorUiState.ErrorUi("Error with code: 1"),
            )
            assertEquals(expectedState, stateFlow.value)
        }

    @Test
    fun `navigating work correct to welcome route`() =
        runTest {
            viewModel.navigateToWelcome {}

            assertTrue(fakeWelcomeRouteProvider.wasRouteCalled)
        }

    @Test
    fun `show correct state (4 answered correct and 1 failed) when all questions accidentally were true or false options`() =
        runTest {
            fakeQuizRepository.shouldSimulateOnlyTrueFalseOptions = true

            var curIndex = 0
            val expectedQuizResults = mutableListOf<QuizUi>()
            viewModel.prepareQuizGame(
                CategoryDomain.CARTOON_AND_ANIMATIONS,
                DifficultyDomain.EASY
            )
            repeat(4) {
                val currentQuestion = retrieveDummyTrueFalseQuestionByIndex(curIndex, 5)
                assertEquals(currentQuestion, stateFlow.value)

                val correctAnsweredQuestion = currentQuestion.copy(
                    userAnswer = "true",
                    isAnsweredCorrect = true
                )
                viewModel.saveQuizAnswer(correctAnsweredQuestion)
                expectedQuizResults.add(correctAnsweredQuestion)
                curIndex++

                viewModel.retrieveNextAnswer()
            }

            val finalQuestion = retrieveDummyTrueFalseQuestionByIndex(curIndex, 5)
            assertEquals(finalQuestion, stateFlow.value)

            val incorrectAnsweredQuestion = finalQuestion.copy(
                userAnswer = "false",
                isAnsweredCorrect = false
            )
            viewModel.saveQuizAnswer(incorrectAnsweredQuestion)
            expectedQuizResults.add(incorrectAnsweredQuestion)

            viewModel.showResult()
            val expectedResultUiState = ResultUi(expectedQuizResults, score)
            assertEquals(expectedResultUiState, stateFlow.value)
        }

    private fun retrieveDummyTrueFalseQuestionByIndex(index: Int, totalQuestions: Int): QuizUi {
        val question = stubTrueFalseQuizes[index].question.replaceFirstChar { it.uppercaseChar() }
        val correctAnswer = stubTrueFalseQuizes[index].correctAnswer.replaceFirstChar { it.uppercaseChar() }
        val userAnswer = stubTrueFalseQuizes[index].userAnswer.replaceFirstChar { it.uppercaseChar() }
        return QuizUi(
            number = index + 1,
            question = question,
            correctAnswer = correctAnswer,
            totalQuestions = totalQuestions,
            userAnswer = userAnswer,
            isAnsweredCorrect = stubTrueFalseQuizes[index].isAnsweredCorrect,
            timer = Timer.Initial,
            quizGroupUi = QuizGroupUi.BooleanGroupUi(
                question = question,
                correctOption = correctAnswer,
                userAnswer = userAnswer
            )
        )
    }
}
