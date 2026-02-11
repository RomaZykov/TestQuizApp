package com.example.dailyquiztest.pages

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.dailyquiztest.core.rememberTestNavController
import com.example.dailyquiztest.domain.model.QuizDomain
import com.example.dailyquiztest.domain.model.QuizTypeDomain
import com.example.dailyquiztest.helpPages.ResultPage
import com.example.dailyquiztest.presentation.feature.quiz.CalculateScore
import com.example.dailyquiztest.presentation.feature.quiz.QuizScreenUi
import com.example.dailyquiztest.presentation.feature.quiz.mapper.QuizMapper
import com.example.dailyquiztest.presentation.feature.quiz.model.ResultUi
import com.example.testing.stub.stubDomainAnswers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ResultPageTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var resultPage: ResultPage
    private val quizMapper: QuizMapper = QuizMapper.Base()
    private val score = CalculateScore.Base()

    private val restorationTester: StateRestorationTester =
        StateRestorationTester(composeTestRule)

    @Before
    fun setUp() {
        resultPage = ResultPage(composeTestRule)
    }

//    // Use as copy-past to new tests
//    // Green
//    resultPage.assertGreenCardIconDisplayedOnQuestion(results.first().question)
//    resultPage.assertGreenEdgeDisplayedOnText(results.first().userAnswer)
//    resultPage.assertGreenOptionIconDisplayedOnText(results.first().userAnswer)
//
//    // Red
//    resultPage.assertRedCardIconDisplayedOnQuestion(results.first().question)
//    resultPage.assertRedEdgeDisplayedOnText(results.first().userAnswer)
//    resultPage.assertRedOptionIconIsNotDisplayedOnText(results.first().userAnswer)
//
//    // Green Not Displayed
//    resultPage.assertGreenOptionIconIsNotDisplayedOnText(results.first().userAnswer)
//    resultPage.assertGreenEdgeIsNotDisplayedOnText(results.first().userAnswer)
//
//    // Red Not Displayed
//    resultPage.assertRedEdgeIsNotDisplayedOnText(results.first().incorrectAnswers[0])
//    resultPage.assertRedOptionIconIsNotDisplayedOnText(results.first().userAnswer)

    @Test
    fun checkCorrectMultiple_showsGreenIconsAndEdges() {
        val results = listOf(
            QuizDomain.Quiz(
                question = "Multiple - answer is correct",
                incorrectAnswers = listOf("Aaa", "Bbb", "Ccc"),
                correctAnswer = "Ddd",
                type = QuizTypeDomain.MULTIPLE,
                userAnswer = "Ddd",
                isAnsweredCorrect = true
            )
        )
        restorationTester.setContent {
            val uiState = ResultUi(
                quizAnswers = quizMapper.mapToListQuiz(results),
                score = score.apply { this.totalQuestions(results.size) }
            )
            QuizScreenUi(
                uiState = uiState,
                navController = rememberTestNavController(),
//                timerProgress = {},
                prepareQuizGame = { _, _ -> },
                saveQuizAnswer = {},
                retrieveNextAnswer = {},
                showResult = {},
                navigateToWelcome = {}
            )
        }

    }

    @Test
    fun checkWrongMultiple_showsRedIconsAndEdges() {
        val results = listOf(
            QuizDomain.Quiz(
                question = "Multiple - answer is not correct",
                incorrectAnswers = listOf("Aaa", "Bbb", "Ccc"),
                correctAnswer = "Ddd",
                type = QuizTypeDomain.MULTIPLE,
                userAnswer = "Bbb",
                isAnsweredCorrect = false
            )
        )
        restorationTester.setContent {
            val uiState = ResultUi(
                quizAnswers = quizMapper.mapToListQuiz(results),
                score = score.apply { this.totalQuestions(results.size) }
            )
            QuizScreenUi(
                uiState = uiState,
                navController = rememberTestNavController(),
//                timerProgress = {},
                prepareQuizGame = { _, _ -> },
                saveQuizAnswer = {},
                retrieveNextAnswer = {},
                showResult = {},
                navigateToWelcome = {}
            )
        }

    }

    @Test
    fun checkWrongBoolean_showsRedIconsAndEdges() {
        val results = listOf(
            QuizDomain.Quiz(
                question = "Boolean - answer is not correct",
                incorrectAnswers = listOf("true"),
                correctAnswer = "false",
                type = QuizTypeDomain.BOOLEAN,
                userAnswer = "true",
                isAnsweredCorrect = false
            )
        )
        restorationTester.setContent {
            val uiState = ResultUi(
                quizAnswers = quizMapper.mapToListQuiz(results),
                score = score.apply { this.totalQuestions(results.size) }
            )
            QuizScreenUi(
                uiState = uiState,
                navController = rememberTestNavController(),
//                timerProgress = {},
                prepareQuizGame = { _, _ -> },
                saveQuizAnswer = {},
                retrieveNextAnswer = {},
                showResult = {},
                navigateToWelcome = {}
            )
        }

        resultPage.performScrollToItemWithText(results.first().question)
        resultPage.assertRedCardIconDisplayedOnQuestion(results.first().question)

        resultPage.assertRedOptionIconDisplayedOnText(results.first().userAnswer)
        resultPage.assertRedEdgeDisplayedOnText(results.first().userAnswer)

        with(results.first().correctAnswer) {
            resultPage.assertGreenEdgeIsNotDisplayedOnText(this)
            resultPage.assertGreenOptionIconIsNotDisplayedOnText(this)
        }
    }

    @Test
    fun checkCorrectBoolean_showsGreenIconsAndEdges() {
        val results = listOf(
            QuizDomain.Quiz(
                question = "Boolean - answer is correct",
                incorrectAnswers = listOf("true"),
                correctAnswer = "false",
                type = QuizTypeDomain.BOOLEAN,
                userAnswer = "false",
                isAnsweredCorrect = true
            )
        )
        restorationTester.setContent {
            val uiState = ResultUi(
                quizAnswers = quizMapper.mapToListQuiz(results),
                score = score.apply { this.totalQuestions(results.size) }
            )
            QuizScreenUi(
                uiState = uiState,
                navController = rememberTestNavController(),
//                timerProgress = {},
                prepareQuizGame = { _, _ -> },
                saveQuizAnswer = {},
                retrieveNextAnswer = {},
                showResult = {},
                navigateToWelcome = {}
            )
        }

        resultPage.performScrollToItemWithText(results.first().question)
        resultPage.assertGreenCardIconDisplayedOnQuestion(results.first().question)

        resultPage.assertGreenOptionIconDisplayedOnText(results.first().userAnswer)
        resultPage.assertGreenEdgeDisplayedOnText(results.first().userAnswer)

        with(results.first().incorrectAnswers[0]) {
            resultPage.assertRedEdgeIsNotDisplayedOnText(this)
        }
    }

    @Test
    fun resetState_onLazyList_showsCorrectPreviouslySelectedItem() {
        restorationTester.setContent {
            val uiState = ResultUi(
                quizAnswers = quizMapper.mapToListQuiz(stubDomainAnswers),
                score = score.apply { this.totalQuestions(stubDomainAnswers.size) }
            )
            QuizScreenUi(
                uiState = uiState,
                navController = rememberTestNavController(),
//                timerProgress = {},
                prepareQuizGame = { _, _ -> },
                saveQuizAnswer = {},
                retrieveNextAnswer = {},
                showResult = {},
                navigateToWelcome = {}
            )
        }

        resultPage.assertPageDisplayed()
        resultPage.hasScrollOption()

        composeTestRule.onNodeWithText(stubDomainAnswers.first().question)
            .assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(stubDomainAnswers.last().question)
            .assertIsNotDisplayed()

        restorationTester.emulateSavedInstanceStateRestore()

        resultPage.performScrollToItemWithText(stubDomainAnswers.last().question)

        restorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNodeWithText(stubDomainAnswers.last().question)
            .assertExists().assertIsDisplayed()
        resultPage.performScrollToItemWithText(stubDomainAnswers.first().question)
        composeTestRule.onNodeWithText(stubDomainAnswers.first().question).assertExists()
            .assertIsDisplayed()
    }
}