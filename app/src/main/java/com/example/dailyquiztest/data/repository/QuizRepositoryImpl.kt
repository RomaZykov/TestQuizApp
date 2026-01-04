package com.example.dailyquiztest.data.repository

import com.example.dailyquiztest.data.model.network.NetworkQuizQuestionsDataSource
import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestion
import com.example.dailyquiztest.domain.model.QuizQuestion
import com.example.dailyquiztest.domain.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val networkQuizQuestionsDataSource: NetworkQuizQuestionsDataSource,
    private val networkToDomainQuizQuestionMapper: NetworkQuizQuestion.Mapper<QuizQuestion>
) : QuizRepository {
    override suspend fun retrieveQuizQuestions(
        amount: Int,
        category: Int,
        difficulty: String
    ): Result<List<QuizQuestion>> {
        return networkQuizQuestionsDataSource.retrieveQuizQuestions(amount, category, difficulty).map {
            it.take(amount).map { networkQuiz ->
                networkQuiz.map(networkToDomainQuizQuestionMapper)
            }
        }
    }
}