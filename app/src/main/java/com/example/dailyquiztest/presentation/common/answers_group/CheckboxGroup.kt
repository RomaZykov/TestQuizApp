package com.example.dailyquiztest.presentation.common.answers_group

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailyquiztest.presentation.ui.theme.DailyQuizTheme

internal class CheckboxGroup(
    private val question: String,
    private val correctOptions: List<String>,
    private val inCorrectOptions: List<String>,
    private val checkedEnabled: Boolean,
    private val actionButtonEnabled: MutableState<Boolean>,
    private val userAnswers: List<String>
) : AnswersGroup {
    private val CheckboxGroupSaver = listSaver(
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
    override fun DisplayGroup(
        shouldShowBorder: Boolean,
        updateQuiz: (List<String>) -> Unit
    ) {
        val allOptions =
            rememberSaveable(question, saver = CheckboxGroupSaver) {
                mutableStateListOf<String>().apply {
                    val shuffledList = (correctOptions + inCorrectOptions).shuffled()
                    addAll(shuffledList)
                }
            }
        val selectedOptions =
            rememberSaveable(inputs = allOptions.toTypedArray(), saver = CheckboxGroupSaver) {
                mutableStateListOf<String>().apply {
                    allOptions.forEach {
                        if (userAnswers.contains(it)) {
                            this.add(userAnswers.first { answer -> answer == it })
                        } else {
                            this.add("")
                        }
                    }
                }
            }
        actionButtonEnabled.value = selectedOptions.any { allOptions.contains(it) }
        Column(
            modifier = Modifier
                .selectableGroup()
                .fillMaxWidth()
        ) {
            allOptions.forEachIndexed { index, option ->
                val optionChecked = rememberSaveable(option, question) { mutableStateOf(false) }
                val optionSelected = option == selectedOptions[index] && optionChecked.value
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            enabled = checkedEnabled,
                            selected = optionSelected,
                            onClick = {
                                optionChecked.value = !optionChecked.value
                                if (optionChecked.value) {
                                    selectedOptions[index] = option
                                } else {
                                    selectedOptions[index] = ""
                                }
                                updateQuiz.invoke(selectedOptions.toList())
                            },
                            role = Role.Checkbox
                        )
                        .then(
                            if (shouldShowBorder && (optionSelected || correctOptions.contains(
                                    option
                                ))
                            ) Modifier.border(
                                2.dp,
                                if (correctOptions.contains(option)) {
                                    DailyQuizTheme.colorScheme.onSurface
                                } else {
                                    DailyQuizTheme.colorScheme.error
                                },
                                RoundedCornerShape(16.dp)
                            ) else Modifier
                        )
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(DailyQuizTheme.colorScheme.onSecondary)
                        .requiredHeightIn(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                        checked = option == selectedOptions[index],
                        onCheckedChange = {}
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
}

@Preview(showSystemUi = true)
@Composable
private fun CheckboxGroupPreview() {
    val actionButtonEnabled = remember { mutableStateOf(true) }
    CheckboxGroup(
        "test question",
        listOf("a"),
        listOf("b", "c", "d"),
        true,
        actionButtonEnabled,
        listOf("")
    ).DisplayGroup(false) {}
}
