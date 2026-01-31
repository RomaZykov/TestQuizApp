package com.example.dailyquiztest.presentation

import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.domain.model.QuestionType
import com.example.dailyquiztest.fake.FakeFormatDate
import com.example.dailyquiztest.fake.FakeWelcomeRouteProvider
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState
import com.example.dailyquiztest.presentation.features.quiz.QuizViewModel
import com.example.dailyquiztest.presentation.features.quiz.model.FiltersUi
import com.example.dailyquiztest.presentation.features.quiz.model.LoadingUi
import com.example.dailyquiztest.presentation.features.quiz.model.QuizResultUi
import com.example.dailyquiztest.presentation.features.quiz.model.QuizUi
import com.example.dailyquiztest.presentation.features.quiz.model.small_screen.DialogUiState
import com.example.dailyquiztest.presentation.features.quiz.model.small_screen.ErrorUiState
import com.example.testing.di.FakeDispatcherList
import com.example.testing.dummy.dummyTrueFalseQuizes
import com.example.testing.dummy.stubQuizes
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

    private lateinit var viewModel: QuizViewModel
    private lateinit var fakeQuizRepository: FakeQuizRepository
    private lateinit var fakeHistoryRepository: FakeHistoryRepository
    private lateinit var fakeWelcomeRouteProvider: FakeWelcomeRouteProvider
    private lateinit var fakeFormatDate: FakeFormatDate
    private lateinit var dispatchers: FakeDispatcherList
    private lateinit var stateFlow: StateFlow<QuizUiState>

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
        viewModel = QuizViewModel(
            quizRepository = fakeQuizRepository,
            historyRepository = fakeHistoryRepository,
            welcomeRouteProvider = fakeWelcomeRouteProvider,
            dispatcherList = dispatchers,
            fakeFormatDate
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

            viewModel.prepareQuizGame(Category.FILM, Difficulty.HARD)
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

            val expectedFinalState =
                retrieveStubQuestionByIndex(0, Category.FILM, Difficulty.HARD)
            assertEquals(expectedFinalState, stateFlow.value)
        }

    @Test
    fun `show failed filterState when there is NoConnection error`() =
        runTest {
            fakeQuizRepository.shouldSimulateNetworkError = true

            viewModel.prepareQuizGame(Category.FILM, Difficulty.HARD)

            val expectedState = FiltersUi(
                errorSnackBar = ErrorUiState.ErrorUi("Check your connection!"),
            )
            assertEquals(expectedState, stateFlow.value)
        }

    @Test
    fun `show failed filterState when there is ServiceUnavailable error`() =
        runTest {
            fakeQuizRepository.shouldSimulateServiceUnavailableError = true

            viewModel.prepareQuizGame(Category.FILM, Difficulty.HARD)

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
                Category.CARTOON_AND_ANIMATIONS,
                Difficulty.EASY
            )
            repeat(4) {
                val currentQuestion = retrieveDummyTrueFalseQuestionByIndex(currentNumQuestion)
                assertEquals(currentQuestion, stateFlow.value)

                val correctAnsweredQuestion = currentQuestion.copy(
                    userAnswers = listOf("true"),
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
                userAnswers = listOf("false"),
                isAnsweredCorrect = false
            )
            viewModel.saveQuizAnswer(incorrectAnsweredQuestion)
            expectedQuizResults.add(incorrectAnsweredQuestion)

            viewModel.showResult()
            val expectedResultUiState = QuizResultUi(expectedQuizResults)
            assertEquals(expectedResultUiState, stateFlow.value)
        }

    private fun retrieveDummyTrueFalseQuestionByIndex(index: Int): QuizUi {
        return QuizUi(
            currentNumberQuestion = index,
            question = dummyTrueFalseQuizes[index].question,
            incorrectAnswers = dummyTrueFalseQuizes[index].incorrectAnswers,
            correctAnswer = dummyTrueFalseQuizes[index].correctAnswer,
            questionType = QuestionType.BOOLEAN,
            totalQuestions = 5,
            category = Category.CARTOON_AND_ANIMATIONS,
            difficulty = Difficulty.EASY,
            timerDialogUi = DialogUiState.NoDialog
        )
    }

    private fun retrieveStubQuestionByIndex(
        index: Int,
        category: Category,
        difficulty: Difficulty
    ): QuizUi {
        return QuizUi(
            currentNumberQuestion = index,
            question = stubQuizes[index].question,
            incorrectAnswers = stubQuizes[index].incorrectAnswers,
            correctAnswer = stubQuizes[index].correctAnswer,
            questionType = QuestionType.entries.find { it == stubQuizes[index].type }
                ?: QuestionType.BOOLEAN,
            totalQuestions = difficulty.amountOfQuestions,
            category = category,
            difficulty = difficulty,
            timerDialogUi = DialogUiState.NoDialog
        )
    }
}
