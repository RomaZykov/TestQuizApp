package com.example.dailyquiztest.data.repository

import com.example.dailyquiztest.data.mapper.NetworkToDomainQuizQuestionMapper
import com.example.dailyquiztest.data.model.network.NetworkQuizDataSource
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.dailyquiztest.domain.model.QuizQuestion
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val networkQuizDataSource: NetworkQuizDataSource,
    private val networkToDomainQuizQuestionMapper: NetworkToDomainQuizQuestionMapper
) : QuizRepository {
    override suspend fun retrieveQuizQuestions(
        amount: Int,
        category: Int,
        difficulty: String
    ): Result<List<QuizQuestion>> {
        return networkQuizDataSource.retrieveQuizQuestions(amount, category, difficulty).map {
            it.map { networkQuiz ->
                networkQuiz.map(networkToDomainQuizQuestionMapper)
            }
        }
    }
}