package com.example.testing.data.mapper

import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestion
import com.example.dailyquiztest.domain.model.QuestionTypeDomain
import com.example.dailyquiztest.domain.model.QuizDomain

class FakeNetworkToDomainQuizQuestionMapper : NetworkQuizQuestion.Mapper<QuizDomain> {
    var mapCalledCount = 0

    override fun mappedValue(
        question: String,
        incorrectAnswers: List<String>,
        correctAnswer: String,
        type: String
    ): QuizDomain {
        mapCalledCount++
        return QuizDomain(
            question = question,
            incorrectAnswers = incorrectAnswers,
            correctAnswer = correctAnswer,
            type = QuestionTypeDomain.entries.find { it.typeApi == type } ?: throw IllegalArgumentException("No found question type - $type")
        )
    }
}