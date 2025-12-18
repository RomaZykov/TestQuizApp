package com.example.dailyquiztest.domain.model

data class QuizResult(
    val id: Int = 0,
    val stars: Int,
    val categoriesTypes: CategoriesTypes,
    val difficultiesTypes: DifficultiesTypes,
    val lastTime: String,
    val lastDate: String
) {
    fun <T> map(mapper: Mapper<T>): T {
        return mapper.mappedValue(
            id = this.id,
            stars = this.stars,
            categoriesTypes = this.categoriesTypes,
            difficultiesTypes = this.difficultiesTypes,
            lastTime = this.lastTime,
            lastDate = this.lastDate
        )
    }

    interface Mapper<T> {
        fun mappedValue(
            id: Int,
            stars: Int,
            categoriesTypes: CategoriesTypes,
            difficultiesTypes: DifficultiesTypes,
            lastTime: String,
            lastDate: String
        ): T
    }
}
