package com.example.dailyquiztest.pages

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.dailyquiztest.core.rememberTestNavController
import com.example.dailyquiztest.helpPages.ResultPage
import com.example.dailyquiztest.presentation.feature.quiz.QuizScreenUi
import com.example.dailyquiztest.presentation.feature.quiz.model.ResultUi
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
            val uiState = ResultUi(
                quizAnswers = stubQuizUiAnswers
            )
            QuizScreenUi(
                uiState = uiState,
                navController = rememberTestNavController(),
                timerProgress = {},
                prepareQuizGame = { _, _ -> },
                saveQuizAnswer = {},
                retrieveNextAnswer = {},
                showResult = {},
                navigateToWelcome = {}
            )
        }

        resultPage.assertPageDisplayed()
        resultPage.hasScrollOption()

        composeTestRule.onNodeWithText(stubQuizUiAnswers.first().question)
            .assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(stubQuizUiAnswers.last().question)
            .assertIsNotDisplayed()

        restorationTester.emulateSavedInstanceStateRestore()

        resultPage.performScrollToItemWithText(stubQuizUiAnswers.last().question)

        restorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNodeWithText(stubQuizUiAnswers.last().question)
            .assertExists().assertIsDisplayed()
        resultPage.performScrollToItemWithText(stubQuizUiAnswers.first().question)
        composeTestRule.onNodeWithText(stubQuizUiAnswers.first().question).assertExists()
            .assertIsDisplayed()
    }
}