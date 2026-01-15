package com.example.dailyquiztest.pages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyquiztest.R
import com.example.dailyquiztest.core.StringResources
import com.example.dailyquiztest.core.rememberTestNavController
import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.help_pages.FiltersPage
import com.example.dailyquiztest.presentation.features.quiz.QuizScreen
import com.example.dailyquiztest.presentation.features.quiz.model.FiltersUi
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
                categories = listOf(
                    Category.COMICS,
                    Category.GENERAL_KNOWLEDGE,
                    Category.BOARD_GAMES,
                    Category.HISTORY,
                    Category.ART,
                    Category.VEHICLES,
                    Category.SPORTS,
                    Category.MYTHOLOGY,
                    Category.VIDEO_GAMES
                ),
                difficulties = listOf(
                    Difficulty.EASY,
                    Difficulty.MEDIUM,
                    Difficulty.HARD
                ),
                shouldShowError = false
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
    }

    @Test
    fun changeOrientation_saveCorrectChoosing_onFiltersPage() {
        filtersPage.assertPageDisplayed()
        filtersPage.hasScrollOption()

        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeCategory(Category.VIDEO_GAMES)
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeDifficulty(Difficulty.HARD)
        filtersPage.assertStartQuizButtonEnabled()

        restorationTester.emulateSavedInstanceStateRestore()

        filtersPage.assertCategorySelected(Category.VIDEO_GAMES)
        filtersPage.assertDifficultySelected(Difficulty.HARD)
        filtersPage.assertStartQuizButtonEnabled()
    }

    @Test
    fun changeOrientationWithInitialItems_showCorrect_onFiltersPage() {
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