package com.example.dailyquiztest.domain.model

enum class DifficultiesTypes(val levelApi: String, val amountOfQuestions: Int, val timeToComplete: Int) {
    EASY("easy", 5, 120000),
    MEDIUM("medium", 10, 90000),
    HARD("hard", 15, 60000);

    companion object {
        fun from(level: String): DifficultiesTypes =
            requireNotNull(DifficultiesTypes.entries.find { it.levelApi == level }) { "No found Difficulty with type $level" }
    }
}