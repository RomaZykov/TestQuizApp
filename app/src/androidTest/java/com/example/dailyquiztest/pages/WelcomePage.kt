package com.example.dailyquiztest.pages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.dailyquiztest.R
import com.example.dailyquiztest.core.StringResources
import com.example.dailyquiztest.presentation.features.welcome.WelcomeUiState
import com.example.dayliquiztest.HiltComponentActivity

class WelcomePage(private val composeTestRule: ComposeTestRule) :
    StringResources() {

    private val startButton =
        composeTestRule.onNode(
            hasText(retrieveString(R.string.start_quiz_button_text))
                    and hasClickAction()
        )

    private val historyButton =
        composeTestRule.onNode(
            hasContentDescription(retrieveString(R.string.history_title_text))
                    and hasClickAction()
        )

    fun clickHistoryButton() {
        historyButton.assertExists()
        historyButton.performClick()
    }

    fun clickStartButton() {
        startButton.assertIsDisplayed()
        startButton.performClick()
    }

    fun assertPageDisplayed() {
        composeTestRule.onNodeWithContentDescription(WelcomeUiState.INITIAL_WELCOME_SCREEN).assertExists()
            .assertIsDisplayed()
    }
}
