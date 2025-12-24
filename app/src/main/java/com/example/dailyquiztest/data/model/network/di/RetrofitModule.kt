package com.example.dailyquiztest.data.model.network.di

import com.example.dailyquiztest.data.model.network.QuizApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {
    @Singleton
    @Provides
    fun provideQuizApi(): QuizApi {
        return Retrofit.Builder()
            .baseUrl(QuizApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApi::class.java)
    }
}