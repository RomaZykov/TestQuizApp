package com.example.dailyquiztest.data.mapper

import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestion
import com.example.dailyquiztest.domain.model.QuizTypeDomain
import com.example.dailyquiztest.domain.model.QuizDomain
import javax.inject.Inject

class NetworkToDomainQuizQuestionMapper @Inject constructor() :
    NetworkQuizQuestion.Mapper<QuizDomain> {
    override fun mappedValue(
        question: String,
        incorrectAnswers: List<String>,
        correctAnswer: String,
        type: String
    ): QuizDomain {
        return QuizDomain(
            question = question,
            incorrectAnswers = incorrectAnswers.map { it.lowercase() },
            correctAnswer = correctAnswer.lowercase(),
            type = QuizTypeDomain.entries.find { types -> types.typeApi == type }
                ?: QuizTypeDomain.BOOLEAN
        )
    }
}