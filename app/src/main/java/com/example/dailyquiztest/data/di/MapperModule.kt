package com.example.dailyquiztest.data.di

import com.example.dailyquiztest.data.mapper.DomainToLocalQuizResultMapper
import com.example.dailyquiztest.data.mapper.LocalToDomainQuizResultMapper
import com.example.dailyquiztest.data.mapper.NetworkToDomainQuizQuestionMapper
import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestion
import com.example.dailyquiztest.domain.model.QuizQuestion
import com.example.dailyquiztest.domain.model.QuizResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {
    @Provides
    @Singleton
    fun provideLocalToDomainQuizResultMapper(): LocalQuizResult.Mapper<QuizResult> =
        LocalToDomainQuizResultMapper()

    @Provides
    @Singleton
    fun provideDomainToLocalQuizResultMapper(): QuizResult.Mapper<LocalQuizResult> =
        DomainToLocalQuizResultMapper()

    @Provides
    @Singleton
    fun provideNetworkToDomainQuizQuestionMapper(): NetworkQuizQuestion.Mapper<QuizQuestion> =
        NetworkToDomainQuizQuestionMapper()
}