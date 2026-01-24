package com.example.dailyquiztest.pages

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.dailyquiztest.core.rememberTestNavController
import com.example.dailyquiztest.helpPages.ResultPage
import com.example.dailyquiztest.presentation.features.quiz.QuizScreen
import com.example.dailyquiztest.presentation.features.quiz.model.QuizResultUi
import com.example.testing.dummy.stubQuizAnswers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ResultPageTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var resultPage: ResultPage

    private val restorationTester: StateRestorationTester =
        StateRestorationTester(composeTestRule)

    @Before
    fun setUp() {
        resultPage = ResultPage(composeTestRule)
    }

    @Test
    fun resetState_onLazyList_showsCorrectPreviouslySelectedItem() {
        restorationTester.setContent {
            val uiState = QuizResultUi(
                quizAnswers = stubQuizAnswers
            )
            QuizScreen(
                uiState = uiState,
                navController = rememberTestNavController(),
                prepareQuizGame = { _, _ -> },
                saveQuizAnswer = {},
                retrieveNextAnswer = {},
                showResult = {},
                navigateToWelcome = {}
            )
        }

        resultPage.assertPageDisplayed()
        resultPage.hasScrollOption()

        composeTestRule.onNodeWithText(stubQuizAnswers.first().question)
            .assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(stubQuizAnswers.last().question)
            .assertIsNotDisplayed()

        restorationTester.emulateSavedInstanceStateRestore()

        resultPage.performScrollToItemWithText(stubQuizAnswers.last().question)

        restorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNodeWithText(stubQuizAnswers.last().question)
            .assertExists().assertIsDisplayed()
        resultPage.performScrollToItemWithText(stubQuizAnswers.first().question)
        composeTestRule.onNodeWithText(stubQuizAnswers.first().question).assertExists()
            .assertIsDisplayed()
    }
}