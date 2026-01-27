package com.example.dailyquiztest.presentation.features.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dailyquiztest.R
import com.example.dailyquiztest.core.Const
import com.example.dailyquiztest.presentation.common.ActionButtonWithText
import com.example.dailyquiztest.presentation.common.TopAppBarDecorator
import com.example.dailyquiztest.presentation.common.UiLogo
import com.example.dailyquiztest.presentation.main_navigation.Route
import com.example.dailyquiztest.presentation.main_navigation.navigateIfResumed
import com.example.dailyquiztest.presentation.ui.theme.DailyQuizTheme

@Composable
fun WelcomeScreenNav(
    navController: NavController,
    viewModel: WelcomeViewModel = hiltViewModel<WelcomeViewModel>()
) {
    WelcomeScreen(
        navController,
        viewModel::navigateToFilters,
        viewModel::navigateToHistory
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    navController: NavController,
    onStartQuizClicked: (toFilters: (Route) -> Unit) -> Unit,
    onHistoryClicked: (toHistory: (Route) -> Unit) -> Unit
) {
    val scrollState = rememberScrollState()
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier
            .semantics { contentDescription = Const.WelcomeContDesc.toString() }
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            WelcomeAppBar(onHistoryButtonClicked = {
                onHistoryClicked.invoke(navController::navigateIfResumed)
            }, scrollBehavior)
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(DailyQuizTheme.colorScheme.primary)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Rtl)
                )
                .padding(top = DailyQuizTheme.dimensions.topPadding),
            verticalArrangement = Arrangement.Top
        ) {
            UiLogo()
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            WelcomeCard(navController, onStartQuizClicked)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WelcomeAppBar(
    onHistoryButtonClicked: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBarDecorator(scrollBehavior) {
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

@Composable
private fun WelcomeCard(
    navController: NavController,
    onStartQuizClicked: (toFilters: (Route) -> Unit) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(DailyQuizTheme.colorScheme.secondary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WelcomeText()
        ActionButtonWithText(
            onClick = {
                onStartQuizClicked.invoke(navController::navigateIfResumed)
            },
            text = R.string.start_quiz_button_text
        )
    }
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

@Composable
@Preview(showSystemUi = true)
fun WelcomePreview() {
    WelcomeScreen(rememberNavController(), {}, {})
}
