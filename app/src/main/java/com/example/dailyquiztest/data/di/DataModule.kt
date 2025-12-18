package com.example.dailyquiztest.data.di

import com.example.dailyquiztest.data.repository.HistoryQuizRepositoryImpl
import com.example.dailyquiztest.data.repository.QuizRepositoryImpl
import com.example.dailyquiztest.domain.repository.HistoryQuizRepository
import com.example.dailyquiztest.domain.repository.QuizRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindHistoryQuizRepository(impl: HistoryQuizRepositoryImpl): HistoryQuizRepository

    @Binds
    abstract fun bindQuizRepository(impl: QuizRepositoryImpl): QuizRepository
}