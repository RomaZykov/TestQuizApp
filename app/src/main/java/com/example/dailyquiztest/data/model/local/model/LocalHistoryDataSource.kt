package com.example.dailyquiztest.data.model.local.model

import com.example.dailyquiztest.data.model.local.HistoryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface LocalHistoryDataSource {

    fun fetchQuizResults(): Flow<List<LocalQuizResult>>

    suspend fun addQuizResult(quiz: LocalQuizResult)

    suspend fun deleteQuiz(uid: Int)

    class Base @Inject constructor(private val dao: HistoryDao) : LocalHistoryDataSource {
        override fun fetchQuizResults(): Flow<List<LocalQuizResult>> = flow {
            emit(dao.getHistoryQuizResults())
        }

        override suspend fun addQuizResult(quiz: LocalQuizResult) {
            dao.insert(quiz)
        }

        override suspend fun deleteQuiz(uid: Int) {
            dao.deleteQuiz(uid)
        }
    }
}