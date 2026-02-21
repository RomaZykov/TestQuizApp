package com.example.dailyquiztest.pages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyquiztest.R
import com.example.dailyquiztest.core.StringResources
import com.example.dailyquiztest.core.rememberTestNavController
import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.helpPages.FiltersPage
import com.example.dailyquiztest.presentation.feature.quiz.QuizScreenUi
import com.example.dailyquiztest.presentation.feature.quiz.model.FiltersUi
import com.example.dailyquiztest.presentation.feature.quiz.model.small_screen.ErrorUiState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FiltersPageTest : StringResources() {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var filtersPage: FiltersPage

    private val restorationTester: StateRestorationTester = StateRestorationTester(composeTestRule)

    @Before
    fun setUp() {
        filtersPage = FiltersPage(composeTestRule)
        restorationTester.setContent {
            val uiState = FiltersUi(
                categories = CategoryDomain.entries,
                difficulties = DifficultyDomain.entries,
                ErrorUiState.EmptyUi
            )
            QuizScreenUi(
                uiState = uiState,
                navController = rememberTestNavController(),
                prepareQuizGame = { _, _ -> },
                saveQuizAnswer = {},
                retrieveNextAnswer = {},
                showResult = {},
                navigateToWelcome = {},
                stopTimer = {}
            )
        }
    }

    @Test
    fun changeOrientation_saveCorrectChoosing() {
        filtersPage.assertPageDisplayed()
        filtersPage.hasScrollOption()

        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeCategory(CategoryDomain.VIDEO_GAMES)
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeDifficulty(DifficultyDomain.HARD)
        filtersPage.assertStartQuizButtonEnabled()

        restorationTester.emulateSavedInstanceStateRestore()

        filtersPage.assertCategorySelected(CategoryDomain.VIDEO_GAMES)
        filtersPage.assertDifficultySelected(DifficultyDomain.HARD)
        filtersPage.assertStartQuizButtonEnabled()
    }

    @Test
    fun changeOrientationWithInitialItems_showCorrectContent() {
        filtersPage.assertPageDisplayed()
        filtersPage.hasScrollOption()

        composeTestRule.onNodeWithText(retrieveString(R.string.category_menu_text)).assertExists()
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(retrieveString(R.string.difficulty_menu_text)).assertExists()
            .assertIsDisplayed()
        filtersPage.assertStartQuizButtonNotEnabled()

        restorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNodeWithText(retrieveString(R.string.category_menu_text)).assertExists()
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(retrieveString(R.string.difficulty_menu_text)).assertExists()
            .assertIsDisplayed()
        filtersPage.assertStartQuizButtonNotEnabled()
    }
}