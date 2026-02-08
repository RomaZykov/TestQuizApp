package com.example.dailyquiztest.presentation

import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.domain.model.QuizTypeDomain
import com.example.dailyquiztest.fake.FakeFormatDate
import com.example.dailyquiztest.fake.FakeWelcomeRouteProvider
import com.example.dailyquiztest.presentation.feature.quiz.CalculateScore
import com.example.dailyquiztest.presentation.feature.quiz.QuizUiState
import com.example.dailyquiztest.presentation.feature.quiz.QuizViewModel
import com.example.dailyquiztest.presentation.feature.quiz.mapper.QuizUiMapper
import com.example.dailyquiztest.presentation.feature.quiz.model.FiltersUi
import com.example.dailyquiztest.presentation.feature.quiz.model.LoadingUi
import com.example.dailyquiztest.presentation.feature.quiz.model.ResultUi
import com.example.dailyquiztest.presentation.feature.quiz.model.QuizUi
import com.example.dailyquiztest.presentation.feature.quiz.model.small_screen.ErrorUiState
import com.example.dailyquiztest.presentation.feature.quiz.model.small_screen.QuizGroupUi
import com.example.testing.di.FakeDispatcherList
import com.example.testing.stub.stubTrueFalseQuizes
import com.example.testing.stub.stubDomainQuizes
import com.example.testing.repository.FakeHistoryRepository
import com.example.testing.repository.FakeQuizRepository
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
        viewModel = QuizViewModel(
            quizRepository = fakeQuizRepository,
            historyRepository = fakeHistoryRepository,
            welcomeRouteProvider = fakeWelcomeRouteProvider,
            dispatcherList = dispatchers,
            score = score,
            formatDate = fakeFormatDate,
            mapper = QuizUiMapper.Base()
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

            val expectedFinalState = QuizUi(
                number = 1,
                question = stubDomainQuizes[0].question,
                incorrectAnswers = stubDomainQuizes[0].incorrectAnswers,
                correctAnswer = stubDomainQuizes[0].correctAnswer,
                totalQuestions = stubDomainQuizes.size,
                quizGroupUi = QuizGroupUi.MultipleGroupUi(
                    question = stubDomainQuizes[0].question,
                    correctOption = stubDomainQuizes[0].correctAnswer,
                    inCorrectOptions = stubDomainQuizes[0].incorrectAnswers,
                    userAnswer = ""
                )
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

            var currentNumQuestion = 0
            val expectedQuizResults = mutableListOf<QuizUi>()
            viewModel.prepareQuizGame(
                CategoryDomain.CARTOON_AND_ANIMATIONS,
                DifficultyDomain.EASY
            )
            repeat(4) {
                val currentQuestion = retrieveDummyTrueFalseQuestionByIndex(currentNumQuestion)
                assertEquals(currentQuestion, stateFlow.value)

                val correctAnsweredQuestion = currentQuestion.copy(
                    userAnswer = "true",
                    isAnsweredCorrect = true
                )
                viewModel.saveQuizAnswer(correctAnsweredQuestion)
                expectedQuizResults.add(correctAnsweredQuestion)
                currentNumQuestion++

                viewModel.retrieveNextAnswer()
            }

            val finalQuestion = retrieveDummyTrueFalseQuestionByIndex(currentNumQuestion)
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

    private fun retrieveDummyTrueFalseQuestionByIndex(index: Int): QuizUi {
        return QuizUi(
            number = index,
            question = stubTrueFalseQuizes[index].question,
            incorrectAnswers = stubTrueFalseQuizes[index].incorrectAnswers,
            correctAnswer = stubTrueFalseQuizes[index].correctAnswer,
            totalQuestions = stubTrueFalseQuizes.size,
            userAnswer = stubTrueFalseQuizes[index].userAnswer,
            isAnsweredCorrect = stubTrueFalseQuizes[index].isAnsweredCorrect,
            quizGroupUi = QuizGroupUi.BooleanGroupUi(
                question = stubTrueFalseQuizes[index].question,
                correctOption = stubTrueFalseQuizes[index].correctAnswer,
                userAnswer = stubTrueFalseQuizes[index].userAnswer
            )
        )
    }
}
