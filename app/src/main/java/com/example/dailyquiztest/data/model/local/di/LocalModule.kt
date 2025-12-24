package com.example.dailyquiztest.data.model.local.di

import com.example.dailyquiztest.data.model.local.model.LocalHistoryDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalModule {
    @Binds
    abstract fun bindLocalHistoryDataSource(impl: LocalHistoryDataSource.Base): LocalHistoryDataSource
}