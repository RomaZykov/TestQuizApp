package com.example.dailyquiztest.presentation

import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.domain.model.QuestionTypes
import com.example.dailyquiztest.fake.FakeWelcomeRouteProvider
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState
import com.example.dailyquiztest.presentation.features.quiz.QuizViewModel
import com.example.dailyquiztest.presentation.features.quiz.model.FiltersUi
import com.example.dailyquiztest.presentation.features.quiz.model.QuizResultUi
import com.example.dailyquiztest.presentation.features.quiz.model.QuizUi
import com.example.testing.di.FakeDispatcherList
import com.example.testing.dummy.dummyQuizes
import com.example.testing.dummy.dummyTrueFalseQuizes
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
    private lateinit var dispatchers: FakeDispatcherList
    private lateinit var stateFlow: StateFlow<QuizUiState>

    @OptIn(ExperimentalCoroutinesApi::class)
    private var testDispatcher = UnconfinedTestDispatcher()

    @Before
    @OptIn(ExperimentalCoroutinesApi::class)
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeQuizRepository = FakeQuizRepository()
        fakeHistoryRepository = FakeHistoryRepository()
        fakeWelcomeRouteProvider = FakeWelcomeRouteProvider()
        dispatchers = FakeDispatcherList(testDispatcher)
        viewModel = QuizViewModel(
            quizRepository = fakeQuizRepository,
            historyRepository = fakeHistoryRepository,
            welcomeRouteProvider = fakeWelcomeRouteProvider,
            dispatcherList = dispatchers
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

            viewModel.prepareQuizGame(CategoriesTypes.FILM, DifficultiesTypes.HARD)
            advanceTimeBy(2500)
            assertEquals(
                com.example.dailyquiztest.presentation.features.quiz.model.LoadingUi,
                stateFlow.value
            )
            advanceTimeBy(2000)
            assertEquals(
                com.example.dailyquiztest.presentation.features.quiz.model.LoadingUi,
                stateFlow.value
            )
            advanceTimeBy(505)

            val expectedFinalState =
                retrieveDummySimpleQuestionByIndex(0, CategoriesTypes.FILM, DifficultiesTypes.HARD)
            assertEquals(expectedFinalState, stateFlow.value)
        }

    @Test
    fun `show failed filterState when there are some problems to advance in quiz stage`() =
        runTest {
            fakeQuizRepository.shouldSimulateError = true

            viewModel.prepareQuizGame(CategoriesTypes.FILM, DifficultiesTypes.HARD)

            val expectedState = FiltersUi(
                categories = CategoriesTypes.entries,
                difficulties = DifficultiesTypes.entries,
                shouldShowError = true
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
                CategoriesTypes.CARTOON_AND_ANIMATIONS,
                DifficultiesTypes.EASY
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
            questionType = QuestionTypes.BOOLEAN,
            totalQuestions = 5,
            category = CategoriesTypes.CARTOON_AND_ANIMATIONS,
            difficulty = DifficultiesTypes.EASY
        )
    }

    private fun retrieveDummySimpleQuestionByIndex(
        index: Int,
        category: CategoriesTypes,
        difficulty: DifficultiesTypes
    ): QuizUi {
        return QuizUi(
            currentNumberQuestion = index,
            question = dummyQuizes[index].question,
            incorrectAnswers = dummyQuizes[index].incorrectAnswers,
            correctAnswer = dummyQuizes[index].correctAnswer,
            questionType = QuestionTypes.entries.find { it.typeApi == dummyQuizes[index].type }
                ?: QuestionTypes.BOOLEAN,
            totalQuestions = difficulty.amountOfQuestions,
            category = category,
            difficulty = difficulty
        )
    }
}
