package com.example.testing.repository

import com.example.dailyquiztest.domain.model.QuizResult
import com.example.dailyquiztest.domain.repository.HistoryQuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeHistoryRepository @Inject constructor() : HistoryQuizRepository {

    private val histories = mutableListOf<QuizResult>()

    fun clearHistories() = histories.clear()

    override suspend fun saveQuizResult(quizResult: QuizResult) {
        histories.add(quizResult)
    }

    override suspend fun deleteQuizResult(id: Int) {
        histories.removeIf { it.id == id }
    }

    override fun fetchQuizResults(): Flow<List<QuizResult>> = flow {
        // Wrong - emit(histories) - link to the object the same -> no updates for ui testing
        emit(histories.toList())
    }
}