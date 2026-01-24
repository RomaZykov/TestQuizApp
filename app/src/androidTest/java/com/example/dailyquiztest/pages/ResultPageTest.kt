package com.example.dailyquiztest.pages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyquiztest.core.rememberTestNavController
import com.example.dailyquiztest.help_pages.ResultPage
import com.example.dailyquiztest.presentation.features.quiz.QuizScreen
import com.example.dailyquiztest.presentation.features.quiz.model.QuizResultUi
import com.example.testing.dummy.dummyQuizAnswers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResultPageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var resultPage: ResultPage

    private val restorationTester: StateRestorationTester = StateRestorationTester(composeTestRule)

    @Before
    fun setUp() {
        resultPage = ResultPage(composeTestRule)
    }

    @Test
    fun changeOrientation_onLazyList_showsCorrectSelectedItem() {
        restorationTester.setContent {
            val uiState = QuizResultUi(
                quizAnswers = dummyQuizAnswers
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

        composeTestRule.onNodeWithText(dummyQuizAnswers.first().question)
            .assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(dummyQuizAnswers.last().question)
            .assertIsNotDisplayed()

        restorationTester.emulateSavedInstanceStateRestore()

        resultPage.performScrollToItemWithText(dummyQuizAnswers.last().question)

        restorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNodeWithText(dummyQuizAnswers.last().question)
            .assertExists().assertIsDisplayed()
        resultPage.performScrollToItemWithText(dummyQuizAnswers.first().question)
        composeTestRule.onNodeWithText(dummyQuizAnswers.first().question).assertExists().assertIsDisplayed()
    }
}