package com.example.dailyquiztest.data.di

import com.example.dailyquiztest.data.mapper.DomainToLocalResultMapper
import com.example.dailyquiztest.data.mapper.LocalToDomainResultMapper
import com.example.dailyquiztest.data.mapper.NetworkToDomainQuizMapper
import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestion
import com.example.dailyquiztest.domain.model.QuizDomain
import com.example.dailyquiztest.domain.model.ResultDomain
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
    fun provideLocalToDomainQuizResultMapper(): LocalQuizResult.Mapper<ResultDomain.Result> =
        LocalToDomainResultMapper()

    @Provides
    @Singleton
    fun provideDomainToLocalQuizResultMapper(): ResultDomain.MapTo<LocalQuizResult> =
        DomainToLocalResultMapper()

    @Provides
    @Singleton
    fun provideNetworkToDomainQuizQuestionMapper(): NetworkQuizQuestion.Mapper<QuizDomain.Quiz> =
        NetworkToDomainQuizMapper()
}