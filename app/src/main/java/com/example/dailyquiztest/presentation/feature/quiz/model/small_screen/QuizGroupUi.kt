package com.example.dailyquiztest.presentation.feature.quiz.model.small_screen

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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.dailyquiztest.presentation.ui.DailyQuizTheme

interface QuizGroupUi {
    @Composable
    fun DisplayDynamicGroup(shouldShowBorder: Boolean, updateQuiz: (String) -> Unit)

    @Composable
    fun DisplayStaticGroup()

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
        override fun DisplayStaticGroup() {
            val (selectedOption, onOptionSelected) = rememberSaveable(question) {
                mutableStateOf(userAnswer)
            }
            Options(
                correctOption,
                allOptions,
                selectedOption,
                pressedEnabled = false,
                shouldShowBorder = true,
                onOptionSelected
            ) {}
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
        listOf("true", "false"),
        userAnswer
    )

    class MultipleGroupUi(
        question: String,
        correctOption: String,
        inCorrectOptions: List<String>,
        userAnswer: String
    ) : Abstract(
        question,
        correctOption,
        (listOf(correctOption) + inCorrectOptions).shuffled(),
        userAnswer
    )
}

@Composable
private fun Options(
    correctOption: String,
    allOptions: List<String>,
    selectedOption: String,
    pressedEnabled: Boolean,
    shouldShowBorder: Boolean,
    onOptionSelected: (String) -> Unit,
    updateQuiz: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .selectableGroup()
            .fillMaxWidth()
    ) {
        allOptions.forEach { option ->
            val optionSelected = option == selectedOption
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(DailyQuizTheme.colorScheme.onSecondary)
                    .requiredHeightIn(56.dp)
                    .selectable(
                        enabled = pressedEnabled,
                        selected = optionSelected,
                        onClick = {
                            onOptionSelected(option)
                            updateQuiz.invoke(option)
                        },
                        role = Role.RadioButton
                    )
                    .then(
                        if (shouldShowBorder && (optionSelected || correctOption == option)) Modifier.border(
                            2.dp,
                            if (correctOption == option) {
                                DailyQuizTheme.colorScheme.onSurface
                            } else {
                                DailyQuizTheme.colorScheme.error
                            },
                            RoundedCornerShape(16.dp)
                        ) else Modifier
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    selected = optionSelected,
                    onClick = null
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