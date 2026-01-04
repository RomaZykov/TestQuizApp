package com.example.dailyquiztest.data

import com.example.dailyquiztest.data.model.local.HistoryDao
import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import kotlinx.coroutines.flow.MutableStateFlow

class TestHistoryDao : HistoryDao {

    private val entities = MutableStateFlow(emptyList<LocalQuizResult>())

    override suspend fun deleteQuiz(quizResultId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getHistoryQuizResults(): List<LocalQuizResult> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(quizResult: LocalQuizResult) {
        TODO("Not yet implemented")
    }
}