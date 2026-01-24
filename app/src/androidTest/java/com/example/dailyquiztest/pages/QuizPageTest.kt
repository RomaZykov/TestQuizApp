package com.example.dailyquiztest.pages

import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyquiztest.core.rememberTestNavController
import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.domain.model.QuestionTypes
import com.example.dailyquiztest.helpPages.QuizPage
import com.example.dailyquiztest.presentation.features.quiz.QuizScreen
import com.example.dailyquiztest.presentation.features.quiz.model.QuizUi
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
                currentNumberQuestion = 1,
                question = "Test question",
                incorrectAnswers = listOf("b", "c", "d"),
                correctAnswer = "a",
                questionType = QuestionTypes.MULTIPLE,
                totalQuestions = 5,
                category = Category.CARTOON_AND_ANIMATIONS,
                difficulty = Difficulty.MEDIUM
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
        quizPage.assertPageDisplayed()
        quizPage.assertNextButtonNotEnabled()
        composeTestRule.onNodeWithText("a").performClick()
        quizPage.assertNextButtonEnabled()

        restorationTester.emulateSavedInstanceStateRestore()

        quizPage.assertPageDisplayed()
        composeTestRule.onNodeWithText("a").assertIsSelected()
        quizPage.assertNextButtonEnabled()
    }

    @Test
    fun changeOrientation_whenAllOptionsSelected_showCorrectContent() {
        restorationTester.setContent {
            val uiState = QuizUi(
                currentNumberQuestion = 1,
                question = "Test question",
                incorrectAnswers = listOf("b", "c", "d"),
                correctAnswer = "a",
                questionType = QuestionTypes.MULTIPLE,
                totalQuestions = 5,
                category = Category.CARTOON_AND_ANIMATIONS,
                difficulty = Difficulty.MEDIUM
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
        quizPage.assertPageDisplayed()
        quizPage.assertNextButtonNotEnabled()
        composeTestRule.onNodeWithText("a").performClick()
        quizPage.assertNextButtonEnabled()
        composeTestRule.onNodeWithText("b").performClick()
        quizPage.assertNextButtonEnabled()
        composeTestRule.onNodeWithText("c").performClick()
        quizPage.assertNextButtonEnabled()
        composeTestRule.onNodeWithText("d").performClick()
        quizPage.assertNextButtonEnabled()

        restorationTester.emulateSavedInstanceStateRestore()

        quizPage.assertPageDisplayed()
        composeTestRule.onNodeWithText("a").assertIsSelected()
        composeTestRule.onNodeWithText("b").assertIsSelected()
        composeTestRule.onNodeWithText("c").assertIsSelected()
        composeTestRule.onNodeWithText("d").assertIsSelected()
        quizPage.assertNextButtonEnabled()
    }

    @Test
    fun changeOrientation_whenOneCorrectBooleanOptionSelected_showCorrectContent() {
        restorationTester.setContent {
            val uiState = QuizUi(
                currentNumberQuestion = 1,
                question = "Test question",
                incorrectAnswers = listOf("false"),
                correctAnswer = "true",
                questionType = QuestionTypes.BOOLEAN,
                totalQuestions = 5,
                category = Category.CARTOON_AND_ANIMATIONS,
                difficulty = Difficulty.HARD
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
        quizPage.assertPageDisplayed()
        quizPage.assertNextButtonNotEnabled()
        composeTestRule.onNodeWithText("True").performClick()
        quizPage.assertNextButtonEnabled()

        restorationTester.emulateSavedInstanceStateRestore()

        quizPage.assertPageDisplayed()
        composeTestRule.onNodeWithText("true", ignoreCase = true).assertIsSelected()
        composeTestRule.onNodeWithText("false", ignoreCase = true).assertIsNotSelected()
        quizPage.assertNextButtonEnabled()
    }
}