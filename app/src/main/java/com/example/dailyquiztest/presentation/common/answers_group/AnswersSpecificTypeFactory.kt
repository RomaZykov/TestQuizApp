package com.example.dailyquiztest.presentation.common.answers_group

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.dailyquiztest.domain.model.QuestionTypes

interface AnswersSpecificTypeFactory {

    fun createGroup(): AnswersGroup

    class Base(
        private val apiType: QuestionTypes,
        private val question: String,
        private val correctAnswers: List<String>,
        private val inCorrectAnswers: List<String>,
        private val checkedEnabled: Boolean,
        private val actionButtonEnabled: MutableState<Boolean> = mutableStateOf(true),
        private val userAnswers: List<String> = listOf("")

    ) : AnswersSpecificTypeFactory {

        override fun createGroup(): AnswersGroup {
            return when (apiType) {
                QuestionTypes.MULTIPLE -> {
                    CheckboxGroup(
                        question,
                        correctAnswers,
                        inCorrectAnswers,
                        checkedEnabled = true,
                        actionButtonEnabled,
                        userAnswers = userAnswers
                    )
                }

                QuestionTypes.BOOLEAN -> {
                    RadioButtonGroup(
                        question = question,
                        correctOption = correctAnswers.first().toBoolean(),
                        checkedEnabled = checkedEnabled,
                        actionButtonEnabled,
                        if (userAnswers.first().isNotEmpty()) {
                            userAnswers.first().toBoolean()
                        } else {
                            null
                        }
                    )
                }
            }
        }
    }
}