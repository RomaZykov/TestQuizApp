package com.example.dailyquiztest.data.di

import android.content.Context
import androidx.room.Room
import com.example.dailyquiztest.data.model.local.HistoryDao
import com.example.dailyquiztest.data.model.local.HistoryDataBase
import com.example.dailyquiztest.data.model.local.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideHistoryDatabase(@ApplicationContext context: Context): HistoryDataBase {
        return Room.databaseBuilder(
            context,
            HistoryDataBase::class.java,
            "history_db"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(database: HistoryDataBase): HistoryDao {
        return database.historyDao()
    }
}