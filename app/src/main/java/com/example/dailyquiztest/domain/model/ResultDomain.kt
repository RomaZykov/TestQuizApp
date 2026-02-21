package com.example.dailyquiztest.domain.model

interface ResultDomain {
    fun <T> map(mapper: MapTo<T>): T

    interface MapTo<T> {
        fun mapToResult(result: Result): T
    }

    data class Result(
        val number: Int = 0,
        val stars: Int,
        val categoryDomain: CategoryDomain,
        val difficultyDomain: DifficultyDomain,
        val lastTime: String,
        val lastDate: String,
    ) : ResultDomain {
        override fun <T> map(mapper: MapTo<T>): T {
            return mapper.mapToResult(this)
        }
    }
}