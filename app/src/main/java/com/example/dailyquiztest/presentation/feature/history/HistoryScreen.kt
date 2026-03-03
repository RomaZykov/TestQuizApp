package com.example.dailyquiztest.presentation.feature.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.dailyquiztest.R
import com.example.dailyquiztest.presentation.main_navigation.Route
import com.example.dailyquiztest.presentation.main_navigation.navigateIfResumed
import kotlinx.coroutines.launch

@Composable
fun HistoryScreenNav(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel<HistoryViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadQuizHistory()
    }

    HistoryScreen(
        uiState,
        navController,
        viewModel::onFiltersPhaseNavigate,
        viewModel::deleteQuizHistory
    )
}

@Composable
fun HistoryScreen(
    uiState: HistoryUiState,
    navController: NavController,
    navigateToFilters: (toFilters: (Route) -> Unit) -> Unit,
    deleteQuizHistory: (quizNumber: Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val message = stringResource(R.string.delete_retry)
    uiState.Display(
        historyUserActions = object : HistoryUserActions {
            override fun onBackButtonClicked(): () -> Unit = {
                navController.popBackStack()
            }

            override fun onDeleteClicked(): (Int) -> Unit = {
                deleteQuizHistory.invoke(it)
                scope.launch {
                    snackBarHostState.showSnackbar(message)
                }
            }

            override fun onStartQuizClicked(): () -> Unit = {
                navigateToFilters(navController::navigateIfResumed)
            }
        }
    )
    Box {
        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackBarHostState
        ) {
            CustomSnackBar(it)
        }
    }
}

@Composable
private fun CustomSnackBar(data: SnackbarData) {
    val snackBarContDesc = stringResource(R.string.snack_bar_cont_desc)
    Snackbar(
        modifier = Modifier
            .semantics {
                contentDescription = snackBarContDesc
            }
            .padding(32.dp),
        shape = RoundedCornerShape(32.dp),
        containerColor = Color.White
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = data.visuals.message,
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SnackBarPreview() {
    CustomSnackBar(object : SnackbarData {
        override fun dismiss() {}
        override fun performAction() {}
        override val visuals: SnackbarVisuals
            get() = object : SnackbarVisuals {
                override val actionLabel: String?
                    get() = null
                override val duration: SnackbarDuration
                    get() = TODO("Not yet implemented")
                override val message: String
                    get() = "Test message"
                override val withDismissAction: Boolean
                    get() = TODO("Not yet implemented")
            }
    })
}