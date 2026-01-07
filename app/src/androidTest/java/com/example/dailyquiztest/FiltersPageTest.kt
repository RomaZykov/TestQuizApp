package com.example.dailyquiztest

import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyquiztest.core.rememberTestNavController
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.pages.FiltersPage
import com.example.dailyquiztest.presentation.features.quiz.QuizScreen
import com.example.dailyquiztest.presentation.features.quiz.model.FiltersUi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FiltersPageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var filtersPage: FiltersPage

    private val restorationTester: StateRestorationTester = StateRestorationTester(composeTestRule)

    @Before
    fun setUp() {
        filtersPage = FiltersPage(composeTestRule)
    }

    @Test
    fun changeOrientation_onFiltersPage() {
        restorationTester.setContent {
            val uiState = FiltersUi(
                categories = listOf(
                    CategoriesTypes.COMICS,
                    CategoriesTypes.GENERAL_KNOWLEDGE,
                    CategoriesTypes.BOARD_GAMES,
                ),
                difficulties = listOf(
                    DifficultiesTypes.EASY,
                    DifficultiesTypes.MEDIUM,
                    DifficultiesTypes.HARD,
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

        filtersPage.assertPageDisplayed()

//        restorationTester.emulateSavedInstanceStateRestore()

//        historyPage.assertNonEmptyHistoriesDisplayed()
    }
}