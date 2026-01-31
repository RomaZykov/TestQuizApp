package com.example.dailyquiztest.presentation.features.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyquiztest.core.DispatcherList
import com.example.dailyquiztest.core.FormatDate
import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.domain.model.QuizResult
import com.example.dailyquiztest.domain.repository.HistoryRepository
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.dailyquiztest.presentation.features.quiz.model.small_screen.ErrorUiState
import com.example.dailyquiztest.presentation.features.quiz.model.FiltersUi
import com.example.dailyquiztest.presentation.features.quiz.model.LoadingUi
import com.example.dailyquiztest.presentation.features.quiz.model.QuizResultUi
import com.example.dailyquiztest.presentation.features.quiz.model.QuizUi
import com.example.dailyquiztest.presentation.features.quiz.model.small_screen.DialogUiState
import com.example.dailyquiztest.presentation.main_navigation.Route
import com.example.dailyquiztest.presentation.main_navigation.WelcomeRouteProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val historyRepository: HistoryRepository,
    private val welcomeRouteProvider: WelcomeRouteProvider,
    private val dispatcherList: DispatcherList,
    private val formattedDate: FormatDate
) : ViewModel(), CoreVMActions {

    private val uiStateMutable = MutableStateFlow<QuizUiState>(
        FiltersUi(
            errorSnackBar = ErrorUiState.EmptyUi,
        )
    )
    val uiState: StateFlow<QuizUiState>
        get() = uiStateMutable.asStateFlow()

    private val questions: MutableList<QuizUi> = mutableListOf()
    private var currentQuizQuestion = 0

    override fun prepareQuizGame(
        category: Category,
        difficulty: Difficulty
    ) {
        viewModelScope.launch(dispatcherList.io()) {
            uiStateMutable.value = LoadingUi
            quizRepository.retrieveQuizQuestions(
                amount = difficulty.amountOfQuestions,
                category = category.apiId,
                difficulty = difficulty.toString()
            ).onSuccess {
                questions.addAll(it.mapIndexed { i, quizQuestion ->
                    QuizUi(
                        currentNumberQuestion = i,
                        question = quizQuestion.question,
                        incorrectAnswers = quizQuestion.incorrectAnswers,
                        correctAnswer = quizQuestion.correctAnswer,
                        questionType = quizQuestion.type,
                        totalQuestions = it.size,
                        category = category,
                        difficulty = difficulty,
                        timerDialogUi = DialogUiState.NoDialog
                    )
                })
                uiStateMutable.value = questions[currentQuizQuestion]
            }.onFailure {
                val filtersUi = FiltersUi(
                    errorSnackBar = ErrorUiState.ErrorUi(it.message ?: "")
                )
                uiStateMutable.value = filtersUi
                delay(2000)
                uiStateMutable.value = filtersUi.copy(errorSnackBar = ErrorUiState.EmptyUi)
            }
        }
    }

    override fun saveQuizAnswer(quizUi: QuizUi) {
        questions[currentQuizQuestion] = quizUi
    }

    override fun retrieveNextAnswer() {
        uiStateMutable.value = questions[++currentQuizQuestion]
    }

    override fun showResult() {
        val resultScreen = QuizResultUi(quizAnswers = questions)
        uiStateMutable.value = resultScreen
        viewModelScope.launch(dispatcherList.io()) {
            historyRepository.saveQuizResult(
                QuizResult(
                    stars = resultScreen.calculateStarsScoreResult(),
                    category = questions.first().category,
                    difficulty = questions.first().difficulty,
                    lastTime = formattedDate.timeFinished(),
                    lastDate = formattedDate.dateFinished()
                )
            )
        }
    }

    override fun timerProgress() {
        TODO("Not yet implemented")
    }

    override fun navigateToWelcome(toWelcome: (Route) -> Unit) {
        currentQuizQuestion = 0
        questions.clear()
        toWelcome.invoke(welcomeRouteProvider.route())
    }
}

interface CoreVMActions {

    fun retrieveNextAnswer()

    fun showResult()

    fun saveQuizAnswer(quizUi: QuizUi)

    fun prepareQuizGame(
        category: Category,
        difficulty: Difficulty
    )

    fun timerProgress()

    fun navigateToWelcome(toWelcome: (Route) -> Unit)
}