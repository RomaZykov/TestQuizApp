package com.example.dailyquiztest.data.model.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes

@Entity(tableName = "history_db")
data class LocalQuizResult(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(
        name = "quiz_number",
        defaultValue = "0"
    ) val quizResultNumber: Int,
    @ColumnInfo(name = "stars") val stars: Int,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "difficulty") val difficulty: String,
    @ColumnInfo(name = "last_time") val lastTime: String,
    @ColumnInfo(name = "last_date") val lastDate: String
) {

    constructor(
        stars: Int,
        categoriesTypes: String,
        difficultiesTypes: String,
        lastTime: String,
        lastDate: String
    ) : this(0, stars, categoriesTypes, difficultiesTypes, lastTime, lastDate)

    fun <T> map(mapper: Mapper<T>): T {
        return mapper.mappedValue(
            quizResultNumber = this.quizResultNumber,
            stars = this.stars,
            categoriesTypes = CategoriesTypes.valueOf(this.category),
            difficultiesTypes = DifficultiesTypes.valueOf(this.difficulty),
            lastTime = this.lastTime,
            lastDate = this.lastDate
        )
    }

    interface Mapper<T> {
        fun mappedValue(
            quizResultNumber: Int,
            stars: Int,
            categoriesTypes: CategoriesTypes,
            difficultiesTypes: DifficultiesTypes,
            lastTime: String,
            lastDate: String
        ): T
    }
}