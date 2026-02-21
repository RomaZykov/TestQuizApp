package com.example.dailyquiztest.presentation.feature.quiz.di

import com.example.dailyquiztest.presentation.feature.quiz.core.CalculateScore
import com.example.dailyquiztest.presentation.main_navigation.RouteBuilder
import com.example.dailyquiztest.presentation.feature.quiz.navigation.BaseQuizRouteProvider
import com.example.dailyquiztest.presentation.feature.quiz.navigation.QuizRouteBuilder
import com.example.dailyquiztest.presentation.main_navigation.QuizRouteProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object QuizModule {

    @Provides
    fun provideQuizRouteProvider(): QuizRouteProvider = BaseQuizRouteProvider()

    @Provides
    fun provideScore(): CalculateScore.All = CalculateScore.Base()

    @Provides
    @IntoSet
    fun provideQuizRouteBuilder(): RouteBuilder = QuizRouteBuilder()
}