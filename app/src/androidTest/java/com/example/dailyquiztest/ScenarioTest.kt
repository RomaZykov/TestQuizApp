package com.example.dailyquiztest

import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyquiztest.core.StringResources
import com.example.dailyquiztest.core.dummyHistoryResults
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.domain.repository.HistoryRepository
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.dailyquiztest.pages.FiltersPage
import com.example.dailyquiztest.pages.HistoryPage
import com.example.dailyquiztest.pages.QuizPage
import com.example.dailyquiztest.pages.ResultPage
import com.example.dailyquiztest.pages.WelcomePage
import com.example.dailyquiztest.presentation.features.welcome.navigation.WelcomeRoute
import com.example.dailyquiztest.presentation.main_navigation.MainNavigation
import com.example.dayliquiztest.HiltComponentActivity
import com.example.testing.repository.FakeQuizRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ScenarioTest : StringResources() {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Inject
    lateinit var fakeHistoryRepository: HistoryRepository

    @Inject
    lateinit var fakeQuizRepository: QuizRepository

    @Inject
    lateinit var testDispatcher: TestDispatcher

    private lateinit var welcomePage: WelcomePage
    private lateinit var historyPage: HistoryPage
    private lateinit var quizPage: QuizPage
    private lateinit var resultPage: ResultPage
    private lateinit var filtersPage: FiltersPage

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()
        welcomePage = WelcomePage(composeTestRule)
        historyPage = HistoryPage(composeTestRule, fakeHistoryRepository)
        filtersPage = FiltersPage(composeTestRule)
        quizPage = QuizPage(composeTestRule, fakeQuizRepository)
        resultPage = ResultPage(composeTestRule)
        composeTestRule.activity.setContent {
            navController = rememberTestNavController()
            MainNavigation(
                navController = navController,
                startDestination = WelcomeRoute
            )
        }
    }

    @Test
    fun checkHistoryScreen_whenNoHistories() {
        welcomePage.assertPageDisplayed()
        welcomePage.clickHistoryButton()

        historyPage.assertEmptyHistoriesDisplayed()
        historyPage.clickBackButton()

        welcomePage.assertPageDisplayed()
    }

    @Test
    fun startQuizFromHistoryPage_whenEmpty() {
        welcomePage.assertPageDisplayed()
        welcomePage.clickHistoryButton()

        historyPage.assertEmptyHistoriesDisplayed()
        historyPage.clickStartQuizButtonWhenEmptyHistory()

        filtersPage.assertPageDisplayed()
    }

    @Test
    fun fromWelcomeScreen_toNonEmptyHistoryScreen_thenBack() = runTest {
        welcomePage.assertPageDisplayed()
        welcomePage.clickHistoryButton()

        historyPage.initWithDummyHistories()
        historyPage.assertNonEmptyHistoriesDisplayed()

        historyPage.clickBackButton()
        welcomePage.assertPageDisplayed()
    }

    @Test
    fun deleteAllHistories() = runTest {
        welcomePage.assertPageDisplayed()
        welcomePage.clickHistoryButton()

        historyPage.initWithDummyHistories()
        historyPage.assertNonEmptyHistoriesDisplayed()

        var totalHistories = dummyHistoryResults.size
        repeat(totalHistories) {
            historyPage.longPressToDeleteHistoryByIndex(totalHistories - 1)
            totalHistories--
        }

        historyPage.assertEmptyHistoriesDisplayed()

        historyPage.clickBackButton()
        welcomePage.assertPageDisplayed()
    }

    @Test
    fun deleteHistoryFromMiddle() = runTest {
        welcomePage.assertPageDisplayed()
        welcomePage.clickHistoryButton()

        historyPage.initWithDummyHistories()
        historyPage.assertNonEmptyHistoriesDisplayed()

        historyPage.longPressToDeleteHistoryByIndex(2)
        historyPage.assertSnackBarAboutDeletingExist()
        historyPage.assertHistoryNonExistWithText("Quiz 3")

        historyPage.clickBackButton()
        welcomePage.assertPageDisplayed()
    }

    @Test
    fun fromStart_toFinishQuiz_withCheckingHistory() = runTest {
        welcomePage.assertPageDisplayed()
        welcomePage.clickStartButton()

        filtersPage.assertPageDisplayed()
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeCategory(CategoriesTypes.VIDEO_GAMES)
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeDifficulty(DifficultiesTypes.EASY)
        filtersPage.assertStartQuizButtonEnabled()
        filtersPage.clickStartQuizButton()

        quizPage.assertPageDisplayed()
        repeat(DifficultiesTypes.EASY.amountOfQuestions - 1) {
            quizPage.assertNextButtonNotEnabled()
            quizPage.chooseOption(true)
            quizPage.clickNextButton()
        }

        quizPage.assertFinishQuizButtonNotEnabled()
        quizPage.chooseOption(true)
        quizPage.clickFinishQuizButton()

        resultPage.assertPageDisplayed()
        resultPage.assertFinalResultContains(
            retrieveString(R.string.five_stars_title),
            retrieveString(R.string.five_stars_description)
        )
        resultPage.clickStartAgainButton()

        welcomePage.assertPageDisplayed()
        welcomePage.clickHistoryButton()

        historyPage.assertNonEmptyHistoriesDisplayed()
    }

    @Test
    fun showErrorMessage_whenStartingQuiz_withNoConnection() = runTest {
        (fakeQuizRepository as FakeQuizRepository).shouldSimulateError = true

        welcomePage.assertPageDisplayed()
        welcomePage.clickStartButton()

        filtersPage.assertPageDisplayed()
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeCategory(CategoriesTypes.VIDEO_GAMES)
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeDifficulty(DifficultiesTypes.EASY)
        filtersPage.assertStartQuizButtonEnabled()
        filtersPage.clickStartQuizButton()

        filtersPage.assertPageDisplayed()
        filtersPage.errorSnackBarWasDisplayed()

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        welcomePage.assertPageDisplayed()
    }

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalTestApi::class)
    @Test
    fun showLoadingPage_whenQuizIsNotPreparedForFiveSec() = runTest {
        (fakeQuizRepository as FakeQuizRepository).shouldSimulateFiveSecDelay = true

        welcomePage.assertPageDisplayed()
        welcomePage.clickStartButton()

        filtersPage.assertPageDisplayed()
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeDifficulty(DifficultiesTypes.HARD)
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeCategory(CategoriesTypes.BOARD_GAMES)
        filtersPage.assertStartQuizButtonEnabled()
        filtersPage.clickStartQuizButton()

        testDispatcher.scheduler.advanceTimeBy(2500)
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("LoadingScreen").assertExists()
            .assertIsDisplayed()
        testDispatcher.scheduler.advanceTimeBy(2000)
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("LoadingScreen").assertExists()
            .assertIsDisplayed()
        // More than 5 sec
        testDispatcher.scheduler.advanceTimeBy(600)
        composeTestRule.waitForIdle()

        quizPage.assertPageDisplayed()
    }

    @Test
    fun failQuizOnHardDifficulty_whenNoTimeLeft() = runTest {
        welcomePage.assertPageDisplayed()
        welcomePage.clickStartButton()

        filtersPage.assertPageDisplayed()
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeDifficulty(DifficultiesTypes.HARD)
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeCategory(CategoriesTypes.BOARD_GAMES)
        filtersPage.assertStartQuizButtonEnabled()
        filtersPage.clickStartQuizButton()

        quizPage.assertPageDisplayed()
        quizPage.chooseOption(false)

        // Failed after 60 seconds for HARD difficulty
        composeTestRule.mainClock.advanceTimeBy(60005)
        composeTestRule.waitForIdle()
        quizPage.assertFailedDialogDisplayed()
        quizPage.clickStartAgainButton()

        welcomePage.assertPageDisplayed()
    }

    @Test
    fun finishQuiz_whereAllOptionsAreTrueOrFalse_withFourCorrectAnswersOutOfFive() = runTest {
        (fakeQuizRepository as FakeQuizRepository).shouldSimulateOnlyTrueFalseOptions = true

        welcomePage.assertPageDisplayed()
        welcomePage.clickStartButton()

        filtersPage.chooseSomeCategory(CategoriesTypes.FILM)
        filtersPage.chooseSomeDifficulty(DifficultiesTypes.EASY)
        filtersPage.clickStartQuizButton()

        repeat(4) {
            quizPage.assertNextButtonNotEnabled()
            quizPage.chooseOption(true)
            quizPage.clickNextButton()
        }

        quizPage.assertFinishQuizButtonNotEnabled()
        quizPage.chooseOption(false)
        quizPage.clickFinishQuizButton()

        resultPage.assertPageDisplayed()
        resultPage.assertFinalResultContains(
            retrieveString(R.string.four_stars_title),
            retrieveString(R.string.four_stars_desc)
        )
        resultPage.clickBottomStartAgainButton()

        welcomePage.assertPageDisplayed()
    }

    @Test
    fun finishQuiz_whereAllOptionsAreTrueOrFalse_withAllInCorrectAnswers() = runTest {
        (fakeQuizRepository as FakeQuizRepository).shouldSimulateOnlyTrueFalseOptions = true

        welcomePage.assertPageDisplayed()
        welcomePage.clickStartButton()

        filtersPage.chooseSomeCategory(CategoriesTypes.COMICS)
        filtersPage.chooseSomeDifficulty(DifficultiesTypes.EASY)
        filtersPage.clickStartQuizButton()

        repeat(4) {
            quizPage.assertNextButtonNotEnabled()
            quizPage.chooseOption(false)
            quizPage.clickNextButton()
        }

        quizPage.assertFinishQuizButtonNotEnabled()
        quizPage.chooseOption(false)
        quizPage.clickFinishQuizButton()

        resultPage.assertPageDisplayed()
        resultPage.assertFinalResultContains(
            retrieveString(R.string.zero_stars_title),
            retrieveString(R.string.zero_stars_desc)
        )
        resultPage.clickStartAgainButton()

        welcomePage.assertPageDisplayed()
    }

    @Composable
    private fun rememberTestNavController(): TestNavHostController {
        val context = LocalContext.current
        return remember {
            TestNavHostController(context).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
        }
    }
}
