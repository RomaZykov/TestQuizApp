package com.example.dailyquiztest.data.mapper

import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.domain.model.ResultDomain
import org.junit.Assert.assertEquals
import org.junit.Test

class DomainToLocalQuizResultMapperTest {
    @Test
    fun `map should return LocalQuizResult with valid input`() {
        val mapper = DomainToLocalResultMapper()
        val quizResultNumber = 1
        val stars = 5
        val categoryDomain = CategoryDomain.COMICS
        val difficultyDomain = DifficultyDomain.HARD
        val lastTime = "18:45"
        val lastDate = "2025:01:01"

        val result = mapper.mapToResult(
            ResultDomain.Result(
            number = quizResultNumber,
            stars = stars,
            categoryDomain = categoryDomain,
            difficultyDomain = difficultyDomain,
            lastTime = lastTime,
            lastDate = lastDate
            )
        )

        assertEquals(1, result.number)
        assertEquals(5, result.stars)
        assertEquals(CategoryDomain.COMICS.name, result.category)
        assertEquals(DifficultyDomain.HARD.toString(), result.difficulty)
        assertEquals("18:45", result.lastTime)
        assertEquals("2025:01:01", result.lastDate)
    }
}