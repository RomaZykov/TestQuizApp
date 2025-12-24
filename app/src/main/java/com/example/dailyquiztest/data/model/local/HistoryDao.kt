package com.example.dailyquiztest.data.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dailyquiztest.data.model.local.model.LocalQuizResult

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quizResult: LocalQuizResult)

    @Query("delete from history_db where quiz_number = :quizResultId")
    suspend fun deleteQuiz(quizResultId: Int)

    @Query("SELECT * FROM history_db")
    suspend fun getHistoryQuizResults(): List<LocalQuizResult>
}