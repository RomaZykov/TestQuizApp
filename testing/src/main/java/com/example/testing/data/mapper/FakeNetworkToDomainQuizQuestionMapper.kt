package com.example.testing.data.mapper

import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestion
import com.example.dailyquiztest.domain.model.QuizTypeDomain
import com.example.dailyquiztest.domain.model.QuizDomain

class FakeNetworkToDomainQuizQuestionMapper : NetworkQuizQuestion.Mapper<QuizDomain.Quiz> {
    var mapCalledCount = 0

    override fun mappedValue(
        question: String,
        incorrectAnswers: List<String>,
        correctAnswer: String,
        type: String
    ): QuizDomain.Quiz {
        mapCalledCount++
        return QuizDomain.Quiz(
            question = question,
            incorrectAnswers = incorrectAnswers,
            correctAnswer = correctAnswer,
            type = QuizTypeDomain.entries.find { it.typeApi == type } ?: throw IllegalArgumentException("No found question type - $type")
        )
    }
}