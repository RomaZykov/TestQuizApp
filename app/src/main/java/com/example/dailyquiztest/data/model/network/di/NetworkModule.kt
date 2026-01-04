package com.example.dailyquiztest.data.model.network.di

import com.example.dailyquiztest.data.model.network.NetworkQuizQuestionsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkModule {
    @Binds
    abstract fun bindNetworkQuizDataSource(impl: NetworkQuizQuestionsDataSource.Base): NetworkQuizQuestionsDataSource
}