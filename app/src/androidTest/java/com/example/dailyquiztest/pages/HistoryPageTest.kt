package com.example.dailyquiztest.pages

import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyquiztest.help_pages.HistoryPage
import com.example.testing.repository.FakeHistoryRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoryPageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var historyPage: HistoryPage
    private val restorationTester: StateRestorationTester = StateRestorationTester(composeTestRule)

    @Before
    fun setUp() {
        historyPage = HistoryPage(composeTestRule, FakeHistoryRepository())
    }

    @Test
    fun changeOrientationInHistory() {
        restorationTester.setContent {
//            HistoryScreen(
//                rememberTestNavController(),
//            )
        }

        historyPage.assertNonEmptyHistoriesDisplayed()

        restorationTester.emulateSavedInstanceStateRestore()

        historyPage.assertNonEmptyHistoriesDisplayed()
    }
}