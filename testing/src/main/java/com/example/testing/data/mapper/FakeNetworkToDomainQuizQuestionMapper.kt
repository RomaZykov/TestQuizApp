package com.example.testing.data.mapper

import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestion
import com.example.dailyquiztest.domain.model.QuestionType
import com.example.dailyquiztest.domain.model.QuizQuestion

class FakeNetworkToDomainQuizQuestionMapper : NetworkQuizQuestion.Mapper<QuizQuestion> {
    var mapCalledCount = 0

    override fun mappedValue(
        question: String,
        incorrectAnswers: List<String>,
        correctAnswer: String,
        type: String
    ): QuizQuestion {
        mapCalledCount++
        return QuizQuestion(
            question = question,
            incorrectAnswers = incorrectAnswers,
            correctAnswer = correctAnswer,
            type = QuestionType.entries.find { it.typeApi == type } ?: QuestionType.UNKNOWN
        )
    }
}