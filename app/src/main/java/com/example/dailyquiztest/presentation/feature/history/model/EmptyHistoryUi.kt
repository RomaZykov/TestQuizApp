package com.example.dailyquiztest.presentation.feature.history.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailyquiztest.R
import com.example.dailyquiztest.presentation.common.ActionButtonWithText
import com.example.dailyquiztest.presentation.feature.history.HistoryUiState
import com.example.dailyquiztest.presentation.feature.history.HistoryUserActions
import com.example.dailyquiztest.presentation.feature.history.components.HistoryTopBar
import com.example.dailyquiztest.presentation.ui.DailyQuizTheme

object EmptyHistoryUi : HistoryUiState {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Display(
        historyUserActions: HistoryUserActions
    ) {
        Scaffold(
            modifier = Modifier.semantics {
                contentDescription = "empty history screen"
            },
            topBar = {
                HistoryTopBar(historyUserActions.onBackButtonClicked())
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DailyQuizTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .background(DailyQuizTheme.colorScheme.secondary),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .padding(top = 8.dp),
                        text = stringResource(R.string.empty_history),
                        style = DailyQuizTheme.typography.body,
                        textAlign = TextAlign.Center
                    )
                    val contentDesc = stringResource(R.string.start_quiz_button_text)
                    ActionButtonWithText(modifier = Modifier.semantics {
                        contentDescription = contentDesc
                    }, onClick = {
                        historyUserActions.onStartQuizClicked().invoke()
                    }, text = R.string.start_quiz_button_text)
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun EmptyHistoryPreview() {
    EmptyHistoryUi.Display(HistoryUserActions.ForPreview)
}