package com.example.dailyquiztest.presentation.feature.quiz.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyquiztest.R
import com.example.dailyquiztest.presentation.common.ActionButtonWithText
import com.example.dailyquiztest.presentation.common.StarsScore
import com.example.dailyquiztest.presentation.feature.quiz.model.small_screen.QuizGroupUi
import com.example.dailyquiztest.presentation.feature.quiz.CalculateScore
import com.example.dailyquiztest.presentation.feature.quiz.QuizUiState
import com.example.dailyquiztest.presentation.feature.quiz.QuizUserActions
import com.example.dailyquiztest.presentation.ui.DailyQuizTheme

data class ResultUi(
    private val quizAnswers: List<QuizUi>,
    private val score: CalculateScore
) : QuizUiState {

    @Composable
    override fun Display(quizUserActions: QuizUserActions) {
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .semantics {
                    contentDescription = "result screen"
                }
                .testTag("result lazy list")
                .fillMaxWidth()
                .background(DailyQuizTheme.colorScheme.primary),
            state = listState
        ) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 42.dp),
                    text = stringResource(R.string.result_title_text),
                    style = DailyQuizTheme.typography.titleTopBar,
                    textAlign = TextAlign.Center
                )
            }
            item {
                ResultActionCard(quizUserActions.onStartNewQuizClicked())
            }
            itemsIndexed(quizAnswers) { i, answeredQuiz ->
                QuizResultItem(i, answeredQuiz)
            }
            item {
                ActionButtonWithText(
                    modifier = Modifier
                        .semantics {
                            contentDescription = "bottom start again button"
                        }
                        .padding(start = 20.dp, end = 20.dp, bottom = 72.dp),
                    onClick = {
                        quizUserActions.onStartNewQuizClicked().invoke()
                    },
                    text = R.string.start_again,
                    containerColors = ButtonDefaults.buttonColors().copy(
                        DailyQuizTheme.colorScheme.secondary
                    ),
                    textColors = DailyQuizTheme.colorScheme.tertiary
                )
            }
        }
    }

    @Composable
    fun CalculatedScoreResult() {
        Column(
            modifier = Modifier.padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(score.scoreTitleResId()),
                style = DailyQuizTheme.typography.title
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Text(
                stringResource(score.scoreDescriptionResId()),
                style = DailyQuizTheme.typography.regular,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    private fun ResultActionCard(
        onStartNewQuizClicked: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 12.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(DailyQuizTheme.colorScheme.secondary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StarsScore(calculatedStarsScoreResult = score.calculateStarsScoreResult())
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    stringResource(
                        R.string.score_result,
                        score.totalCorrectAnswers(),
                        quizAnswers.size
                    ),
                    textAlign = TextAlign.Center,
                    color = DailyQuizTheme.colorScheme.surface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                CalculatedScoreResult()
            }
            ActionButtonWithText(text = R.string.start_again, onClick = {
                onStartNewQuizClicked.invoke()
            })
        }
    }

    @Composable
    private fun QuizResultItem(i: Int, quizUi: QuizUi) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(DailyQuizTheme.colorScheme.secondary)
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.questions_count, i + 1, quizAnswers.size),
                    style = DailyQuizTheme.typography.numberOfQuestions,
                    color = DailyQuizTheme.colorScheme.background
                )
                quizUi.PrintImage()
            }
            quizUi.PrintText()
            quizUi.PrintStaticOptions()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun QuizResultsPreview() {
    val listAnsweredQuizes = mutableListOf<QuizUi>().apply {
        repeat(10) {
            val question = "Test title $it"
            val correctAnswer = "d"
            val userAnswer = "b"
            this.add(
                QuizUi(
                    number = it,
                    question = question,
                    incorrectAnswers = listOf("a", "b", "c"),
                    correctAnswer = correctAnswer,
                    totalQuestions = it,
                    userAnswer = userAnswer,
                    isAnsweredCorrect = false,
                    quizGroupUi = if (it % 2 == 0) {
                        QuizGroupUi.BooleanGroupUi(
                            question = question,
                            correctOption = correctAnswer,
                            userAnswer = userAnswer
                        )
                    } else {
                        QuizGroupUi.MultipleGroupUi(
                            question = question,
                            correctOption = correctAnswer,
                            inCorrectOptions = listOf("a", "b", "c"),
                            userAnswer = userAnswer
                        )
                    }
                )
            )
        }
    }.toList()
    ResultUi(
        listAnsweredQuizes,
        score = CalculateScore.Base(),
    ).Display(quizUserActions = QuizUserActions.ForPreview)
}
