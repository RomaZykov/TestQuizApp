package com.example.dailyquiztest.presentation.feature.quiz.di

import com.example.dailyquiztest.presentation.feature.quiz.mapper.QuizMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideQuizUiMapper(): QuizMapper = QuizMapper.Base()
}