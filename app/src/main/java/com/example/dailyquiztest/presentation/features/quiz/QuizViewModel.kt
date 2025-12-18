package com.example.dailyquiztest.presentation.features.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.dailyquiztest.core.DispatcherList
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.domain.model.QuestionTypes
import com.example.dailyquiztest.domain.model.QuizResult
import com.example.dailyquiztest.domain.repository.HistoryQuizRepository
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.dailyquiztest.presentation.features.quiz.model.FiltersUi
import com.example.dailyquiztest.presentation.features.quiz.model.LoadingUi
import com.example.dailyquiztest.presentation.features.quiz.model.QuizResultUi
import com.example.dailyquiztest.presentation.features.quiz.model.QuizUi
import com.example.dailyquiztest.presentation.main_navigation.WelcomeRouteProvider
import com.example.dailyquiztest.presentation.main_navigation.navigateIfResumed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

interface QuizViewModel {

    fun quizUiStateFlow(): StateFlow<QuizUiState>

    fun retrieveNextAnswer()

    fun showResult()

    fun saveQuizAnswers(quizUi: QuizUi)

    fun prepareQuizGame(
        category: CategoriesTypes,
        difficulty: DifficultiesTypes
    )

    fun navigateToWelcome(navController: NavController) = Unit

    @HiltViewModel
    class Base @Inject constructor(
        private val quizRepository: QuizRepository,
        private val historyRepository: HistoryQuizRepository,
        private val welcomeRouteProvider: WelcomeRouteProvider,
        private val dispatcherList: DispatcherList
    ) : ViewModel(), QuizViewModel {
        private val uiState = MutableStateFlow<QuizUiState>(
            FiltersUi(
                categories = CategoriesTypes.entries.toList(),
                difficulties = DifficultiesTypes.entries.toList(),
                false
            )
        )
        private val questions: MutableList<QuizUi> = mutableListOf()
        private var currentQuizQuestion = 0

        override fun quizUiStateFlow(): StateFlow<QuizUiState> {
            return uiState.asStateFlow()
        }

        override fun prepareQuizGame(
            category: CategoriesTypes,
            difficulty: DifficultiesTypes
        ) {
            uiState.value = LoadingUi
            viewModelScope.launch(dispatcherList.io()) {
                quizRepository.retrieveQuizQuestions(
                    difficulty.amountOfQuestions,
                    category.apiId,
                    difficulty.levelApi
                ).onSuccess {
                    it.forEachIndexed { i, quiz ->
                        questions.add(
                            QuizUi(
                                currentNumberQuestion = i,
                                question = quiz.question,
                                incorrectAnswers = quiz.incorrectAnswers,
                                correctAnswer = quiz.correctAnswer,
                                questionType = QuestionTypes.entries.find { types -> types.typeApi == quiz.type }
                                    ?: QuestionTypes.BOOLEAN,
                                totalQuestions = it.size,
                                category = category,
                                difficulty = difficulty
                            ))
                    }
                    uiState.value = questions[currentQuizQuestion]
                }.onFailure {
                    uiState.value = FiltersUi(
                        categories = CategoriesTypes.entries.toList(),
                        difficulties = DifficultiesTypes.entries.toList(),
                        true
                    )
                }
            }
        }

        override fun saveQuizAnswers(quizUi: QuizUi) {
            questions[currentQuizQuestion] = quizUi
        }

        override fun retrieveNextAnswer() {
            uiState.update { questions[++currentQuizQuestion] }
        }

        override fun showResult() {
            val resultScreen = QuizResultUi(quizAnswers = questions)
            uiState.update { resultScreen }
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

        override fun navigateToWelcome(navController: NavController) {
            currentQuizQuestion = 0
            questions.clear()
            navController.navigateIfResumed(welcomeRouteProvider.route())
        }
    }
}