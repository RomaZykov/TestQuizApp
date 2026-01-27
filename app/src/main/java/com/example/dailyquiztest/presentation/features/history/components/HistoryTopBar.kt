package com.example.dailyquiztest.presentation.features.history.components

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.dailyquiztest.R
import com.example.dailyquiztest.presentation.common.TopAppBarDecorator
import com.example.dailyquiztest.presentation.ui.DailyQuizTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryTopBar(onBackButtonClicked: () -> Unit, scrollBehavior: TopAppBarScrollBehavior? = null) {
    TopAppBarDecorator(backButton = {
        IconButton(onClick = { onBackButtonClicked.invoke() }) {
            Image(
                painterResource(R.drawable.arrow_back_icon),
                contentDescription = stringResource(R.string.back_button)
            )
        }
    }, scrollBehavior = scrollBehavior) {
        Text(
            stringResource(R.string.history_title_text),
            style = DailyQuizTheme.typography.titleTopBar
        )
    }
}