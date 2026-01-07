package com.example.dailyquiztest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.dailyquiztest.data.model.local.HistoryDao
import com.example.dailyquiztest.data.model.local.HistoryDataBase
import com.example.dailyquiztest.data.model.local.model.LocalQuizResult
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HistoryDaoTest {

    private lateinit var db: HistoryDataBase
    lateinit var historyDao: HistoryDao

    @Before
    fun setup() {
        db = run {
            val context = ApplicationProvider.getApplicationContext<Context>()
            Room.inMemoryDatabaseBuilder(
                context,
                HistoryDataBase::class.java,
            ).build()
        }
        historyDao = db.historyDao()
    }

    @After
    fun teardown() = db.close()

    @Test
    fun historyDao_fetchCorrectIds() = runTest {
        insertHistories()

        val actualResult = historyDao.getHistoryQuizResults()

        assertEquals(
            listOf(1, 2, 3),
            actualResult.map { it.quizResultNumber }
        )
    }

    @Test
    fun historyDao_deleteAllData_equalsEmpty() = runTest {
        insertHistories()

        historyDao.deleteQuiz(1)
        historyDao.deleteQuiz(2)
        historyDao.deleteQuiz(3)

        val actualResult = historyDao.getHistoryQuizResults()
        assertEquals(
            emptyList<LocalQuizResult>(),
            actualResult
        )
    }

    @Test
    fun historyDao_deletingFromMiddle_fetchesCorrectIds() = runTest {
        insertHistories()

        historyDao.deleteQuiz(2)
        val actualResults = historyDao.getHistoryQuizResults()

        assertEquals(
            listOf(1, 3),
            actualResults.map { it.quizResultNumber }
        )
    }

    private suspend fun insertHistories() {
        val historyEntities = listOf(
            LocalQuizResult(
                quizResultNumber = 1,
                stars = 0,
                category = CategoriesTypes.HISTORY.categoryName,
                difficulty = DifficultiesTypes.EASY.levelApi,
                lastTime = "",
                lastDate = ""
            ),
            LocalQuizResult(
                quizResultNumber = 2,
                stars = 5,
                category = CategoriesTypes.BOARD_GAMES.categoryName,
                difficulty = DifficultiesTypes.HARD.levelApi,
                lastTime = "",
                lastDate = ""
            ),
            LocalQuizResult(
                quizResultNumber = 3,
                stars = 2,
                category = CategoriesTypes.HISTORY.categoryName,
                difficulty = DifficultiesTypes.MEDIUM.levelApi,
                lastTime = "",
                lastDate = ""
            )
        )
        historyEntities.forEach {
            historyDao.insert(it)
        }
    }
}

