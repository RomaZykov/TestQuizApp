package com.example.dailyquiztest.pages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTouchInput
import com.example.dailyquiztest.R
import com.example.dailyquiztest.core.StringResources
import com.example.dailyquiztest.domain.model.QuizQuestion
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState
import com.example.testing.dummy.dummyQuizes

class QuizPage(
    private val composeTestRule: ComposeTestRule
) : StringResources() {

    private val dummyQuizesToIterate: ArrayDeque<QuizQuestion> = ArrayDeque<QuizQuestion>().apply {
        dummyQuizes.forEach {
            this.add(it)
        }
    }

    private val nextButton =
        composeTestRule.onNode(
            hasText(retrieveString(R.string.next_button_text))
                    and hasClickAction()
        )

    private val finishButton =
        composeTestRule.onNode(
            hasText(retrieveString(R.string.finish_quiz_button_text))
                    and hasClickAction()
        )

    fun assertPageDisplayed() {
        composeTestRule.onNodeWithContentDescription(QuizUiState.QUIZ_SCREEN)
            .assertExists()
            .assertIsDisplayed()
    }

    fun clickNextButton() {
        nextButton.assertIsDisplayed().assertIsEnabled().performClick()
        dummyQuizesToIterate.removeFirst()
        composeTestRule.mainClock.advanceTimeBy(2005)
        composeTestRule.waitForIdle()
    }

    fun clickFinishQuizButton() {
        finishButton.assertIsDisplayed().assertIsEnabled().performClick()
        dummyQuizesToIterate.removeFirst()
        composeTestRule.mainClock.advanceTimeBy(2005)
        composeTestRule.waitForIdle()
    }

    fun assertFinishQuizButtonNotEnabled() {
        finishButton.assertIsDisplayed().assertIsNotEnabled()
    }

    fun assertNextButtonNotEnabled() {
        nextButton.assertIsDisplayed().assertIsNotEnabled()
    }

    fun chooseCorrectOption() {
        composeTestRule.onNodeWithText(dummyQuizesToIterate[0].question).isDisplayed()
        composeTestRule.onNodeWithText(
            dummyQuizesToIterate[0].correctAnswer,
            ignoreCase = true
        )
            .assertIsDisplayed()
            .performScrollTo()
            .performTouchInput {
                // Sometimes simple click() is too fast.
                // Imitate long press a lil longer (default — 100ms)
                down(center)
                advanceEventTime(1000)
                up()
            }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText(
            dummyQuizesToIterate[0].correctAnswer,
            ignoreCase = true
        ).assertIsSelected()
    }
}
