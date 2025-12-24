package com.example.dailyquiztest.core.di

import com.example.dailyquiztest.core.DispatcherList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Singleton
    fun provideDispatcherList(): DispatcherList = DispatcherList.Base()
}