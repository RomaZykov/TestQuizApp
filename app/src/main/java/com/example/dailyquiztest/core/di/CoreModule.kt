package com.example.dailyquiztest.core.di

import com.example.dailyquiztest.core.FormattedDate
import com.example.dailyquiztest.core.StringProvider
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
    abstract fun bindsResources(resources: StringProvider.Base): StringProvider

    @Binds
    @Singleton
    abstract fun bindsFormattedDate(formattedDate: FormattedDate.Base): FormattedDate
}