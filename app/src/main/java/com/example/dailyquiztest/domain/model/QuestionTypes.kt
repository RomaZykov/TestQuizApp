package com.example.dailyquiztest.domain.model

//enum class QuestionTypes(val typeApi: String) {
//    MULTIPLE("multiple"),
//    BOOLEAN("boolean")
//}

interface QuestionTypes {
    val typeApi: String

    data class Multiple(override val typeApi: String = "multiple") : QuestionTypes
    data class Boolean(override val typeApi: String = "boolean") : QuestionTypes
}