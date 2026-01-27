package com.example.dailyquiztest.fake

import com.example.dailyquiztest.core.FormattedDate

class FakeFormattedDate : FormattedDate {

    override fun dateFinished(): String {
        return "2025"
    }

    override fun timeFinished(): String {
        return "12:55"
    }
}