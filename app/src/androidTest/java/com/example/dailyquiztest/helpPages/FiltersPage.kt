package com.example.dailyquiztest.helpPages

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
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
import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain

class FiltersPage(private val composeTestRule: ComposeTestRule) : StringResources() {

    val mainContentDesc = "filters screen"

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
        composeTestRule.onNodeWithContentDescription(mainContentDesc)
            .assertExists()
            .assertIsDisplayed()
    }

    fun chooseSomeCategory(someCategoryDomain: CategoryDomain) {
        composeTestRule.onNodeWithText(retrieveString(R.string.category_menu_text))
            .assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText(retrieveString(someCategoryDomain.textId)).performScrollTo()
            .assertIsDisplayed().performClick()
    }

    fun chooseSomeDifficulty(someDifficultyDomain: DifficultyDomain) {
        composeTestRule.onNodeWithText(retrieveString(R.string.difficulty_menu_text))
            .assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText(someDifficultyDomain.toString()).assertIsDisplayed().performClick()
    }

    fun clickStartQuizButton() {
        startButton.performScrollTo().assertIsDisplayed()
        startButton.performClick()
        composeTestRule.waitForIdle()
    }

    fun errorSnackBarWasDisplayedWithText(exceptionText: String) {
        composeTestRule.onNodeWithText(exceptionText, substring = true)
            .assertExists()
            .assertIsDisplayed()
    }

    fun errorSnackBarNotDisplayedWithText(exceptionText: String) {
        composeTestRule.onNodeWithText(exceptionText, substring = true)
            .assertIsNotDisplayed()
            .assertDoesNotExist()
    }

    fun assertCategorySelected(categoryDomain: CategoryDomain) {
        composeTestRule.onNodeWithText(retrieveString(categoryDomain.textId)).assertExists()
            .assertIsDisplayed()
    }

    fun assertDifficultySelected(difficultyDomain: DifficultyDomain) {
        composeTestRule.onNodeWithText(difficultyDomain.toString(), ignoreCase = true).assertExists()
            .assertIsDisplayed()
    }

    fun hasScrollOption() {
        composeTestRule.onNodeWithContentDescription(mainContentDesc)
            .onChildren().onFirst().assert(
                hasScrollAction()
            )
    }
}
