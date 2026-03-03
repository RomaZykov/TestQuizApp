package com.example.dailyquiztest.presentation.feature.history.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

data class HistoryUi(private val histories: List<ResultDomain.Result>) : HistoryUiState {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Display(historyUserActions: HistoryUserActions) {
        var selectedQuizNumber by rememberSaveable { mutableStateOf("") }
        val anySelected = selectedQuizNumber.isNotEmpty()

        val screenContDesc = stringResource(R.string.non_empty_history_screen_cont_desc)
        val scrimColor = Color.Black.copy(alpha = 0.6f)
        val scrollBehavior =
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
        Scaffold(
            modifier = Modifier
                .semantics {
                    contentDescription = screenContDesc
                }
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = if (anySelected) scrimColor else Color.Transparent,
            topBar = {
                DarkOverlayDecorator(scrimColor, anySelected) {
                    HistoryTopBar(
                        onBackButtonClicked = historyUserActions.onBackButtonClicked(),
                        scrollBehavior = scrollBehavior
                    )
                }
            }
        ) { innerPadding ->
            val listState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("history lazy list")
                    .padding(top = innerPadding.calculateTopPadding()), state = listState
            ) {
                itemsIndexed(histories) { _, result ->
                    HistoryCard(
                        result,
                        selectedQuizNumber,
                        selectedQuizChanged = {
                            selectedQuizNumber = it
                        },
                        onDeleteClicked = {
                            historyUserActions.onDeleteClicked().invoke(result.number)
                            selectedQuizNumber = ""
                        })
                }
                item {
                    Spacer(modifier = Modifier.padding(vertical = 40.dp))
                    UiLogo(if (selectedQuizNumber.isNotEmpty()) 0.3f else 1f)
                    Spacer(modifier = Modifier.padding(vertical = 80.dp))
                }
            }
        }
    }

    @Composable
    private fun HistoryCard(
        resultDomain: ResultDomain.Result,
        selectedQuizNumber: String,
        selectedQuizChanged: (String) -> Unit,
        onDeleteClicked: () -> Unit
    ) {
        var menuExpanded by rememberSaveable { mutableStateOf(false) }
        val haptics = LocalHapticFeedback.current
        val thisCardSelected = selectedQuizNumber == resultDomain.number.toString()
        val cardModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(40.dp))
        Box {
            Column(
                modifier = cardModifier
                    .combinedClickable(onLongClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        selectedQuizChanged.invoke(resultDomain.number.toString())
                        menuExpanded = true
                    }, onClick = {})
                    .background(DailyQuizTheme.colorScheme.secondary)
                    .padding(24.dp)
            ) {
                StarsRow(resultDomain)
                DateRow(resultDomain)
                CategoryAndDifficultyColumn(resultDomain)
            }
            if (selectedQuizNumber.isNotEmpty() && !thisCardSelected) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .then(cardModifier)
                        .background(Color.Black.copy(0.6f))
                )
            }
            DeleteMenu(
                menuExpanded,
                onDismissRequest = {
                    selectedQuizChanged.invoke("")
                    menuExpanded = false
                }, onDeleteClicked = {
                    onDeleteClicked.invoke()
                    menuExpanded = false
                })
        }
    }
}

@Composable
private fun DarkOverlayDecorator(
    color: Color, anySelected: Boolean, content: @Composable () -> Unit
) {
    Box {
        content()
        if (anySelected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(color)
            )
        }
    }
}

@Composable
private fun CategoryAndDifficultyColumn(resultDomain: ResultDomain.Result) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val categoryStringName = stringResource(resultDomain.categoryDomain.textId)
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp), text = stringResource(
                R.string.category_result, categoryStringName
            ), style = DailyQuizTheme.typography.body, fontSize = 12.sp
        )
        Text(
            stringResource(
                R.string.difficulty_result, resultDomain.difficultyDomain.toString()
            ), style = DailyQuizTheme.typography.body, fontSize = 12.sp
        )
    }
}

@Composable
private fun DateRow(resultDomain: ResultDomain.Result) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            resultDomain.lastDate, style = DailyQuizTheme.typography.body, fontSize = 12.sp
        )
        Text(
            resultDomain.lastTime, style = DailyQuizTheme.typography.body, fontSize = 12.sp
        )
    }
}

@Composable
private fun StarsRow(resultDomain: ResultDomain.Result) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            stringResource(R.string.quiz_number_title, resultDomain.number),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DailyQuizTheme.colorScheme.tertiary
        )
        StarsScore(modifier = Modifier.height(24.dp), resultDomain.stars)
    }
}

@Composable
private fun DeleteMenu(
    menuExpanded: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    DropdownMenu(
        modifier = Modifier
            .width(230.dp)
            .background(DailyQuizTheme.colorScheme.secondary),
        expanded = menuExpanded,
        onDismissRequest = {
            onDismissRequest.invoke()
        },
        shape = RoundedCornerShape(24.dp),
        offset = DpOffset(24.dp, 0.dp)
    ) {
        DropdownMenuItem(text = {
            Text(stringResource(R.string.delete_text))
        }, onClick = {
            onDeleteClicked.invoke()
        }, leadingIcon = {
            Image(
                painter = painterResource(R.drawable.trash_icon), contentDescription = null
            )
        })
    }
}

@Preview(showSystemUi = true)
@Composable
fun HistoryUiPreview() {
    HistoryUi(
        listOf(
            ResultDomain.Result(
                1,
                stars = 0,
                categoryDomain = CategoryDomain.HISTORY,
                difficultyDomain = DifficultyDomain.EASY,
                lastTime = "14:54",
                lastDate = "2014"
            ), ResultDomain.Result(
                2,
                stars = 5,
                categoryDomain = CategoryDomain.HISTORY,
                difficultyDomain = DifficultyDomain.EASY,
                lastTime = "2014",
                lastDate = "14:54"
            ), ResultDomain.Result(
                3,
                stars = 3,
                categoryDomain = CategoryDomain.CARTOON_AND_ANIMATIONS,
                difficultyDomain = DifficultyDomain.MEDIUM,
                lastTime = "2014",
                lastDate = "14:54"
            ), ResultDomain.Result(
                4,
                stars = 2,
                categoryDomain = CategoryDomain.CARTOON_AND_ANIMATIONS,
                difficultyDomain = DifficultyDomain.HARD,
                lastTime = "2014",
                lastDate = "14:54"
            )
        )
    ).Display(historyUserActions = HistoryUserActions.ForPreview)
}
