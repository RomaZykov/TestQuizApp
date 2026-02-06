package com.example.dailyquiztest.presentation.feature.history.di

import com.example.dailyquiztest.presentation.main_navigation.RouteBuilder
import com.example.dailyquiztest.presentation.feature.history.navigation.BaseHistoryRouteProvider
import com.example.dailyquiztest.presentation.feature.history.navigation.HistoryRouteBuilder
import com.example.dailyquiztest.presentation.main_navigation.HistoryRouteProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {

    @Provides
    fun provideHistoryRouteProvider(): HistoryRouteProvider = BaseHistoryRouteProvider()

    @Provides
    @IntoSet
    fun provideHistoryRouteBuilder(): RouteBuilder = HistoryRouteBuilder()
}