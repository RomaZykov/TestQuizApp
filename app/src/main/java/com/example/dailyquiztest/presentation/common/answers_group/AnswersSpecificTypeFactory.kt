package com.example.dailyquiztest.presentation.common.answers_group

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.dailyquiztest.domain.model.QuestionTypes

interface AnswersSpecificTypeFactory {

    fun createGroup(): AnswersGroup

    class Base(
        private val questionTypes: QuestionTypes,
        private val question: String,
        private val correctAnswers: List<String>,
        private val inCorrectAnswers: List<String>,
        private val checkedEnabled: Boolean,
        private val actionButtonEnabled: MutableState<Boolean> = mutableStateOf(true),
        private val userAnswers: List<String> = listOf("")
    ) : AnswersSpecificTypeFactory {

        override fun createGroup(): AnswersGroup {
            return when (questionTypes) {
                is QuestionTypes.Multiple -> {
                    CheckboxGroup(
                        question,
                        correctAnswers,
                        inCorrectAnswers,
                        checkedEnabled = checkedEnabled,
                        actionButtonEnabled,
                        userAnswers = userAnswers
                    )
                }

                is QuestionTypes.Boolean -> {
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
                    throw IllegalArgumentException("Unknown question type: ${questionTypes.typeApi}")
                }
            }
        }
    }
}