package com.example.testing.repository

import com.example.dailyquiztest.domain.model.ResultDomain
import com.example.dailyquiztest.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeHistoryRepository @Inject constructor() : HistoryRepository {

    private val histories = mutableListOf<ResultDomain>()

    override suspend fun saveQuizResult(resultDomain: ResultDomain) {
        histories.add(resultDomain)
    }

    override suspend fun deleteQuizResult(id: Int) {
        histories.removeIf { it.number == id }
    }

    override fun fetchQuizResults(): Flow<List<ResultDomain>> = flow {
        // Wrong - emit(histories) - link to the same object -> no updates for ui testing
        emit(histories.toList())
    }
}