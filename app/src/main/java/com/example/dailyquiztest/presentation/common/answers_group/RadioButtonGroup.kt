package com.example.dailyquiztest.presentation.common.answers_group

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.dailyquiztest.presentation.ui.theme.DailyQuizTheme

internal class RadioButtonGroup(
    private val question: String,
    private val correctOption: Boolean,
    private val checkedEnabled: Boolean,
    private val actionButtonEnabled: MutableState<Boolean>,
    private val userAnswer: Boolean? = null,
) : AnswersGroup {

    @Composable
    override fun DisplayGroup(
        shouldShowBorder: Boolean,
        updateQuiz: (List<String>) -> Unit
    ) {
        val (selectedOption, onOptionSelected) = rememberSaveable(inputs = listOf(question).toTypedArray()) {
            mutableStateOf(userAnswer)
        }
        val allOptions = listOf(true, false)
        actionButtonEnabled.value = allOptions.contains(selectedOption)
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
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(DailyQuizTheme.colorScheme.onSecondary)
                        .requiredHeightIn(56.dp)
                        .selectable(
                            enabled = checkedEnabled,
                            selected = optionSelected,
                            onClick = {
                                onOptionSelected(option)
                                updateQuiz.invoke(listOf(option.toString()))
                            },
                            role = Role.RadioButton
                        )
                        .then(
                            if (shouldShowBorder && (optionSelected || correctOption == option)
                            ) Modifier.border(
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
                        selected = option == selectedOption,
                        onClick = null
                    )
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = option.toString().first().uppercase() + option.toString()
                            .takeLast(option.toString().length - 1),
                        style = DailyQuizTheme.typography.body,
                        overflow = TextOverflow.Visible
                    )
                }
            }
        }
    }
}