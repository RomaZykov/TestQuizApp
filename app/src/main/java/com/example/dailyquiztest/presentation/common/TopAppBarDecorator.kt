package com.example.dailyquiztest.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.dailyquiztest.presentation.ui.theme.DailyQuizTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDecorator(
    scrollBehavior: TopAppBarScrollBehavior? = null,
    backButton: @Composable (() -> Unit) = {},
    title: @Composable (() -> Unit)
) {
    AnimatedVisibility(true) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                backButton()
            },
            colors = TopAppBarDefaults.topAppBarColors()
                .copy(containerColor = DailyQuizTheme.colorScheme.primary),
            title = {
                title()
            },
            expandedHeight = 148.dp,
            scrollBehavior = scrollBehavior
        )
    }
}