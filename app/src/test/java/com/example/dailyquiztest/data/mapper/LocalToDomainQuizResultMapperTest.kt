package com.example.dailyquiztest.data.mapper

import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalToDomainQuizResultMapperTest {
    @Test
    fun `map should return DomainQuizResult with valid input`() {
        val mapper = LocalToDomainQuizResultMapper()
        val quizResultNumber = 1
        val stars = 5
        val categoriesTypes = CategoriesTypes.COMICS
        val difficultiesTypes = DifficultiesTypes.HARD
        val lastTime = "18:45"
        val lastDate = "2025:01:01"

        val result = mapper.mappedValue(
            quizResultNumber = quizResultNumber,
            stars = stars,
            categoriesTypes = categoriesTypes,
            difficultiesTypes = difficultiesTypes,
            lastTime = lastTime,
            lastDate = lastDate
        )

        assertEquals(1, result.id)
        assertEquals(5, result.stars)
        assertEquals(CategoriesTypes.COMICS, result.categoriesTypes)
        assertEquals(DifficultiesTypes.HARD, result.difficultiesTypes)
        assertEquals("18:45", result.lastTime)
        assertEquals("2025:01:01", result.lastDate)
    }
}