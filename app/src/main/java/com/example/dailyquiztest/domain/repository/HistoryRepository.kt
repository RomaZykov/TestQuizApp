package com.example.dailyquiztest.domain.repository

import com.example.dailyquiztest.domain.model.ResultDomain
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun saveQuizResult(resultDomain: ResultDomain)

    suspend fun deleteQuizResult(id: Int)

    fun fetchQuizResults(): Flow<List<ResultDomain>>
}