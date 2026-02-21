package com.example.dailyquiztest.presentation.feature.quiz.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.dailyquiztest.R
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.presentation.common.ActionButtonWithText
import com.example.dailyquiztest.presentation.common.CommonCard
import com.example.dailyquiztest.presentation.ui.DailyQuizTheme

interface Timer {
    fun totalSeconds(): Int

    fun totalMinutes(): Int

    fun currentSecondsProgress(): Float

    fun currentMinutes(): Int

    @Composable
    fun ShowTimeIsOverDialog(onStartAgainClicked: () -> Unit) = Unit

    abstract class Abstract(
        private val tick: Float,
        private val difficulty: DifficultyDomain
    ) : Timer {
        override fun totalSeconds(): Int {
            return difficulty.timeToComplete / 1000 % 60
        }

        override fun totalMinutes(): Int {
            return difficulty.timeToComplete / 1000 / 60
        }

        override fun currentSecondsProgress(): Float {
            return tick / (difficulty.timeToComplete / 1000)
        }

        override fun currentMinutes(): Int {
            return tick.toInt() % 60
        }
    }

    data class TimeIsOverDialog(
        private val tick: Float,
        private val difficulty: DifficultyDomain
    ) : Abstract(tick, difficulty) {
        @Composable
        override fun ShowTimeIsOverDialog(onStartAgainClicked: () -> Unit) {
            Dialog(
                onDismissRequest = {}, properties = DialogProperties(
                    usePlatformDefaultWidth = false
                )
            ) {
                CommonCard {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.padding(top = 24.dp))
                        Text(
                            stringResource(R.string.time_is_over_title),
                            style = DailyQuizTheme.typography.title
                        )
                        Text(
                            modifier = Modifier
                                .padding(vertical = 12.dp, horizontal = 24.dp),
                            text = stringResource(R.string.time_is_over_desc),
                            textAlign = TextAlign.Center
                        )
                        ActionButtonWithText(text = R.string.start_again) {
                            onStartAgainClicked.invoke()
                        }
                    }
                }
            }
        }
    }

    data class TimerProgress(private val tick: Float, private val difficulty: DifficultyDomain) :
        Abstract(tick, difficulty),
        Timer

    data object Initial : Abstract(0f, DifficultyDomain.EASY), Timer
}