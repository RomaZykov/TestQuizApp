package com.example.dailyquiztest.presentation.feature.quiz.mapper

import androidx.compose.runtime.mutableStateOf
import com.example.dailyquiztest.domain.model.QuizDomain
import com.example.dailyquiztest.domain.model.QuizDomain.Quiz
import com.example.dailyquiztest.domain.model.QuizTypeDomain
import com.example.dailyquiztest.presentation.common.quiz_group.QuizGroupUi
import com.example.dailyquiztest.presentation.feature.quiz.model.QuizUi
import javax.inject.Inject

interface QuizUiMapper : QuizDomain.MapTo<List<QuizUi>> {
    class Base @Inject constructor() : QuizUiMapper {
        override fun mapToListQuiz(list: List<Quiz>): List<QuizUi> {
            return list.mapIndexed { i, domain ->
                val question = domain.question
                val inCorrectAnswers = domain.incorrectAnswers
                val correctAnswer = domain.correctAnswer
                QuizUi(
                    number = i,
                    question = question,
                    correctAnswer = correctAnswer,
                    incorrectAnswers = inCorrectAnswers,
                    totalQuestions = list.size,
                    quizGroupUi = if (domain.type == QuizTypeDomain.BOOLEAN) {
                        QuizGroupUi.BooleanGroupUi(
                            question = question,
                            correctOption = correctAnswer,
                            userAnswer = "",
                            actionButtonEnabled = mutableStateOf(false)
                        )
                    } else {
                        QuizGroupUi.MultipleGroupUi(
                            question = question,
                            correctOption = correctAnswer,
                            inCorrectOptions = inCorrectAnswers,
                            userAnswer = "",
                            actionButtonEnabled = mutableStateOf(false)
                        )
                    }
                )
            }
        }
    }
}