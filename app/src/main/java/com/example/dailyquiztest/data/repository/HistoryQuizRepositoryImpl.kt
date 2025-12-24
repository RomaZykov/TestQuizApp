package com.example.dailyquiztest.data.repository

import com.example.dailyquiztest.data.model.local.model.LocalHistoryDataSource
import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.repository.HistoryQuizRepository
import com.example.dailyquiztest.domain.model.QuizResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryQuizRepositoryImpl @Inject constructor(
    private val localHistoryDataSource: LocalHistoryDataSource,
    private val mapperFromLocalToDomain: LocalQuizResult.Mapper<QuizResult>,
    private val mapperFromDomainToLocal: QuizResult.Mapper<LocalQuizResult>,
) : HistoryQuizRepository {

    override fun fetchQuizResults(): Flow<List<QuizResult>> =
        localHistoryDataSource.fetchQuizResults().map { localQuizResults ->
            localQuizResults.map {
                it.map(mapperFromLocalToDomain)
            }
        }

    override suspend fun saveQuizResult(quizResult: QuizResult) {
        localHistoryDataSource.addQuizResult(quizResult.map(mapperFromDomainToLocal))
    }

    override suspend fun deleteQuizResult(id: Int) {
        localHistoryDataSource.deleteQuiz(id)
    }
}