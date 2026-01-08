package com.example.dailyquiztest.presentation.features.history

interface HistoryUserActions {
    fun onStartQuizClicked(): () -> Unit

    fun onDeleteClicked(): (Int) -> Unit

    fun onBackButtonClicked(): () -> Unit

    companion object {
        val previewHistoryUserActions = object : HistoryUserActions {
            override fun onBackButtonClicked(): () -> Unit = {}

            override fun onDeleteClicked(): (Int) -> Unit = {}

            override fun onStartQuizClicked(): () -> Unit = {}
        }
    }
}