package com.example.dailyquiztest.domain.model

interface QuizDomain {
    fun <T> map(mapper: MapTo<T>): T

    interface MapTo<T> {
        fun mapToListQuiz(list: List<Quiz>): T
    }

    data class Quiz(
        val question: String,
        val incorrectAnswers: List<String>,
        val correctAnswer: String,
        val type: QuizTypeDomain,
        val userAnswer: String = "",
        val isAnsweredCorrect: Boolean = false
    ) : QuizDomain {
        override fun <T> map(mapper: MapTo<T>): T {
            return mapper.mapToListQuiz(listOf(this))
        }
    }
}