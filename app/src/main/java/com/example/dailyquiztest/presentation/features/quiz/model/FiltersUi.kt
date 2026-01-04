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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailyquiztest.R
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.presentation.common.ActionButtonWithText
import com.example.dailyquiztest.presentation.common.TopAppBarDecorator
import com.example.dailyquiztest.presentation.common.UiLogo
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState
import com.example.dailyquiztest.presentation.features.quiz.UserActions
import com.example.dailyquiztest.presentation.ui.theme.DailyQuizTheme

data class FiltersUi(
    val categories: List<CategoriesTypes>,
    val difficulties: List<DifficultiesTypes>,
    val shouldShowError: Boolean
) : QuizUiState {

    @Composable
    override fun Display(userActions: UserActions) {
        val shouldShowErrorToast = rememberSaveable { mutableStateOf(shouldShowError) }
        LaunchedEffect(shouldShowError) {
            shouldShowErrorToast.value = shouldShowError
        }
        val categoryLabel = stringResource(R.string.category_menu_text)
        val selectedCategory = rememberSaveable { mutableStateOf(categoryLabel) }
        val difficultyLabel = stringResource(R.string.difficulty_menu_text)
        val selectedDifficulty = rememberSaveable { mutableStateOf(difficultyLabel) }
        val startButtonEnabled =
            selectedCategory.value != categoryLabel && selectedDifficulty.value != difficultyLabel
        Scaffold(
            modifier = Modifier.semantics {
                contentDescription = QuizUiState.FILTERS_SCREEN
            },
            topBar = { FiltersTopBar(userActions.onBackClicked()) }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DailyQuizTheme.colorScheme.primary)
                    .padding(innerPadding)
                    .padding(top = DailyQuizTheme.dimensions.topPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .background(DailyQuizTheme.colorScheme.secondary),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TitleText()
                    DescriptionText()
                    Categories(categories, selectedCategory, categoryLabel)
                    Difficulties(difficulties, selectedDifficulty, difficultyLabel)
                    StartQuizButton(
                        userActions.onFiltersPhaseNextButtonClicked(),
                        selectedCategory.value,
                        selectedDifficulty.value,
                        startButtonEnabled
                    )
                }
                if (shouldShowErrorToast.value) {
                    DisplaySnackBar()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun FiltersTopBar(onBackClicked: () -> Unit) {
        TopAppBarDecorator(backButton = {
            IconButton({
                onBackClicked.invoke()
            }) {
                Image(painter = painterResource(R.drawable.arrow_back_icon), null)
            }
        }) {
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
        categories: List<CategoriesTypes>,
        selectedCategory: MutableState<String>,
        categoryLabel: String
    ) {
        // TODO: DRY
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
                value = selectedCategory.value,
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
                    DropdownMenuItem(
                        text = {
                            Text(it.categoryName)
                        },
                        onClick = {
                            selectedCategory.value = it.categoryName
                            expanded = false
                        })
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Difficulties(
        difficulties: List<DifficultiesTypes>,
        selectedDifficulty: MutableState<String>,
        difficultyLabel: String
    ) {
        // TODO: DRY
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
                value = selectedDifficulty.value,
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

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                difficulties.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(it.levelApi)
                        },
                        onClick = {
                            selectedDifficulty.value = it.levelApi
                            expanded = false
                        })
                }
            }
        }
    }

    @Composable
    private fun StartQuizButton(
        onFiltersPhaseNextButtonClicked: (CategoriesTypes, DifficultiesTypes) -> Unit,
        selectedCategory: String,
        selectedDifficulty: String,
        startButtonEnabled: Boolean
    ) {
        ActionButtonWithText(
            enabled = startButtonEnabled,
            onClick = {
                onFiltersPhaseNextButtonClicked.invoke(
                    CategoriesTypes.from(selectedCategory),
                    DifficultiesTypes.from(selectedDifficulty)
                )
            },
            text = R.string.start_quiz_button_text
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun FiltersPreview() {
    FiltersUi(CategoriesTypes.entries.toList(), emptyList(), true)
        .Display(userActions = UserActions.previewUserActions)
}