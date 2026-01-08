package com.example.testing.data.local

import com.example.dailyquiztest.data.model.local.model.LocalHistoryDataSource
import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalHistoryDataSource : LocalHistoryDataSource {
    private val localHistories = mutableListOf<LocalQuizResult>()
    override suspend fun addQuizResult(quiz: LocalQuizResult) {
        localHistories.add(quiz)
    }

    override suspend fun deleteQuiz(uid: Int) {
        localHistories.removeIf { it.quizResultNumber == uid }
    }

    override fun fetchQuizResults(): Flow<List<LocalQuizResult>> = flow {
        emit(localHistories.toList())
    }
}