package com.example.dailyquiztest.data.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dailyquiztest.data.model.local.model.LocalQuizResult

@Database(entities = [LocalQuizResult::class], version = 2, exportSchema = false)
abstract class HistoryDataBase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
}

//@PrimaryKey(autoGenerate = true) @ColumnInfo(
//    name = "quiz_number",
//    defaultValue = "0"
//) val quizResultNumber: Int,
//@ColumnInfo(name = "stars") val stars: Int,
//@ColumnInfo(name = "category") val category: String,
//@ColumnInfo(name = "difficulty") val difficulty: String,
//@ColumnInfo(name = "last_time") val lastTime: String,
//@ColumnInfo(name = "last_date") val lastDate: String
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE history_db RENAME TO history_db_old")
        database.execSQL(
            "CREATE TABLE history_db (quiz_number INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL DEFAULT 0, stars INTEGER NOT NULL, category TEXT  NOT NULL, difficulty TEXT  NOT NULL, last_time TEXT NOT NULL, last_date TEXT NOT NULL)"
        )
        database.execSQL(
            "INSERT INTO history_db (quiz_number, stars, category, difficulty) SELECT quiz_number, stars, category, difficulty FROM history_db_old"
        )
        database.execSQL("DROP TABLE history_db_old")
    }
}
