package com.example.dailyquiztest.presentation.features.quiz.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyquiztest.R
import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.domain.model.QuestionTypeDomain
import com.example.dailyquiztest.presentation.common.ActionButtonWithText
import com.example.dailyquiztest.presentation.common.StarsScore
import com.example.dailyquiztest.presentation.common.answers_group.AnswersSpecificTypeFactory
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState
import com.example.dailyquiztest.presentation.features.quiz.QuizUserActions
import com.example.dailyquiztest.presentation.ui.DailyQuizTheme

data class ResultUi(
    private val quizAnswers: List<QuizUi>
) : QuizUiState {

    @Composable
    override fun Display(timerProgress: () -> Unit, quizUserActions: QuizUserActions) {
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
            items(quizAnswers) {
                QuizResultItem(it)
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
        val title = stringResource(titleAndDescription().first)
        val description = stringResource(titleAndDescription().second)
        Column(
            modifier = Modifier.padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = DailyQuizTheme.typography.title)
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Text(
                description,
                style = DailyQuizTheme.typography.regular,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    private fun ResultActionCard(onStartNewQuizClicked: () -> Unit) {
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
                StarsScore(calculatedStarsScoreResult = calculateStarsScoreResult())
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    stringResource(
                        R.string.score_result,
                        totalCorrectAnswers(),
                        quizAnswers.first().totalQuestions
                    ),
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
    private fun QuizResultItem(quiz: QuizUi) {
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
                    text = stringResource(
                        R.string.questions_count,
                        quiz.number + 1,
                        quiz.totalQuestions
                    ),
                    style = DailyQuizTheme.typography.numberOfQuestions,
                    color = DailyQuizTheme.colorScheme.background
                )
                Image(
                    painter = painterResource(
                        if (quiz.isAnsweredCorrect) {
                            R.drawable.property_1_right
                        } else {
                            R.drawable.property_1_wrong
                        }
                    ), null
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = quiz.question,
                style = DailyQuizTheme.typography.title,
                textAlign = TextAlign.Center
            )
            Column {
                val quizOptions = AnswersSpecificTypeFactory.Base(
                    questionTypeDomain = quiz.questionTypeDomain,
                    question = quiz.question,
                    correctAnswers = listOf(quiz.correctAnswer),
                    inCorrectAnswers = quiz.incorrectAnswers,
                    checkedEnabled = false,
                    userAnswers = quiz.userAnswers
                )
                quizOptions.createGroup().DisplayGroup(true) {}
            }
        }
    }

    private fun titleAndDescription(): Pair<Int, Int> {
        return when (calculateScorePercentage()) {
            in 0 until 20 -> Pair(
                R.string.zero_stars_title,
                R.string.zero_stars_desc
            )

            in 20 until 40 -> Pair(
                R.string.one_stars_title,
                R.string.one_stars_desc
            )

            in 40 until 60 -> Pair(
                R.string.two_stars_title,
                R.string.two_stars_desc
            )

            in 60 until 80 -> Pair(
                R.string.three_stars_title,
                R.string.three_stars_desc
            )

            in 80 until 100 -> Pair(
                R.string.four_stars_title,
                R.string.four_stars_desc
            )

            else -> Pair(
                R.string.five_stars_title,
                R.string.five_stars_description
            )
        }
    }

    private fun totalCorrectAnswers() = quizAnswers.count { it.isAnsweredCorrect }
    private fun calculateScorePercentage() =
        totalCorrectAnswers() * 100 / quizAnswers.first().totalQuestions

    fun calculateStarsScoreResult(): Int {
        val percentagesList = listOf(20, 40, 60, 80, 100)
        var starsCounter = 0
        val calculatedScore = calculateScorePercentage()
        for (e in percentagesList) {
            if (calculatedScore >= e) {
                starsCounter++
            } else {
                break
            }
        }
        return starsCounter
    }
}

@Preview(showSystemUi = true)
@Composable
fun QuizResultsPreview() {
    ResultUi(
        mutableListOf<QuizUi>().apply {
            repeat(10) {
                this.add(
                    QuizUi(
                        number = it,
                        question = "Test title $it",
                        incorrectAnswers = listOf("a", "b", "c"),
                        correctAnswer = "d",
                        questionTypeDomain = if (it % 2 == 0) {
                            QuestionTypeDomain.MULTIPLE
                        } else {
                            QuestionTypeDomain.BOOLEAN
                        },
                        totalQuestions = 10,
                        userAnswers = listOf("a"),
                        categoryDomain = CategoryDomain.CARTOON_AND_ANIMATIONS,
                        difficultyDomain = DifficultyDomain.EASY
                    )
                )
            }
        }.toList()
    ).Display(timerProgress = {}, quizUserActions = QuizUserActions.ForPreview)
}
