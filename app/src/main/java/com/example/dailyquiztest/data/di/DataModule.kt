package com.example.dailyquiztest.data.di

import com.example.dailyquiztest.data.repository.HistoryRepositoryImpl
import com.example.dailyquiztest.data.repository.QuizRepositoryImpl
import com.example.dailyquiztest.domain.repository.HistoryRepository
import com.example.dailyquiztest.domain.repository.QuizRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindHistoryQuizRepository(impl: HistoryRepositoryImpl): HistoryRepository

    @Binds
    abstract fun bindQuizRepository(impl: QuizRepositoryImpl): QuizRepository
}