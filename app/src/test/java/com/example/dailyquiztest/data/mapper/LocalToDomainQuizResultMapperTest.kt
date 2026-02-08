package com.example.dailyquiztest.data.mapper

import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalToDomainQuizResultMapperTest {

    @Test
    fun `map should return DomainQuizResult with valid input`() {
        val mapper = LocalToDomainResultMapper()
        val quizResultNumber = 1
        val stars = 5
        val categoryDomain = CategoryDomain.COMICS
        val difficultyDomain = DifficultyDomain.HARD
        val lastTime = "18:45"
        val lastDate = "2025:01:01"

        val result = mapper.mappedValue(
            number = quizResultNumber,
            stars = stars,
            categoryDomain = categoryDomain,
            difficultyDomain = difficultyDomain,
            lastTime = lastTime,
            lastDate = lastDate
        )

        assertEquals(1, result.number)
        assertEquals(5, result.stars)
        assertEquals(CategoryDomain.COMICS, result.categoryDomain)
        assertEquals(DifficultyDomain.HARD, result.difficultyDomain)
        assertEquals("18:45", result.lastTime)
        assertEquals("2025:01:01", result.lastDate)
    }
}