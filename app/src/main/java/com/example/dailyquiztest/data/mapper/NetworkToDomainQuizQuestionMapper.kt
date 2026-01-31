package com.example.dailyquiztest.data.mapper

import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestion
import com.example.dailyquiztest.domain.model.QuestionType
import com.example.dailyquiztest.domain.model.QuizQuestion
import javax.inject.Inject

class NetworkToDomainQuizQuestionMapper @Inject constructor() :
    NetworkQuizQuestion.Mapper<QuizQuestion> {
    override fun mappedValue(
        question: String,
        incorrectAnswers: List<String>,
        correctAnswer: String,
        type: String
    ): QuizQuestion {
        return QuizQuestion(
            question = question,
            incorrectAnswers = incorrectAnswers.map { it.lowercase() },
            correctAnswer = correctAnswer.lowercase(),
            type = QuestionType.entries.find { types -> types.typeApi == type }
                ?: QuestionType.BOOLEAN
        )
    }
}