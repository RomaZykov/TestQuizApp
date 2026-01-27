package com.example.dailyquiztest.core.di

import com.example.dailyquiztest.core.FormatDate
import com.example.dailyquiztest.core.ProvideString
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonModule {

    @Binds
    @Singleton
    abstract fun bindsResources(resources: ProvideString.Base): ProvideString

    @Binds
    @Singleton
    abstract fun bindsFormattedDate(formatDate: FormatDate.Base): FormatDate
}