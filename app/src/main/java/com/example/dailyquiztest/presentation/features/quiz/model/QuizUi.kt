package com.example.dailyquiztest.presentation.features.quiz.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyquiztest.R
import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.domain.model.QuestionType
import com.example.dailyquiztest.presentation.common.ActionButtonWithText
import com.example.dailyquiztest.presentation.common.CommonCard
import com.example.dailyquiztest.presentation.common.TopAppBarDecorator
import com.example.dailyquiztest.presentation.common.UiLogo
import com.example.dailyquiztest.presentation.common.answers_group.AnswersSpecificTypeFactory
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState
import com.example.dailyquiztest.presentation.features.quiz.QuizUserActions
import com.example.dailyquiztest.presentation.features.quiz.model.small_screen.DialogUiState
import com.example.dailyquiztest.presentation.ui.DailyQuizTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

data class QuizUi(
    val currentNumberQuestion: Int,
    val question: String,
    val incorrectAnswers: List<String>,
    val correctAnswer: String,
    val questionType: QuestionType,
    val totalQuestions: Int,
    val userAnswers: List<String> = listOf(),
    val isAnsweredCorrect: Boolean = false,
    val category: Category,
    val difficulty: Difficulty,
    val timerDialogUi: DialogUiState
) : QuizUiState {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Display(timerProgress: () -> Unit, quizUserActions: QuizUserActions) {
        val finalUserAnswers =
            rememberSaveable(question) { mutableListOf(userAnswers.joinToString()) }
        val finalAnswersCorrect = rememberSaveable(question) { mutableStateOf(isAnsweredCorrect) }

        val actionButtonEnabled = rememberSaveable(question) { mutableStateOf(false) }
        val shouldShowBorder = rememberSaveable(question) { mutableStateOf(false) }

        val scrollState = rememberScrollState()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        val scope = rememberCoroutineScope()
        LaunchedEffect(shouldShowBorder) {
            if (shouldShowBorder.value) {
                delay(2.seconds)
                shouldShowBorder.value = false
            }
        }

        Scaffold(
            topBar = {
                TopAppBarDecorator(scrollBehavior) {
                    UiLogo(40.dp)
                }
            },
            modifier = Modifier
                .semantics {
                    contentDescription = QuizUiState.QuizContDesc.toString()
                }
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = innerPadding.calculateTopPadding())
                    .background(DailyQuizTheme.colorScheme.primary)
                    .verticalScroll(scrollState)
            ) {
                CommonCard {
                    Column(
                        modifier = Modifier
                            .background(DailyQuizTheme.colorScheme.secondary),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
//                        TimerProgress(shouldShowTimeIsOverDialog)
                        NumberOfQuestions(currentNumberQuestion, totalQuestions)
                        Question(question)
                        QuizOptions(
                            listOf(correctAnswer),
                            incorrectAnswers,
                            actionButtonEnabled,
                            shouldShowBorder.value
                        ) { userAnswers, isAnswersCorrect ->
                            finalUserAnswers.clear()
                            finalUserAnswers.addAll(userAnswers)
                            finalAnswersCorrect.value = isAnswersCorrect
                        }
                        ActionButtonWithText(
                            enabled = actionButtonEnabled.value && !shouldShowBorder.value,
                            onClick = {
                                shouldShowBorder.value = true
                                val updatedQuizUi = this@QuizUi.copy(
                                    userAnswers = finalUserAnswers,
                                    isAnsweredCorrect = finalAnswersCorrect.value
                                )
                                scope.launch {
                                    delay(2.seconds)
                                    if (currentNumberQuestion + 1 == totalQuestions) {
                                        quizUserActions.onResultClicked().invoke(updatedQuizUi)
                                    } else {
                                        quizUserActions.onNextClicked().invoke(updatedQuizUi)
                                    }
                                }
                            },
                            text = if (currentNumberQuestion + 1 == totalQuestions) {
                                R.string.finish_quiz_button_text
                            } else {
                                R.string.next_button_text
                            }
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 40.dp),
                    text = stringResource(R.string.quiz_bottom_hint),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 10.sp
                )
            }
        }
        timerDialogUi.Display {
            quizUserActions.onStartNewQuizClicked().invoke()
        }
    }

    @Composable
    private fun TimerProgress(shouldShowTimeIsOverDialog: MutableState<Boolean>) {
//        var ticks by rememberSaveable { mutableIntStateOf(0) }
//        val currentProgress = remember { mutableFloatStateOf(0f) }
//        LaunchedEffect(Unit) {
//            while (ticks < difficulty.timeToComplete / 1000) {
//                delay(1.seconds)
//                ticks++
//                currentProgress.floatValue = (ticks.toFloat() / (difficulty.timeToComplete / 1000))
//            }
//            shouldShowTimeIsOverDialog.value = true
//        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 14.dp, end = 14.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(
                        R.string.time_counter,
//                        ticks / 60,
//                        ticks % 60
                    )
                )
