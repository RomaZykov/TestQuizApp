package com.example.dailyquiztest.presentation.common.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.dailyquiztest.R
import com.example.dailyquiztest.presentation.common.TopAppBarDecorator
import com.example.dailyquiztest.presentation.common.UiLogo
import com.example.dailyquiztest.presentation.ui.DailyQuizTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiLoading() {
    val screenContDesc = stringResource(R.string.loading_screen_cont_desc)
    Scaffold(
        modifier = Modifier.semantics {
            contentDescription = screenContDesc
        },
        topBar = {
            TopAppBarDecorator {}
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DailyQuizTheme.colorScheme.primary)
                .padding(innerPadding)
        ) {
            UiLogo()
            Spacer(modifier = Modifier.padding(vertical = 24.dp))
            Loading()
        }
    }
}