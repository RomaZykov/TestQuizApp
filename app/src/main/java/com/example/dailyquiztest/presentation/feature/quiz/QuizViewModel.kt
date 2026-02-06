package com.example.dailyquiztest.presentation.feature.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyquiztest.core.DispatcherList
import com.example.dailyquiztest.core.FormatDate
import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.domain.model.ResultDomain
import com.example.dailyquiztest.domain.repository.HistoryRepository
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.dailyquiztest.presentation.feature.quiz.model.FiltersUi
import com.example.dailyquiztest.presentation.feature.quiz.model.LoadingUi
import com.example.dailyquiztest.presentation.feature.quiz.model.QuizUi
import com.example.dailyquiztest.presentation.feature.quiz.model.ResultUi
import com.example.dailyquiztest.presentation.feature.quiz.model.small_screen.ErrorUiState
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
        categoryDomain: CategoryDomain,
        difficultyDomain: DifficultyDomain
    ) {
        viewModelScope.launch(dispatcherList.io()) {
            uiStateMutable.value = LoadingUi
            quizRepository.retrieveQuizQuestions(
                amount = difficultyDomain.amountOfQuestions,
                category = categoryDomain.apiId,
                difficulty = difficultyDomain.toString()
            ).onSuccess {
                questions.addAll(it.mapIndexed { i, quizQuestion ->
                    QuizUi(
                        number = i,
                        question = quizQuestion.question,
                        incorrectAnswers = quizQuestion.incorrectAnswers,
                        correctAnswer = quizQuestion.correctAnswer,
                        quizTypeDomain = quizQuestion.type,
                        totalQuestions = it.size,
                        categoryDomain = categoryDomain,
                        difficultyDomain = difficultyDomain
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
        val resultScreen = ResultUi(quizAnswers = questions)
        uiStateMutable.value = resultScreen
        viewModelScope.launch(dispatcherList.io()) {
            historyRepository.saveQuizResult(
                ResultDomain(
                    stars = resultScreen.calculateStarsScoreResult(),
                    categoryDomain = questions.first().categoryDomain,
                    difficultyDomain = questions.first().difficultyDomain,
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
        categoryDomain: CategoryDomain,
        difficultyDomain: DifficultyDomain
    )

    fun timerProgress()

    fun navigateToWelcome(toWelcome: (Route) -> Unit)
}