//                val totalMinutes = difficulty.timeToComplete / 1000 / 60
//                val totalSeconds = difficulty.timeToComplete / 1000 % 60
//                Text(text = stringResource(R.string.time_counter, totalMinutes, totalSeconds))
            }
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                gapSize = 0.dp,
                trackColor = DailyQuizTheme.colorScheme.onSecondary,
                color = DailyQuizTheme.colorScheme.tertiary,
                drawStopIndicator = {},
                progress = {
                    0f
//                    currentProgress.floatValue
                }
            )
        }
    }

    @Composable
    private fun NumberOfQuestions(currentNumberQuestion: Int, totalQuestions: Int) {
        Text(
            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp),
            text = stringResource(
                R.string.total_questions,
                currentNumberQuestion + 1,
                totalQuestions
            ),
            style = DailyQuizTheme.typography.numberOfQuestions
        )
    }

    @Composable
    private fun Question(question: String) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp),
            text = question,
            textAlign = TextAlign.Center,
            style = DailyQuizTheme.typography.questionTitle
        )
    }

    @Composable
    private fun QuizOptions(
        correctAnswers: List<String>,
        inCorrectAnswers: List<String>,
        actionButtonEnabled: MutableState<Boolean>,
        shouldShowBorderWithDelay: Boolean,
        updateUserAnswers: (List<String>, Boolean) -> Unit
    ) {
        val quizOptions = AnswersSpecificTypeFactory.Base(
            correctAnswers = correctAnswers,
            inCorrectAnswers = inCorrectAnswers,
            checkedEnabled = true,
            actionButtonEnabled = actionButtonEnabled,
            questionType = questionType,
            question = question
        )
        quizOptions.createGroup()
            .DisplayGroup(shouldShowBorderWithDelay, updateQuiz = { selectedOptions ->
                updateUserAnswers.invoke(
                    selectedOptions.filter { it.isNotEmpty() },
                    selectedOptions.filter { it.isNotEmpty() }.size == 1 && selectedOptions.contains(
                        correctAnswer
                    )
                )
            })
    }
}

@Composable
@Preview(showSystemUi = true)
private fun LongQuizPreview() {
    QuizUi(
        currentNumberQuestion = 0,
        question = "Test question Test question  Test question Test question Test question Test question Test question Test question Test question?",
        incorrectAnswers = listOf(
            "Test 1 Test 1 Test 1 Test 1 Test 1 Test 1 Test 1 Test 1 Test 1 Test 1 Test 1 Test 1 Test 1",
            "Test 2 Test 2 Test 2 Test 2 Test 2 Test 2 Test 2 Test 2 Test 2 Test 2 Test 2 Test 2 Test 2",
            "Test 3",
            "Test 4"
        ),
        correctAnswer = "i`m correct answer",
        questionType = QuestionType.MULTIPLE,
        totalQuestions = 5,
        category = Category.CARTOON_AND_ANIMATIONS,
        difficulty = Difficulty.EASY,
        timerDialogUi = DialogUiState.NoDialog
    ).Display(timerProgress = {}, quizUserActions = QuizUserActions.ForPreview)
}

@Composable
@Preview(showSystemUi = true)
private fun ShortQuizPreview() {
    QuizUi(
        currentNumberQuestion = 0,
        question = "Short Test question",
        incorrectAnswers = listOf(
            "1",
            "2",
            "4"
        ),
        correctAnswer = "i`m correct answer",
        questionType = QuestionType.MULTIPLE,
        totalQuestions = 5,
        category = Category.CARTOON_AND_ANIMATIONS,
        difficulty = Difficulty.EASY,
        timerDialogUi = DialogUiState.NoDialog
    ).Display(timerProgress = {}, quizUserActions = QuizUserActions.ForPreview)
}