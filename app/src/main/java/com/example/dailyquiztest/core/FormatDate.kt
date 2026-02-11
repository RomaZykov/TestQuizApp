package com.example.dailyquiztest.core

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

interface FormatDate {
    fun timeFinished(): String
    fun dateFinished(): String

    class Base @Inject constructor() : FormatDate {
        override fun dateFinished(): String {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd MMMM", Locale.getDefault())
            val formatted = current.format(formatter)
            return formatted
        }

        override fun timeFinished(): String {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val formatted = current.format(formatter)
            return formatted
        }
    }
}