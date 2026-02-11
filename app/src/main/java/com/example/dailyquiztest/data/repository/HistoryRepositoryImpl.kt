package com.example.dailyquiztest.data.repository

import com.example.dailyquiztest.data.model.local.model.LocalHistoryDataSource
import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.repository.HistoryRepository
import com.example.dailyquiztest.domain.model.ResultDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val localHistoryDataSource: LocalHistoryDataSource,
    private val mapperFromLocalToDomain: LocalQuizResult.Mapper<ResultDomain.Result>,
    private val mapperFromDomainToLocal: ResultDomain.MapTo<LocalQuizResult>,
) : HistoryRepository {

    override fun fetchQuizResults(): Flow<List<ResultDomain.Result>> =
        localHistoryDataSource.fetchQuizResults().map { localQuizResults ->
            localQuizResults.map {
                it.map(mapperFromLocalToDomain)
            }
        }

    override suspend fun saveQuizResult(result: ResultDomain.Result) {
        localHistoryDataSource.addQuizResult(result.map(mapperFromDomainToLocal))
    }

    override suspend fun deleteQuizResult(id: Int) {
        localHistoryDataSource.deleteQuiz(id)
    }
}