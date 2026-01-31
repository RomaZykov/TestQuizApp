package com.example.dailyquiztest.presentation.common.answers_group

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.dailyquiztest.domain.model.QuestionType

interface AnswersSpecificTypeFactory {

    fun createGroup(): AnswersGroup

    class Base(
        private val questionType: QuestionType,
        private val question: String,
        private val correctAnswers: List<String>,
        private val inCorrectAnswers: List<String>,
        private val checkedEnabled: Boolean,
        private val actionButtonEnabled: MutableState<Boolean> = mutableStateOf(true),
        private val userAnswers: List<String> = listOf("")
    ) : AnswersSpecificTypeFactory {

        override fun createGroup(): AnswersGroup {
            return when (questionType) {
                QuestionType.MULTIPLE -> {
                    CheckboxGroup(
                        question,
                        correctAnswers,
                        inCorrectAnswers,
                        checkedEnabled = checkedEnabled,
                        actionButtonEnabled,
                        userAnswers = userAnswers
                    )
                }

                QuestionType.BOOLEAN -> {
                    RadioButtonGroup(
                        question = question,
                        isCorrectOption = correctAnswers.first().toBoolean(),
                        checkedEnabled = checkedEnabled,
                        actionButtonEnabled,
                        if (userAnswers.first().isNotEmpty()) {
                            userAnswers.first().toBoolean()
                        } else {
                            null
                        }
                    )
                }

                else -> {
                    throw IllegalArgumentException("Unknown question type: ${questionType.typeApi}")
                }
            }
        }
    }
}