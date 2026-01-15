package com.example.dailyquiztest.help_pages

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
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
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState
import com.example.testing.repository.FakeQuizRepository

class QuizPage(
    private val composeTestRule: ComposeTestRule,
    private val fakeQuizRepository: QuizRepository,
) : StringResources() {

    private val dummyQuizesToIterate: ArrayDeque<QuizQuestion> by lazy {
        ArrayDeque<QuizQuestion>().apply {
            this.addAll((fakeQuizRepository as FakeQuizRepository).savedQuizes)
        }
    }

    private val nextButton =
        composeTestRule.onNode(
            hasText(retrieveString(R.string.next_button_text))
                    and hasClickAction()
        )

    private val startAgainButton =
        composeTestRule.onNode(
            hasText(retrieveString(R.string.start_again))
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

    fun clickStartAgainButton() {
        startAgainButton.assertIsDisplayed().assertIsEnabled().performClick()
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

    fun chooseOption(shouldChooseCorrect: Boolean) {
        composeTestRule.apply {
            onNodeWithText(dummyQuizesToIterate[0].question).isDisplayed()
            val yourAnswer = if (shouldChooseCorrect) {
                dummyQuizesToIterate[0].correctAnswer
            } else {
                dummyQuizesToIterate[0].incorrectAnswers.shuffled()[0]
            }
            onNodeWithText(
                yourAnswer,
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
            waitForIdle()
            onNodeWithText(
                yourAnswer,
                ignoreCase = true
            ).assertIsSelected()
        }
    }

    fun assertFailedDialogDisplayed() {
        composeTestRule.apply {
            onNode(SemanticsMatcher.expectValue(SemanticsProperties.IsDialog, Unit))
                .assertExists()
                .assertIsDisplayed()
            onNodeWithText(retrieveString(R.string.time_is_over_title))
                .assertExists()
                .assertIsDisplayed()
            startAgainButton.assertExists().assertIsDisplayed()
        }
    }
}
