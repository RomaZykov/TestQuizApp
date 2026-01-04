package com.example.dailyquiztest.core

import com.example.dailyquiztest.data.di.DataModule
import com.example.dailyquiztest.domain.repository.HistoryRepository
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.testing.repository.FakeHistoryRepository
import com.example.testing.repository.FakeQuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DataModule::class])
object TestModule {

    @Provides
    @Singleton
    fun provideFakeHistoryRepo(): HistoryRepository = FakeHistoryRepository()

    @Provides
    @Singleton
    fun provideFakeQuizRepo(): QuizRepository = FakeQuizRepository()
}