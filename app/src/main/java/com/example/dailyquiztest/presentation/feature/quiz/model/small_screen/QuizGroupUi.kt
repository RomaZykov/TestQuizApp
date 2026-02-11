package com.example.dailyquiztest.presentation.feature.quiz.model.small_screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailyquiztest.R
import com.example.dailyquiztest.presentation.ui.DailyQuizTheme

interface QuizGroupUi {
    @Composable
    fun DisplayDynamicGroup(shouldShowBorder: Boolean, updateQuiz: (String) -> Unit)

    @Composable
    fun DisplayStaticGroup(updatedUserAnswer: String)

    abstract class Abstract(
        private val question: String,
        private val correctOption: String,
        private val allOptions: List<String>,
        private val userAnswer: String
    ) : QuizGroupUi {
        private val GroupSaver = listSaver(
            save = { stateList ->
                mutableStateListOf<String>().apply {
                    addAll(stateList.toList())
                }
            },
            restore = { restoredList ->
                mutableStateListOf<String>().apply {
                    if (restoredList.isNotEmpty()) {
                        addAll(restoredList)
                    }
                }
            }
        )

        @Composable
        override fun DisplayStaticGroup(updatedUserAnswer: String) {
            Options(
                correctOption,
                allOptions,
                updatedUserAnswer,
                pressedEnabled = false,
                shouldShowBorder = true
            )
        }

        @Composable
        override fun DisplayDynamicGroup(
            shouldShowBorder: Boolean,
            updateQuiz: (String) -> Unit
        ) {
            val (selectedOption, onOptionSelected) = rememberSaveable(question) {
                mutableStateOf(userAnswer)
            }
            val allOptions =
                rememberSaveable(question, saver = GroupSaver) {
                    mutableStateListOf<String>().apply {
                        addAll(allOptions)
                    }
                }
            Options(
                correctOption,
                allOptions,
                selectedOption,
                true,
                shouldShowBorder,
                onOptionSelected,
                updateQuiz
            )
        }
    }

    class BooleanGroupUi(
        question: String,
        correctOption: String,
        userAnswer: String,
    ) : Abstract(
        question,
        correctOption,
        listOf("True", "False"),
        userAnswer
    )

    class MultipleGroupUi(
        question: String,
        inCorrectOptions: List<String>,
        correctOption: String,
        userAnswer: String
    ) : Abstract(
        question,
        correctOption,
        (listOf(correctOption) + inCorrectOptions).shuffled(),
        userAnswer
    )
}

private data class SelectableOptionMetaData(
    val optionTestTag: String = "green edge",
    val borderColor: Color = Color.Green,
    @DrawableRes val iconOption: Int = R.drawable.property_right,
    val iconContentDesc: String = "correct option icon",
)

private fun configureSelectableOption(
    shouldShowBorder: Boolean,
    isSelected: Boolean,
    isCorrect: Boolean
): SelectableOptionMetaData {
    return when {
        shouldShowBorder && isSelected && isCorrect -> SelectableOptionMetaData()
        shouldShowBorder && isSelected -> SelectableOptionMetaData(
            optionTestTag = "red edge",
            borderColor = Color.Red,
            iconOption = R.drawable.property_wrong,
            iconContentDesc = "wrong option icon"
        )

        shouldShowBorder && isCorrect -> SelectableOptionMetaData()
        !shouldShowBorder && isSelected -> SelectableOptionMetaData(
            optionTestTag = "no edge",
            borderColor = Color.Transparent,
            iconOption = R.drawable.selected_radio_button,
            iconContentDesc = "selected option icon"
        )

        else -> SelectableOptionMetaData(
            optionTestTag = "no edge",
            borderColor = Color.Transparent,
            iconOption = R.drawable.radio_button_default,
            iconContentDesc = "default option icon"
        )
    }
}

@Composable
private fun Options(
    correctOption: String,
    allOptions: List<String>,
    selectedOption: String,
    pressedEnabled: Boolean,
    shouldShowBorder: Boolean,
    onOptionSelected: (String) -> Unit = {},
    onUpdateQuiz: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .selectableGroup()
            .fillMaxWidth()
    ) {
        allOptions.forEach { option ->
            val isSelected = option.equals(selectedOption, ignoreCase = true)
            val isCorrect = option.equals(correctOption, ignoreCase = true)
            val groupMetaData = configureSelectableOption(shouldShowBorder, isSelected, isCorrect)
            val borderModifier = Modifier.border(
                2.dp,
                groupMetaData.borderColor,
                RoundedCornerShape(16.dp)
            )
            Row(
                Modifier
                    .testTag(groupMetaData.optionTestTag)
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(DailyQuizTheme.colorScheme.onSecondary)
                    .requiredHeightIn(56.dp)
                    .selectable(
                        enabled = pressedEnabled,
                        selected = isSelected,
                        onClick = {
                            onOptionSelected(option)
                            onUpdateQuiz.invoke(option)
                        },
                        role = Role.RadioButton
                    )
                    .then(borderModifier),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    painter = painterResource(id = groupMetaData.iconOption),
                    contentDescription = groupMetaData.iconContentDesc
                )
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = option,
                    style = DailyQuizTheme.typography.body,
                    overflow = TextOverflow.Visible
                )
            }
        }
    }
}


@Composable
@Preview
private fun BooleanStaticPreview() {
    QuizGroupUi.BooleanGroupUi(
        question = "abc",
        correctOption = "false",
        userAnswer = "true"
    ).DisplayStaticGroup("true")
}