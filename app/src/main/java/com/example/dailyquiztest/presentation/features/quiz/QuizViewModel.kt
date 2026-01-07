package com.example.dailyquiztest.presentation.features.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyquiztest.core.DispatcherList
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.domain.model.QuestionTypes
import com.example.dailyquiztest.domain.model.QuizResult
import com.example.dailyquiztest.domain.repository.HistoryRepository
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.dailyquiztest.presentation.features.quiz.model.FiltersUi
import com.example.dailyquiztest.presentation.features.quiz.model.LoadingUi
import com.example.dailyquiztest.presentation.features.quiz.model.QuizResultUi
import com.example.dailyquiztest.presentation.features.quiz.model.QuizUi
import com.example.dailyquiztest.presentation.main_navigation.Route
import com.example.dailyquiztest.presentation.main_navigation.WelcomeRouteProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val historyRepository: HistoryRepository,
    private val welcomeRouteProvider: WelcomeRouteProvider,
    private val dispatcherList: DispatcherList
) : ViewModel(), CoreVMActions {

    private val uiStateMutable = MutableStateFlow<QuizUiState>(
        FiltersUi(
            categories = CategoriesTypes.entries.toList(),
            difficulties = DifficultiesTypes.entries.toList(),
            false
        )
    )
    val uiState: StateFlow<QuizUiState>
        get() = uiStateMutable.asStateFlow()

    private val questions: MutableList<QuizUi> = mutableListOf()
    private var currentQuizQuestion = 0

    override fun prepareQuizGame(
        category: CategoriesTypes,
        difficulty: DifficultiesTypes
    ) {
        uiStateMutable.value = LoadingUi
        viewModelScope.launch(dispatcherList.io()) {
            quizRepository.retrieveQuizQuestions(
                difficulty.amountOfQuestions,
                category.apiId,
                difficulty.levelApi
            ).onSuccess {
                questions.addAll(it.mapIndexed { i, quizQuestion ->
                    QuizUi(
                        currentNumberQuestion = i,
                        question = quizQuestion.question,
                        incorrectAnswers = quizQuestion.incorrectAnswers,
                        correctAnswer = quizQuestion.correctAnswer,
                        questionType = QuestionTypes.entries.find { types -> types.typeApi == quizQuestion.type }
                            ?: QuestionTypes.BOOLEAN,
                        totalQuestions = it.size,
                        category = category,
                        difficulty = difficulty
                    )
                })
                uiStateMutable.value = questions[currentQuizQuestion]
            }.onFailure {
                uiStateMutable.value = FiltersUi(
                    categories = CategoriesTypes.entries.toList(),
                    difficulties = DifficultiesTypes.entries.toList(),
                    true
                )
            }
        }
    }

    override fun saveQuizAnswer(quizUi: QuizUi) {
        questions[currentQuizQuestion] = quizUi
    }

    override fun retrieveNextAnswer() {
        uiStateMutable.update { questions[++currentQuizQuestion] }
    }

    override fun showResult() {
        val resultScreen = QuizResultUi(quizAnswers = questions)
        uiStateMutable.update { resultScreen }
        viewModelScope.launch(dispatcherList.io()) {
            historyRepository.saveQuizResult(
                QuizResult(
                    stars = resultScreen.calculatedStarsScoreResult(),
                    categoriesTypes = questions.first().category,
                    difficultiesTypes = questions.first().difficulty,
                    lastTime = resultScreen.timeFinished(),
                    lastDate = resultScreen.dateFinished()
                )
            )
        }
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
        category: CategoriesTypes,
        difficulty: DifficultiesTypes
    )

    fun navigateToWelcome(toWelcome: (Route) -> Unit)
}