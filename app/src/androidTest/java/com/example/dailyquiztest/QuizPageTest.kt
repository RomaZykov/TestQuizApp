package com.example.dailyquiztest

import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyquiztest.core.rememberTestNavController
import com.example.dailyquiztest.pages.QuizPage
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
    fun changeOrientation_onFiltersPage() {
        restorationTester.setContent {
            val uiState = QuizUi(
                currentNumberQuestion = TODO(),
                question = TODO(),
                incorrectAnswers = TODO(),
                correctAnswer = TODO(),
                questionType = TODO(),
                totalQuestions = TODO(),
                userAnswers = TODO(),
                isAnsweredCorrect = TODO(),
                category = TODO(),
                difficulty = TODO()
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

//        restorationTester.emulateSavedInstanceStateRestore()

//        historyPage.assertNonEmptyHistoriesDisplayed()
    }
}