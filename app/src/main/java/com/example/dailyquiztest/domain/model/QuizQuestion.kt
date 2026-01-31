package com.example.dailyquiztest.domain.model

data class QuizQuestion(
    val question: String,
    val incorrectAnswers: List<String>,
    val correctAnswer: String,
    val type: QuestionType
)
