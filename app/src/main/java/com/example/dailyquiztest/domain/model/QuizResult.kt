package com.example.dailyquiztest.domain.model

data class QuizResult(
    val id: Int = 0,
    val stars: Int,
    val category: Category,
    val difficulty: Difficulty,
    val lastTime: String,
    val lastDate: String
) {
    fun <T> map(mapper: Mapper<T>): T {
        return mapper.mappedValue(
            id = this.id,
            stars = this.stars,
            category = this.category,
            difficulty = this.difficulty,
            lastTime = this.lastTime,
            lastDate = this.lastDate
        )
    }

    interface Mapper<T> {
        fun mappedValue(
            id: Int,
            stars: Int,
            category: Category,
            difficulty: Difficulty,
            lastTime: String,
            lastDate: String
        ): T
    }
}
