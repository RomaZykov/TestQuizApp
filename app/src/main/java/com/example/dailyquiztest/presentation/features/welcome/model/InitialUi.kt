package com.example.dailyquiztest.presentation.features.welcome.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.dailyquiztest.presentation.ui.theme.DailyQuizTheme
import com.example.dailyquiztest.presentation.features.welcome.WelcomeUiState
import com.example.dailyquiztest.presentation.common.UiLogo

data object Initial : WelcomeUiState {
    @Composable
    override fun Display(
        innerPaddingValues: PaddingValues,
        onStartQuizClicked: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .semantics { contentDescription = WelcomeUiState.INITIAL_WELCOME_SCREEN }
                .fillMaxSize()
                .background(DailyQuizTheme.colorScheme.primary)
                .padding(innerPaddingValues)
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .padding(top = DailyQuizTheme.dimensions.topPadding),
                verticalArrangement = Arrangement.Top
            ) {
                UiLogo()
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                WelcomeCard(onStartQuizClicked)
            }
        }
    }

    @Composable
    private fun WelcomeCard(onStartQuizClicked: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(DailyQuizTheme.colorScheme.secondary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WelcomeText()
            StartQuizButton(onStartQuizClicked = {
                onStartQuizClicked.invoke()
            })
        }
    }

    @Composable
    private fun StartQuizButton(onStartQuizClicked: () -> Unit) {
        ActionButtonWithText(
            onClick = {
                onStartQuizClicked.invoke()
            },
            text = R.string.start_quiz_button_text
        )
    }

    @Composable
    private fun WelcomeText() {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 24.dp),
            text = stringResource(R.string.quiz_welcome_text),
            style = DailyQuizTheme.typography.title,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun InitialPreview() {
    Initial.Display(PaddingValues(0.dp)) {}
}