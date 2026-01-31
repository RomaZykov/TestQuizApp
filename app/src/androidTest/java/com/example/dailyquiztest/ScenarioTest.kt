package com.example.dailyquiztest

import android.content.pm.ActivityInfo
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeUp
import com.example.dailyquiztest.core.Const
import com.example.dailyquiztest.core.StringResources
import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.domain.repository.HistoryRepository
import com.example.dailyquiztest.domain.repository.QuizRepository
import com.example.dailyquiztest.helpPages.FiltersPage
import com.example.dailyquiztest.helpPages.HistoryPage
import com.example.dailyquiztest.helpPages.QuizPage
import com.example.dailyquiztest.helpPages.ResultPage
import com.example.dailyquiztest.helpPages.WelcomePage
import com.example.dailyquiztest.presentation.MainActivity
import com.example.dailyquiztest.presentation.features.history.HistoryUiState
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState
import com.example.testing.dummy.dummyHistoryResults
import com.example.testing.dummy.stubQuizAnswers
import com.example.testing.dummy.stubQuizes
import com.example.testing.repository.FakeQuizRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ScenarioTest : StringResources() {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

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

    @Before
    fun setUp() {
        hiltRule.inject()
        welcomePage = WelcomePage(composeTestRule)
        historyPage = HistoryPage(composeTestRule, fakeHistoryRepository)
        filtersPage = FiltersPage(composeTestRule)
        quizPage = QuizPage(composeTestRule, fakeQuizRepository)
        resultPage = ResultPage(composeTestRule)
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
    fun deleteAllHistories_showsEmptyHistory() = runTest {
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
        filtersPage.chooseSomeCategory(Category.VIDEO_GAMES)
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeDifficulty(Difficulty.EASY)
        filtersPage.assertStartQuizButtonEnabled()
        filtersPage.clickStartQuizButton()

        quizPage.assertPageDisplayed()
        repeat(Difficulty.EASY.amountOfQuestions - 1) {
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
        resultPage.clickTopStartAgainButton()

        welcomePage.assertPageDisplayed()
        welcomePage.clickHistoryButton()

        historyPage.assertNonEmptyHistoriesDisplayed()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun showErrorMessage_whenStartingQuiz_withNoConnection() = runTest {
        (fakeQuizRepository as FakeQuizRepository).shouldSimulateNetworkError = true

        welcomePage.assertPageDisplayed()
        welcomePage.clickStartButton()

        filtersPage.assertPageDisplayed()
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeCategory(Category.VIDEO_GAMES)
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeDifficulty(Difficulty.EASY)
        filtersPage.assertStartQuizButtonEnabled()
        filtersPage.clickStartQuizButton()

        filtersPage.assertPageDisplayed()
        filtersPage.errorSnackBarWasDisplayedWithText(retrieveString(R.string.no_connection_exception))
        testDispatcher.scheduler.advanceTimeBy(1000)
        composeTestRule.waitForIdle()
        filtersPage.errorSnackBarWasDisplayedWithText(retrieveString(R.string.no_connection_exception))
        testDispatcher.scheduler.advanceTimeBy(1005)
        composeTestRule.waitForIdle()
        filtersPage.errorSnackBarNotDisplayedWithText(retrieveString(R.string.no_connection_exception))
        filtersPage.assertCategorySelected(Category.VIDEO_GAMES)
        filtersPage.assertDifficultySelected(Difficulty.EASY)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        welcomePage.assertPageDisplayed()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun showErrorMessage_whenStartingQuiz_withServiceUnavailable() = runTest {
        (fakeQuizRepository as FakeQuizRepository).shouldSimulateServiceUnavailableError = true

        welcomePage.assertPageDisplayed()
        welcomePage.clickStartButton()

        filtersPage.assertPageDisplayed()
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeCategory(Category.VIDEO_GAMES)
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeDifficulty(Difficulty.EASY)
        filtersPage.assertStartQuizButtonEnabled()
        filtersPage.clickStartQuizButton()

        filtersPage.assertPageDisplayed()
        filtersPage.errorSnackBarWasDisplayedWithText(retrieveString(R.string.service_unavailable_exception, "1"))
        testDispatcher.scheduler.advanceTimeBy(1000)
        composeTestRule.waitForIdle()
        filtersPage.errorSnackBarWasDisplayedWithText(retrieveString(R.string.service_unavailable_exception, "1"))
        testDispatcher.scheduler.advanceTimeBy(1005)
        composeTestRule.waitForIdle()
        filtersPage.errorSnackBarNotDisplayedWithText(retrieveString(R.string.service_unavailable_exception, "1"))
        filtersPage.assertCategorySelected(Category.VIDEO_GAMES)
        filtersPage.assertDifficultySelected(Difficulty.EASY)

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
        filtersPage.chooseSomeDifficulty(Difficulty.HARD)
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeCategory(Category.BOARD_GAMES)
        filtersPage.assertStartQuizButtonEnabled()
        filtersPage.clickStartQuizButton()

        testDispatcher.scheduler.advanceTimeBy(2500)
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription(Const.LoadingContDesc.toString())
            .assertExists()
            .assertIsDisplayed()
        testDispatcher.scheduler.advanceTimeBy(2000)
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription(Const.LoadingContDesc.toString())
            .assertExists()
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
        filtersPage.chooseSomeDifficulty(Difficulty.HARD)
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeCategory(Category.BOARD_GAMES)
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

        filtersPage.chooseSomeCategory(Category.FILM)
        filtersPage.chooseSomeDifficulty(Difficulty.EASY)
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

        filtersPage.chooseSomeCategory(Category.COMICS)
        filtersPage.chooseSomeDifficulty(Difficulty.EASY)
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
        resultPage.clickTopStartAgainButton()

        welcomePage.assertPageDisplayed()
    }

    // Horizontal test checks
    @Test(timeout = 60000)
    fun checkHorizontalLandscape_hasCorrectBehaviour_onWelcomeScreen() {
        welcomePage.assertPageDisplayed()
        welcomePage.assertHistoryButtonDisplayed()
        welcomePage.assertStartQuizButtonDisplayed()

        composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription(Const.WelcomeContDesc.toString())
            .performTouchInput {
                repeat(5) {
                    swipeUp()
                }
            }
        composeTestRule.waitForIdle()

        welcomePage.assertPageDisplayed()
        welcomePage.assertHistoryButtonNotDisplayed()
        welcomePage.assertStartQuizButtonDisplayed()

        composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        composeTestRule.waitForIdle()

        welcomePage.assertPageDisplayed()
        welcomePage.assertHistoryButtonNotDisplayed()

        composeTestRule.onNodeWithContentDescription(Const.WelcomeContDesc.toString())
            .performTouchInput {
                repeat(5) {
                    swipeDown()
                }
            }
        composeTestRule.waitForIdle()

        welcomePage.assertPageDisplayed()
        welcomePage.assertHistoryButtonDisplayed()
        welcomePage.assertStartQuizButtonDisplayed()
    }

    @Test(timeout = 60000)
    fun checkHorizontalLandscape_hasCorrectBehaviour_onHistoryScreen() = runTest {
        welcomePage.clickHistoryButton()

        historyPage.initWithDummyHistories()
        composeTestRule.waitForIdle()
        historyPage.assertNonEmptyHistoriesDisplayed()
        historyPage.assertHistoryTitleWithBackButtonDisplayed()

        composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        composeTestRule.waitForIdle()

        historyPage.assertNonEmptyHistoriesDisplayed()
        historyPage.assertHistoryTitleWithBackButtonDisplayed()

        composeTestRule.onNodeWithContentDescription(HistoryUiState.NonEmptyHistoryContDesc.toString())
            .performTouchInput {
                repeat(5) {
                    swipeUp()
                }
            }
        composeTestRule.waitForIdle()

        historyPage.assertNonEmptyHistoriesDisplayed()
        historyPage.assertHistoryTitleWithBackButtonNotDisplayed()
        composeTestRule.onNodeWithText(
            retrieveString(
                R.string.quiz_number_title,
                dummyHistoryResults.last().id + 1
            )
        ).assertExists().assertIsDisplayed()

        composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        composeTestRule.waitForIdle()

        historyPage.assertNonEmptyHistoriesDisplayed()
        historyPage.assertHistoryTitleWithBackButtonNotDisplayed()

        composeTestRule.onNodeWithContentDescription(HistoryUiState.NonEmptyHistoryContDesc.toString())
            .performTouchInput {
                repeat(5) {
                    swipeDown()
                }
            }
        composeTestRule.waitForIdle()

        historyPage.assertNonEmptyHistoriesDisplayed()
        historyPage.assertHistoryTitleWithBackButtonDisplayed()
    }

    @Test(timeout = 60000)
    fun checkHorizontalLandscape_hasCorrectBehaviour_onFiltersScreen() {
        welcomePage.clickStartButton()

        filtersPage.assertPageDisplayed()
        filtersPage.assertStartQuizButtonNotEnabled()
        filtersPage.chooseSomeCategory(Category.FILM)
        filtersPage.chooseSomeDifficulty(Difficulty.EASY)
        filtersPage.assertStartQuizButtonEnabled()

        composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        composeTestRule.waitForIdle()

        filtersPage.assertCategorySelected(Category.FILM)
        filtersPage.assertDifficultySelected(Difficulty.EASY)
        filtersPage.assertStartQuizButtonEnabled()

        composeTestRule.onNodeWithContentDescription(QuizUiState.FiltersContDesc.toString())
            .performTouchInput {
                repeat(5) {
                    swipeUp()
                }
            }

        composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription(QuizUiState.FiltersContDesc.toString())
            .performTouchInput {
                repeat(5) {
                    swipeDown()
                }
            }

        filtersPage.assertCategorySelected(Category.FILM)
        filtersPage.assertDifficultySelected(Difficulty.EASY)
        filtersPage.assertStartQuizButtonEnabled()
    }

    @Test(timeout = 60000)
    fun checkHorizontalLandscape_hasCorrectBehaviour_onQuizScreen() {
        welcomePage.clickStartButton()
        filtersPage.chooseSomeCategory(Category.FILM)
        filtersPage.chooseSomeDifficulty(Difficulty.EASY)
        filtersPage.clickStartQuizButton()

        quizPage.assertPageDisplayed()
        quizPage.assertNextButtonNotEnabled()
        composeTestRule.onNodeWithText(stubQuizes.first().incorrectAnswers[2])
            .performScrollTo()
            .performClick()
        composeTestRule.onNodeWithText(stubQuizes.first().incorrectAnswers[0])
            .performScrollTo()
            .performClick()
        quizPage.assertNextButtonEnabled()

        composeTestRule.onNodeWithContentDescription(QuizUiState.QuizContDesc.toString())
            .performTouchInput {
                repeat(5) {
                    swipeUp()
                }
            }

        composeTestRule.onNodeWithContentDescription(QuizUiState.QuizContDesc.toString())
            .performTouchInput {
                repeat(5) {
                    swipeDown()
                }
            }

        quizPage.assertPageDisplayed()
        composeTestRule.onNodeWithText(stubQuizes.first().incorrectAnswers[2]).assertIsSelected()
        composeTestRule.onNodeWithText(stubQuizes.first().incorrectAnswers[0]).assertIsSelected()
        quizPage.assertNextButtonEnabled()
    }

    @Test(timeout = 60000)
    fun checkHorizontalLandscape_hasCorrectBehaviour_onQuizResultScreen() {
        welcomePage.clickStartButton()
        filtersPage.chooseSomeCategory(Category.TELEVISION)
        filtersPage.chooseSomeDifficulty(Difficulty.MEDIUM)
        filtersPage.clickStartQuizButton()
        // 10 for MEDIUM
        val questionsSize = Difficulty.MEDIUM.amountOfQuestions
        repeat(questionsSize - 1) {
            quizPage.chooseOption(it % 2 == 0)
            quizPage.clickNextButton()
        }
        quizPage.chooseOption(true)
        quizPage.clickFinishQuizButton()

        resultPage.assertPageDisplayed()

        composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription(QuizUiState.ResultsContDesc.toString())
            .performTouchInput {
                repeat(5) {
                    swipeUp()
                }
            }
        composeTestRule.waitForIdle()

        resultPage.performScrollToItemWithText(stubQuizAnswers[4].question)
        resultPage.performScrollToItemWithText(stubQuizAnswers[9].question)

        composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(stubQuizes[9].question).assertIsDisplayed()
        resultPage.clickBottomStartAgainButton()

        welcomePage.assertPageDisplayed()
    }
}
