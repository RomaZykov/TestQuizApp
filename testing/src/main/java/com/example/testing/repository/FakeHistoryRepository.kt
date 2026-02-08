package com.example.testing.repository

import com.example.dailyquiztest.domain.model.ResultDomain
import com.example.dailyquiztest.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeHistoryRepository @Inject constructor() : HistoryRepository {

    private val histories = mutableListOf<ResultDomain.Result>()

    override suspend fun saveQuizResult(result: ResultDomain.Result) {
        histories.add(result)
    }

    override suspend fun deleteQuizResult(id: Int) {
        histories.removeIf { it.number == id }
    }

    override fun fetchQuizResults(): Flow<List<ResultDomain.Result>> = flow {
        // Wrong - emit(histories) - link to the same object -> no updates for ui testing
        emit(histories.toList())
    }
}