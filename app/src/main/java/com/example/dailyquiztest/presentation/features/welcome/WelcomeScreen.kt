package com.example.dailyquiztest.presentation.features.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.dailyquiztest.R
import com.example.dailyquiztest.presentation.common.TopAppBarDecorator
import com.example.dailyquiztest.presentation.main_navigation.navigateIfResumed
import com.example.dailyquiztest.presentation.ui.theme.DailyQuizTheme

@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: WelcomeViewModel = hiltViewModel<WelcomeViewModel.Base>()
) {
    val uiState by viewModel.welcomeUiStateFlow().collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            WelcomeAppBar(onHistoryButtonClicked = {
                viewModel.navigateToHistory(navController::navigateIfResumed)
            })
        }
    ) { innerPadding ->
        uiState.Display(
            innerPadding,
            onStartQuizClicked = {
                viewModel.navigateToFilters(navController::navigateIfResumed)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WelcomeAppBar(onHistoryButtonClicked: () -> Unit) {
    TopAppBarDecorator {
        val desc = stringResource(R.string.history_title_text)
        Button(
            modifier = Modifier.semantics {
                contentDescription = desc
            },
            colors = ButtonDefaults.buttonColors()
                .copy(containerColor = DailyQuizTheme.colorScheme.secondary),
            shape = RoundedCornerShape(24.dp),
            onClick = {
                onHistoryButtonClicked.invoke()
            }) {
            Row(
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = DailyQuizTheme.colorScheme.primary,
                    text = stringResource(R.string.history_title_text)
                )
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Image(
                    painter = painterResource(R.drawable.history_icon),
                    contentDescription = null,
                )
            }
        }
    }
}
