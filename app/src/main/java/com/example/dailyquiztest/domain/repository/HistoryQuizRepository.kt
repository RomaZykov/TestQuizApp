package com.example.dailyquiztest.domain.repository

import com.example.dailyquiztest.domain.model.QuizResult
import kotlinx.coroutines.flow.Flow

interface HistoryQuizRepository {
    suspend fun saveQuizResult(quizResult: QuizResult)

    suspend fun deleteQuizResult(id: Int)

    fun fetchQuizResults(): Flow<List<QuizResult>>
}