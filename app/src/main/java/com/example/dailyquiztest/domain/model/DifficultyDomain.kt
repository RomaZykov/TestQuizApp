package com.example.dailyquiztest.domain.model

import androidx.annotation.StringRes
import com.example.dailyquiztest.R

enum class DifficultyDomain(@StringRes val textId: Int, val amountOfQuestions: Int, val timeToComplete: Int) {
    EASY(R.string.easy, 5, 120000),
    MEDIUM(R.string.medium, 10, 90000),
    HARD(R.string.hard, 15, 60000);

    override fun toString(): String {
        return this.name.lowercase()
    }
}