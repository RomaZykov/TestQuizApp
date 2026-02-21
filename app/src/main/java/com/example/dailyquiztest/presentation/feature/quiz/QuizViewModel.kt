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
import com.example.dailyquiztest.presentation.feature.quiz.core.CalculateScore
import com.example.dailyquiztest.presentation.feature.quiz.core.Timer
import com.example.dailyquiztest.presentation.feature.quiz.mapper.QuizMapper
import com.example.dailyquiztest.presentation.feature.quiz.model.LoadingUi
import com.example.dailyquiztest.presentation.feature.quiz.model.QuizUi
import com.example.dailyquiztest.presentation.feature.quiz.model.ResultUi
import com.example.dailyquiztest.presentation.feature.quiz.model.small_screen.ErrorUiState
import com.example.dailyquiztest.presentation.main_navigation.Route
import com.example.dailyquiztest.presentation.main_navigation.WelcomeRouteProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
    private val mapper: QuizMapper,
    private val score: CalculateScore.All,
    private val formatDate: FormatDate,
    private val dispatcherList: DispatcherList
) : ViewModel(), CoreVMActions {

    private val uiStateMutable = MutableStateFlow<QuizUiState>(mapper.mapToFilterInitial())
    val uiState: StateFlow<QuizUiState>
        get() = uiStateMutable.asStateFlow()

    private lateinit var timerJob: Job
    private val tickMutable = MutableStateFlow(0f)
    private val timerStateMutable =
        MutableStateFlow<Timer>(Timer.Initial)

    private lateinit var category: CategoryDomain
    private lateinit var difficulty: DifficultyDomain
    private val quizes: MutableList<QuizUi> = mutableListOf()
    private var currentQuizQuestion = 0

    override fun prepareQuizGame(
        categoryDomain: CategoryDomain,
        difficultyDomain: DifficultyDomain
    ) {
        category = categoryDomain
        difficulty = difficultyDomain
        viewModelScope.launch(dispatcherList.io()) {
            uiStateMutable.value = LoadingUi
            quizRepository.retrieveQuizQuestions(
                amount = difficultyDomain.amountOfQuestions,
                category = categoryDomain.apiId,
                difficulty = difficultyDomain.toString()
            ).onSuccess {
                quizes.addAll(mapper.mapToListQuiz(it))
                uiStateMutable.value = quizes[currentQuizQuestion]
                startTimer()
            }.onFailure {
                val filtersUi = mapper.mapToFilterWithError(it.message ?: "")
                uiStateMutable.value = filtersUi
                delay(2000)
                uiStateMutable.value = filtersUi.copy(errorSnackBar = ErrorUiState.EmptyUi)
            }
        }
    }

    override fun saveQuizAnswer(answeredQuiz: QuizUi) {
        quizes[currentQuizQuestion] = answeredQuiz
    }

    override fun startTimer() {
        val totalSeconds = difficulty.timeToComplete / 1000
        timerJob = viewModelScope.launch(dispatcherList.ui()) {
            for (tick in 1..totalSeconds) {
                delay(1000)
                tickMutable.value = tick.toFloat()
                timerStateMutable.value = Timer.TimerProgress(
                    tick = tickMutable.value,
                    difficulty = difficulty
                )
                uiStateMutable.value =
                    quizes[currentQuizQuestion].copy(timer = timerStateMutable.value)
            }
            stopTimer()
            uiStateMutable.value = quizes[currentQuizQuestion].copy(
                timer = Timer.TimeIsOverDialog(totalSeconds.toFloat(), difficulty)
            )
        }
    }

    override fun stopTimer() {
        timerJob.cancel()
    }

    override fun retrieveNextAnswer() {
        uiStateMutable.value = quizes[++currentQuizQuestion]
    }

    override fun showResult() {
        quizes.forEach { it.visit(score) }
        val resultScreen = ResultUi(quizes, score)
        uiStateMutable.value = resultScreen
        viewModelScope.launch(dispatcherList.io()) {
            historyRepository.saveQuizResult(
                ResultDomain.Result(
                    stars = score.calculateStarsScoreResult(),
                    categoryDomain = category,
                    difficultyDomain = difficulty,
                    lastTime = formatDate.timeFinished(),
                    lastDate = formatDate.dateFinished()
                )
            )
        }
    }

    override fun navigateToWelcome(toWelcome: (Route) -> Unit) {
        currentQuizQuestion = 0
        quizes.clear()
        toWelcome.invoke(welcomeRouteProvider.route())
    }
}

interface CoreVMActions {

    fun retrieveNextAnswer()

    fun showResult()

    fun saveQuizAnswer(answeredQuiz: QuizUi)

    fun prepareQuizGame(
        categoryDomain: CategoryDomain,
        difficultyDomain: DifficultyDomain
    )

    fun startTimer()
    fun stopTimer()

    fun navigateToWelcome(toWelcome: (Route) -> Unit)
}