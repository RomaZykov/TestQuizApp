package com.example.dailyquiztest.domain.repository

import com.example.dailyquiztest.domain.model.QuizDomain

interface QuizRepository {

    suspend fun retrieveQuizQuestions(
        amount: Int,
        category: Int,
        difficulty: String
    ) : Result<List<QuizDomain>>
}
