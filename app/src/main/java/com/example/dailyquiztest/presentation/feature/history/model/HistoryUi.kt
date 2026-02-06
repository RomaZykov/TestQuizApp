package com.example.dailyquiztest.presentation.feature.history.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyquiztest.R
import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.domain.model.ResultDomain
import com.example.dailyquiztest.presentation.common.StarsScore
import com.example.dailyquiztest.presentation.common.UiLogo
import com.example.dailyquiztest.presentation.feature.history.HistoryUiState
import com.example.dailyquiztest.presentation.feature.history.HistoryUserActions
import com.example.dailyquiztest.presentation.feature.history.components.HistoryTopBar
import com.example.dailyquiztest.presentation.ui.DailyQuizTheme
import kotlinx.coroutines.launch

 data class HistoryUi(private val histories: List<ResultDomain>) : HistoryUiState {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Display(
        historyUserActions: HistoryUserActions
    ) {
        val scrollBehavior =
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
        val snackBarHostState = remember { SnackbarHostState() }
        Scaffold(
            modifier = Modifier
                .semantics {
                    contentDescription = "non empty history screen"
                }
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            snackbarHost = {
                SnackbarHost(snackBarHostState)
            },
            topBar = {
                HistoryTopBar(
                    onBackButtonClicked = historyUserActions.onBackButtonClicked(),
                    scrollBehavior
                )
            }
        ) { innerPadding ->
            val dropDownMenuActive = rememberSaveable { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DailyQuizTheme.colorScheme.primary)
                    .padding(top = innerPadding.calculateTopPadding())
                    .alpha(if (dropDownMenuActive.value) 0.5f else 1f)
            ) {
                val listState = rememberLazyListState()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("history lazy list"),
                    state = listState
                ) {
                    itemsIndexed(histories) { _, result ->
                        HistoryCard(result, dropDownMenuActive, snackBarHostState) {
                            historyUserActions.onDeleteClicked().invoke(result.number)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.padding(vertical = 40.dp))
                        UiLogo()
                        Spacer(modifier = Modifier.padding(vertical = 80.dp))
                    }
                }
            }
        }
    }

    @Composable
    private fun HistoryCard(
        resultDomain: ResultDomain,
        dropDownMenuActive: MutableState<Boolean>,
        snackBarHostState: SnackbarHostState,
        onDeleteClicked: () -> Unit
    ) {
        val shouldShowDeleteMenu = rememberSaveable { mutableStateOf(false) }
        val haptics = LocalHapticFeedback.current
        Box(modifier = Modifier.alpha(if (dropDownMenuActive.value && !shouldShowDeleteMenu.value) 0.5f else 1f)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .combinedClickable(
                        onLongClick = {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            shouldShowDeleteMenu.value = true
                            dropDownMenuActive.value = true
                        },
                        onClick = {}
                    )
                    .background(DailyQuizTheme.colorScheme.secondary)
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(R.string.quiz_number_title, (resultDomain.number + 1)),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DailyQuizTheme.colorScheme.tertiary
                    )
                    StarsScore(modifier = Modifier.height(24.dp), resultDomain.stars)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        resultDomain.lastDate,
                        style = DailyQuizTheme.typography.body,
                        fontSize = 12.sp
                    )
                    Text(
                        resultDomain.lastTime,
                        style = DailyQuizTheme.typography.body,
                        fontSize = 12.sp
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val categoryStringName = stringResource(resultDomain.categoryDomain.textId)
                    Text(
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                        text = stringResource(
                            R.string.category_result,
                            categoryStringName
                        ),
                        style = DailyQuizTheme.typography.body,
                        fontSize = 12.sp
                    )
                    Text(
                        stringResource(R.string.difficulty_result, resultDomain.difficultyDomain.toString()),
                        style = DailyQuizTheme.typography.body,
                        fontSize = 12.sp
                    )
                }
            }
            if (shouldShowDeleteMenu.value) {
                DeleteMenu(
                    shouldShowDeleteMenu,
                    dropDownMenuActive,
                    snackBarHostState,
                    onDeleteClicked
                )
            }
        }
    }

    @Composable
    private fun DeleteMenu(
        shouldShowDeleteMenu: MutableState<Boolean>,
        dropDownMenuActive: MutableState<Boolean>,
        snackBarHostState: SnackbarHostState,
        onDeleteClicked: () -> Unit
    ) {
        DropdownMenu(
            modifier = Modifier
                .width(230.dp)
                .background(DailyQuizTheme.colorScheme.secondary),
            expanded = shouldShowDeleteMenu.value,
            onDismissRequest = {
                shouldShowDeleteMenu.value = false
                dropDownMenuActive.value = false
            },
            shape = RoundedCornerShape(24.dp),
            offset = DpOffset(24.dp, 0.dp)
        ) {
            val message = stringResource(R.string.delete_retry)
            val scope = rememberCoroutineScope()
            DropdownMenuItem(
                text = {
                    Text(stringResource(R.string.delete_text))
                },
                onClick = {
                    onDeleteClicked.invoke()
                    scope.launch {
                        snackBarHostState.showSnackbar(message)
                        shouldShowDeleteMenu.value = false
                        dropDownMenuActive.value = false
                    }
                },
                leadingIcon = {
                    Image(
                        painter = painterResource(R.drawable.trash_icon),
                        contentDescription = null
                    )
                },
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HistoryUiPreview() {
    HistoryUi(
        listOf(
            ResultDomain(
                0,
                stars = 0,
                categoryDomain = CategoryDomain.HISTORY,
                difficultyDomain = DifficultyDomain.EASY,
                lastTime = "14:54",
                lastDate = "2014"
            ),
            ResultDomain(
                1,
                stars = 5,
                categoryDomain = CategoryDomain.HISTORY,
                difficultyDomain = DifficultyDomain.EASY,
                lastTime = "2014",
                lastDate = "14:54"
            ),
            ResultDomain(
                2,
                stars = 3,
                categoryDomain = CategoryDomain.CARTOON_AND_ANIMATIONS,
                difficultyDomain = DifficultyDomain.MEDIUM,
                lastTime = "2014",
                lastDate = "14:54"
            ),
            ResultDomain(
                3,
                stars = 2,
                categoryDomain = CategoryDomain.CARTOON_AND_ANIMATIONS,
                difficultyDomain = DifficultyDomain.HARD,
                lastTime = "2014",
                lastDate = "14:54"
            )
        )
    ).Display(historyUserActions = HistoryUserActions.ForPreview)
}