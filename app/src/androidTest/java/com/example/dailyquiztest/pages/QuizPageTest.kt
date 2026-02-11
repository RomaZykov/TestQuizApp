package com.example.dailyquiztest.pages

import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyquiztest.core.rememberTestNavController
import com.example.dailyquiztest.helpPages.QuizPage
import com.example.dailyquiztest.presentation.feature.quiz.QuizScreenUi
import com.example.dailyquiztest.presentation.feature.quiz.model.QuizUi
import com.example.dailyquiztest.presentation.feature.quiz.model.small_screen.QuizGroupUi
import com.example.testing.repository.FakeQuizRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuizPageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var quizPage: QuizPage

    private val restorationTester: StateRestorationTester = StateRestorationTester(composeTestRule)

    @Before
    fun setUp() {
        quizPage = QuizPage(composeTestRule, FakeQuizRepository())
    }

    @Test
    fun changeOrientation_whenOneCorrectOptionSelected_showCorrectContent() {
        restorationTester.setContent {
            val uiState = QuizUi(
                number = 1,
                question = "Test question",
                incorrectAnswers = listOf("Bbb", "Ccc", "Ddd"),
                correctAnswer = "Aaa",
                totalQuestions = 5,
                quizGroupUi = QuizGroupUi.MultipleGroupUi(
                    question = "Test question",
                    inCorrectOptions = listOf("Bbb", "Ccc", "Ddd"),
                    correctOption = "Aaa",
                    userAnswer = ""
                ),
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
        quizPage.assertPageDisplayed()
        quizPage.assertNextButtonNotEnabled()
        composeTestRule.onNodeWithText("Aaa").performClick()
        quizPage.assertNextButtonEnabled()

        restorationTester.emulateSavedInstanceStateRestore()

        quizPage.assertPageDisplayed()
        composeTestRule.onNodeWithText("Aaa").assertIsSelected()
        quizPage.assertNextButtonEnabled()
    }

    @Test
    fun changeOrientation_whenOneCorrectBooleanOptionSelected_showCorrectContent() {
        restorationTester.setContent {
            val uiState = QuizUi(
                number = 1,
                question = "Test question",
                incorrectAnswers = listOf("False"),
                correctAnswer = "True",
                totalQuestions = 5,
                quizGroupUi = QuizGroupUi.BooleanGroupUi(
                    question = "Test question",
                    correctOption = "True",
                    userAnswer = ""
                ),
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
        quizPage.assertPageDisplayed()
        quizPage.assertNextButtonNotEnabled()
        composeTestRule.onNodeWithText("True").performClick()
        quizPage.assertNextButtonEnabled()

        restorationTester.emulateSavedInstanceStateRestore()

        quizPage.assertPageDisplayed()
        composeTestRule.onNodeWithText("True").assertIsSelected()
        composeTestRule.onNodeWithText("False").assertIsNotSelected()
        quizPage.assertNextButtonEnabled()
    }
}