package com.example.dailyquiztest.presentation.feature.quiz.mapper

import com.example.dailyquiztest.domain.model.QuizDomain
import com.example.dailyquiztest.domain.model.QuizDomain.Quiz
import com.example.dailyquiztest.domain.model.QuizTypeDomain
import com.example.dailyquiztest.presentation.feature.quiz.core.Timer
import com.example.dailyquiztest.presentation.feature.quiz.model.FiltersUi
import com.example.dailyquiztest.presentation.feature.quiz.model.QuizUi
import com.example.dailyquiztest.presentation.feature.quiz.model.small_screen.ErrorUiState
import com.example.dailyquiztest.presentation.feature.quiz.model.small_screen.QuizGroupUi
import javax.inject.Inject

interface QuizMapper : QuizDomain.MapTo<List<QuizUi>> {

    fun mapToFilterInitial(): FiltersUi

    fun mapToFilterWithError(errorMessage: String): FiltersUi

    class Base @Inject constructor() : QuizMapper {
        override fun mapToFilterWithError(errorMessage: String): FiltersUi {
            return FiltersUi(errorSnackBar = ErrorUiState.ErrorUi(errorMessage))
        }

        override fun mapToFilterInitial(): FiltersUi {
            return FiltersUi(errorSnackBar = ErrorUiState.EmptyUi)
        }

        override fun mapToListQuiz(list: List<Quiz>): List<QuizUi> {
            return list.mapIndexed { i, domain ->
                val question = domain.question
                val inCorrectAnswers = domain.incorrectAnswers.map { word -> word.replaceFirstChar { it.uppercaseChar() } }
                val correctAnswer = domain.correctAnswer.replaceFirstChar { it.uppercaseChar() }
                val userAnswer = domain.userAnswer
                val isAnsweredCorrect = domain.isAnsweredCorrect
                QuizUi(
                    number = i + 1,
                    question = question,
                    correctAnswer = correctAnswer,
                    totalQuestions = list.size,
                    userAnswer = userAnswer,
                    isAnsweredCorrect = isAnsweredCorrect,
                    timer = Timer.Initial,
                    quizGroupUi = if (domain.type == QuizTypeDomain.BOOLEAN) {
                        QuizGroupUi.BooleanGroupUi(
                            question = question,
                            correctOption = correctAnswer,
                            userAnswer = userAnswer
                        )
                    } else {
                        QuizGroupUi.MultipleGroupUi(
                            question = question,
                            correctOption = correctAnswer,
                            inCorrectOptions = inCorrectAnswers,
                            userAnswer = userAnswer
                        )
                    },
                )
            }
        }
    }
}