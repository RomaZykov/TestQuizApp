package com.example.dailyquiztest.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dailyquiztest.R

@Composable
fun UiLogo(requiredHeight: Dp = 60.dp) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(requiredHeight)
            .padding(),
        painter = painterResource(R.drawable.logo),
        contentDescription = stringResource(R.string.logo),
        alignment = Alignment.TopCenter
    )
}

@Composable
@Preview
fun UiLogoPreview() {
    UiLogo()
}