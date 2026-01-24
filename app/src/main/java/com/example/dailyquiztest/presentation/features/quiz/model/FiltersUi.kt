package com.example.dailyquiztest.presentation.features.quiz.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.dp
import com.example.dailyquiztest.R
import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.presentation.common.ActionButtonWithText
import com.example.dailyquiztest.presentation.common.TopAppBarDecorator
import com.example.dailyquiztest.presentation.common.UiLogo
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState
import com.example.dailyquiztest.presentation.features.quiz.QuizUserActions
import com.example.dailyquiztest.presentation.ui.theme.DailyQuizTheme

data class FiltersUi(
    val categories: List<Category>,
    val difficulties: List<Difficulty>,
    val shouldShowError: Boolean
) : QuizUiState {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Display(quizUserActions: QuizUserActions) {
        val shouldShowErrorBar = rememberSaveable { mutableStateOf(shouldShowError) }
        LaunchedEffect(shouldShowError) {
            shouldShowErrorBar.value = shouldShowError
        }

        val categoryLabel = stringResource(R.string.category_menu_text)
        val selectedCategory = rememberSaveable { mutableStateOf(categoryLabel) }

        val difficultyLabel = stringResource(R.string.difficulty_menu_text)
        val selectedDifficulty = rememberSaveable { mutableStateOf(difficultyLabel) }

        val startButtonEnabled =
            selectedCategory.value != categoryLabel && selectedDifficulty.value != difficultyLabel

        val scrollState = rememberScrollState()
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
        Scaffold(
            modifier = Modifier
                .semantics {
                    contentDescription = QuizUiState.FILTERS_SCREEN
                }
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { FiltersTopBar(quizUserActions.onBackClicked(), scrollBehavior) }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DailyQuizTheme.colorScheme.primary)
                    .padding(top = innerPadding.calculateTopPadding())
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .background(DailyQuizTheme.colorScheme.secondary),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TitleText()
                    DescriptionText()
                    Categories(selectedCategory, categoryLabel)
                    Difficulties(selectedDifficulty, difficultyLabel)
                    StartQuizButton(
                        quizUserActions.onFiltersPhaseNextButtonClicked(),
                        selectedCategory.value,
                        selectedDifficulty.value,
                        startButtonEnabled
                    )
                }
                if (shouldShowErrorBar.value) {
                    DisplaySnackBar()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun FiltersTopBar(onBackClicked: () -> Unit, scrollBehavior: TopAppBarScrollBehavior?) {
        TopAppBarDecorator(backButton = {
            IconButton({
                onBackClicked.invoke()
            }) {
                Image(painter = painterResource(R.drawable.arrow_back_icon), null)
            }
        }, scrollBehavior = scrollBehavior) {
            UiLogo()
        }
    }

    @Composable
    private fun BoxScope.DisplaySnackBar() {
        Snackbar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(stringResource(R.string.error_message))
        }
    }

    @Composable
    private fun TitleText() {
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
            text = stringResource(R.string.almost_done),
            style = DailyQuizTheme.typography.title
        )
    }

    @Composable
    private fun DescriptionText() {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(R.string.category_description), textAlign = TextAlign.Center
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Categories(
        selectedCategory: MutableState<String>,
        categoryLabel: String
    ) {
        var expanded by rememberSaveable { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        expanded = true
                    },
                value = selectedCategory.value.substringBefore(DELIMITER),
                onValueChange = {
                    selectedCategory.value = it
                },
                textStyle = DailyQuizTheme.typography.dropDownItem,
                label = {
                    if (selectedCategory.value != categoryLabel) {
                        Text(categoryLabel)
                    }
                },
                readOnly = true,
                trailingIcon = {
                    Icon(painter = painterResource(R.drawable.outline_keyboard_arrow_down), null)
                }
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                categories.forEach {
                    val categoryText = stringResource(it.textId)
                    DropdownMenuItem(
                        text = {
                            Text(categoryText)
                        },
                        onClick = {
                            selectedCategory.value = "$categoryText-${it.name}"
                            expanded = false
                        })
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Difficulties(
        selectedDifficulty: MutableState<String>,
        difficultyLabel: String
    ) {
        val expanded = rememberSaveable { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            expanded = expanded.value,
            onExpandedChange = { expanded.value = !expanded.value }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        expanded.value = true
                    },
                value = selectedDifficulty.value.substringBefore(DELIMITER),
                onValueChange = {
                    selectedDifficulty.value = it
                },
                textStyle = DailyQuizTheme.typography.dropDownItem,
                label = {
                    if (selectedDifficulty.value != difficultyLabel) {
                        Text(text = difficultyLabel, color = DailyQuizTheme.colorScheme.tertiary)
                    }
                },
                readOnly = true,
                trailingIcon = {
                    Icon(painter = painterResource(R.drawable.outline_keyboard_arrow_down), null)
                }
            )

            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }) {
                difficulties.forEach {
                    val difficultyText = stringResource(it.textId)
                    DropdownMenuItem(
                        text = {
                            Text(difficultyText)
                        },
                        onClick = {
                            selectedDifficulty.value = "$difficultyText-${it.name}"
                            expanded.value = false
                        })
                }
            }
        }
    }

    @Composable
    private fun StartQuizButton(
        onNextButtonClicked: (Category, Difficulty) -> Unit,
        selectedCategory: String,
        selectedDifficulty: String,
        startButtonEnabled: Boolean
    ) {
        ActionButtonWithText(
            enabled = startButtonEnabled,
            onClick = {
                onNextButtonClicked.invoke(
                    Category.valueOf(selectedCategory.substringAfter(DELIMITER)),
                    Difficulty.valueOf(selectedDifficulty.substringAfter(DELIMITER))
                )
            },
            text = R.string.start_quiz_button_text
        )
    }

    companion object {
        const val DELIMITER = "-"
    }
}

@Preview(showSystemUi = true)
@Composable
fun FiltersPreview() {
    FiltersUi(Category.entries.toList(), emptyList(), true)
        .Display(quizUserActions = QuizUserActions.previewQuizUserActions)
}