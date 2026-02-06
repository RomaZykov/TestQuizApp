package com.example.dailyquiztest.domain.model

data class ResultDomain(
    val number: Int = 0,
    val stars: Int,
    val categoryDomain: CategoryDomain,
    val difficultyDomain: DifficultyDomain,
    val lastTime: String,
    val lastDate: String
) {
    fun <T> map(mapper: Mapper<T>): T {
        return mapper.mappedValue(
            id = this.number,
            stars = this.stars,
            categoryDomain = this.categoryDomain,
            difficultyDomain = this.difficultyDomain,
            lastTime = this.lastTime,
            lastDate = this.lastDate
        )
    }

    interface Mapper<T> {
        fun mappedValue(
            id: Int,
            stars: Int,
            categoryDomain: CategoryDomain,
            difficultyDomain: DifficultyDomain,
            lastTime: String,
            lastDate: String
        ): T
    }
}
