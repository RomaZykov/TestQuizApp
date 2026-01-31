package com.example.dailyquiztest.domain.model

enum class QuestionType(val typeApi: String) {
    MULTIPLE("multiple"),
    BOOLEAN("boolean"),
    UNKNOWN("unknown")
}