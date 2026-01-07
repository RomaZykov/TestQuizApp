package com.example.dailyquiztest

import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyquiztest.core.rememberTestNavController
import com.example.dailyquiztest.pages.ResultPage
import com.example.dailyquiztest.presentation.features.quiz.QuizScreen
import com.example.dailyquiztest.presentation.features.quiz.model.QuizResultUi
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
    fun changeOrientation_onFiltersPage() {
        restorationTester.setContent {
            val uiState = QuizResultUi(
                quizAnswers = TODO()
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

//        restorationTester.emulateSavedInstanceStateRestore()

//        historyPage.assertNonEmptyHistoriesDisplayed()
    }
}