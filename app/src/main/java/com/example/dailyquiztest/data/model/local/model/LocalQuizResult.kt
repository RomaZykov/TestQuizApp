package com.example.dailyquiztest.data.model.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain

@Entity(tableName = "history_db")
data class LocalQuizResult(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(
        name = "quiz_number",
        defaultValue = "0"
    ) val number: Int,
    @ColumnInfo(name = "stars") val stars: Int,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "difficulty") val difficulty: String,
    @ColumnInfo(name = "last_time") val lastTime: String,
    @ColumnInfo(name = "last_date") val lastDate: String
) {
    constructor(
        stars: Int,
        category: String,
        difficulty: String,
        lastTime: String,
        lastDate: String
    ) : this(0, stars, category, difficulty, lastTime, lastDate)

    fun <T> map(mapper: Mapper<T>): T {
        return mapper.mappedValue(
            number = this.number,
            stars = this.stars,
            categoryDomain = CategoryDomain.valueOf(this.category),
            difficultyDomain = DifficultyDomain.valueOf(this.difficulty.uppercase()),
            lastTime = this.lastTime,
            lastDate = this.lastDate
        )
    }

    interface Mapper<T> {
        fun mappedValue(
            number: Int,
            stars: Int,
            categoryDomain: CategoryDomain,
            difficultyDomain: DifficultyDomain,
            lastTime: String,
            lastDate: String
        ): T
    }
}