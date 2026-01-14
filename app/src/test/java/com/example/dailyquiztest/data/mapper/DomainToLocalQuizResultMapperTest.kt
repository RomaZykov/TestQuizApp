package com.example.dailyquiztest.data.mapper

import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import org.junit.Assert.assertEquals
import org.junit.Test

class DomainToLocalQuizResultMapperTest {
    @Test
    fun `map should return LocalQuizResult with valid input`() {
        val mapper = DomainToLocalQuizResultMapper()
        val quizResultNumber = 1
        val stars = 5
        val category = Category.COMICS
        val difficulty = Difficulty.HARD
        val lastTime = "18:45"
        val lastDate = "2025:01:01"

        val result = mapper.mappedValue(
            id = quizResultNumber,
            stars = stars,
            category = category,
            difficulty = difficulty,
            lastTime = lastTime,
            lastDate = lastDate
        )

        assertEquals(1, result.quizResultNumber)
        assertEquals(5, result.stars)
        assertEquals(Category.COMICS.name, result.category)
        assertEquals(Difficulty.HARD.toString(), result.difficulty)
        assertEquals("18:45", result.lastTime)
        assertEquals("2025:01:01", result.lastDate)
    }
}