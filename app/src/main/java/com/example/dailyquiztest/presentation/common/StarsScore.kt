package com.example.dailyquiztest.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.dailyquiztest.R

@Composable
fun StarsScore(modifier: Modifier = Modifier, calculatedStarsScoreResult: Int) {
    Row(modifier = modifier) {
        repeat(5) {
            Image(
                painter = painterResource(
                    if (calculatedStarsScoreResult <= it) {
                        R.drawable.property_1_inactive
                    } else {
                        R.drawable.property_1_active
                    }
                ), null
            )
        }
    }
}

@Preview
@Composable
fun StarsScorePreview() {
    StarsScore(calculatedStarsScoreResult = 1)
}
