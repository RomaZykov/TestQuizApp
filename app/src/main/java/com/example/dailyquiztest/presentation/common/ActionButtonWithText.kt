package com.example.dailyquiztest.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dailyquiztest.presentation.ui.DailyQuizTheme

@Composable
fun ActionButtonWithText(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: Int,
    containerColors: ButtonColors = ButtonDefaults.buttonColors().copy(
        DailyQuizTheme.colorScheme.primary
    ),
    textColors: Color = Color.Unspecified,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 6.dp)
            .padding(16.dp)
            .then(modifier),
        enabled = enabled,
        onClick = { onClick.invoke() },
        shape = RoundedCornerShape(16.dp),
        colors = containerColors
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(text),
            style = DailyQuizTheme.typography.button,
            color = textColors
        )
    }
}