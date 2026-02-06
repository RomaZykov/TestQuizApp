package com.example.dailyquiztest.data.repository

import com.example.dailyquiztest.data.model.network.NetworkQuizQuestionsDataSource
import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestion
import com.example.dailyquiztest.domain.model.QuizDomain
import com.example.dailyquiztest.domain.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val networkQuizQuestionsDataSource: NetworkQuizQuestionsDataSource,
    private val networkToDomainQuizDomainMapper: NetworkQuizQuestion.Mapper<QuizDomain>
) : QuizRepository {
    override suspend fun retrieveQuizQuestions(
        amount: Int,
        category: Int,
        difficulty: String
    ): Result<List<QuizDomain>> {
        return networkQuizQuestionsDataSource.retrieveQuizQuestions(amount, category, difficulty).map {
            it.take(amount).map { networkQuiz ->
                networkQuiz.map(networkToDomainQuizDomainMapper)
            }
        }
    }
}