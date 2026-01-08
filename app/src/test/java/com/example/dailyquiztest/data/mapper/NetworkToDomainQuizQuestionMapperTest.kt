package com.example.dailyquiztest.data.mapper

import com.example.dailyquiztest.domain.model.QuestionTypes
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkToDomainQuizQuestionMapperTest {

    @Test
    fun `map from true-false options should return lowercase values with boolean type`() {
        val mapper = NetworkToDomainQuizQuestionMapper()
        val question = "test question"
        val incorrectAnswers = listOf("True")
        val correctAnswer = "False"
        val type = QuestionTypes.BOOLEAN

        val result = mapper.mappedValue(
            question = question,
            incorrectAnswers = incorrectAnswers,
            correctAnswer = correctAnswer,
            type = type.typeApi
        )

        assertEquals(question, result.question)
        assertEquals(listOf("true"), result.incorrectAnswers)
        assertEquals("false", result.correctAnswer)
        assertEquals(QuestionTypes.BOOLEAN.typeApi, result.type)
    }
}