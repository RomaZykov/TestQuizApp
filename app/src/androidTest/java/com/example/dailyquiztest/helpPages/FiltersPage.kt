package com.example.dailyquiztest.helpPages

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.example.dailyquiztest.R
import com.example.dailyquiztest.core.StringResources
import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState

class FiltersPage(private val composeTestRule: ComposeTestRule) :
    StringResources() {

    private val startButton =
        composeTestRule.onNode(
            hasText(retrieveString(R.string.start_quiz_button_text))
                    and hasClickAction()
        )

    fun assertStartQuizButtonNotEnabled() {
        startButton.assertIsDisplayed().assertIsNotEnabled()
    }

    fun assertStartQuizButtonEnabled() {
        startButton.performScrollTo().assertIsDisplayed().assertIsEnabled()
    }

    fun assertPageDisplayed() {
        composeTestRule.onNodeWithContentDescription(QuizUiState.FILTERS_SCREEN)
            .assertExists()
            .assertIsDisplayed()
    }

    fun chooseSomeCategory(someCategory: Category) {
        composeTestRule.onNodeWithText(retrieveString(R.string.category_menu_text))
            .assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText(retrieveString(someCategory.textId)).performScrollTo()
            .assertIsDisplayed().performClick()
    }

    fun chooseSomeDifficulty(someDifficulty: Difficulty) {
        composeTestRule.onNodeWithText(retrieveString(R.string.difficulty_menu_text))
            .assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText(someDifficulty.toString()).assertIsDisplayed().performClick()
    }

    fun clickStartQuizButton() {
        startButton.performScrollTo().assertIsDisplayed()
        startButton.performClick()
        composeTestRule.waitForIdle()
    }

    fun errorSnackBarWasDisplayed() {
        composeTestRule.onNodeWithText(retrieveString(R.string.error_message))
            .assertExists()
            .assertIsDisplayed()
    }

    fun assertCategorySelected(category: Category) {
        composeTestRule.onNodeWithText(retrieveString(category.textId)).assertExists()
            .assertIsDisplayed()
    }

    fun assertDifficultySelected(difficulty: Difficulty) {
        composeTestRule.onNodeWithText(difficulty.toString(), ignoreCase = true).assertExists()
            .assertIsDisplayed()
    }

    fun hasScrollOption() {
        composeTestRule.onNodeWithContentDescription(QuizUiState.FILTERS_SCREEN)
            .onChildren().onFirst().assert(
                hasScrollAction()
            )
    }
}
