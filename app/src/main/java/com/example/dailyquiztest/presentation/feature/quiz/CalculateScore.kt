package com.example.dailyquiztest.presentation.feature.quiz

import com.example.dailyquiztest.R
import javax.inject.Inject

interface CalculateScore  {
    interface AddInfo {
        fun accIfCorrect(isAnsweredCorrect: Boolean)
        fun totalQuestions(totalQuestions: Int)
    }
    interface All : AddInfo, CalculateScore

    fun calculateScorePercentage(): Int
    fun totalCorrectAnswers(): Int
    fun calculateStarsScoreResult(): Int

    fun scoreTitleResId(): Int
    fun scoreDescriptionResId(): Int

    class Base @Inject constructor() : All {
        private var correctAnswers = 0
        private var allQuestions = 0
        private fun titleAndDescription(): Pair<Int, Int> {
            return when (calculateScorePercentage()) {
                in 0 until 20 -> Pair(
                    R.string.zero_stars_title,
                    R.string.zero_stars_desc
                )

                in 20 until 40 -> Pair(
                    R.string.one_stars_title,
                    R.string.one_stars_desc
                )

                in 40 until 60 -> Pair(
                    R.string.two_stars_title,
                    R.string.two_stars_desc
                )

                in 60 until 80 -> Pair(
                    R.string.three_stars_title,
                    R.string.three_stars_desc
                )

                in 80 until 100 -> Pair(
                    R.string.four_stars_title,
                    R.string.four_stars_desc
                )

                else -> Pair(
                    R.string.five_stars_title,
                    R.string.five_stars_description
                )
            }
        }

        override fun totalQuestions(totalQuestions: Int) {
            allQuestions = totalQuestions
        }

        override fun accIfCorrect(isAnsweredCorrect: Boolean) {
            if (isAnsweredCorrect) {
                correctAnswers++
            }
        }

        override fun scoreTitleResId(): Int = titleAndDescription().first
        override fun scoreDescriptionResId(): Int = titleAndDescription().second
        override fun totalCorrectAnswers(): Int = correctAnswers

        override fun calculateScorePercentage(): Int {
            return totalCorrectAnswers() * 100 / allQuestions
        }

        override fun calculateStarsScoreResult(): Int {
            val percentagesList = listOf(20, 40, 60, 80, 100)
            var starsCounter = 0
            val calculatedScore = calculateScorePercentage()
            for (e in percentagesList) {
                if (calculatedScore >= e) {
                    starsCounter++
                } else {
                    break
                }
            }
            return starsCounter
        }
    }
}