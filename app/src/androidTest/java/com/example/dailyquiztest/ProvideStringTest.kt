package com.example.dailyquiztest

import androidx.annotation.StringRes
import androidx.test.platform.app.InstrumentationRegistry
import com.example.dailyquiztest.core.ProvideString
import junit.framework.TestCase
import org.junit.Test

class ProvideStringBaseTest {
    @Test
    fun stringWithoutArgs_returnCorrectString() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val provideString = ProvideString.Base(context)

        @StringRes val testStringRes = R.string.start_quiz_button_text

        val expectedString = context.getString(testStringRes)
        val result = provideString.string(testStringRes)

        TestCase.assertEquals(expectedString, result)
    }

    @Test
    fun stringWithOneArg_returnCorrectString() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val provideString = ProvideString.Base(context)

        @StringRes val testStringRes = R.string.quiz_number_title
        val oneArg = 1

        val expectedString = "Quiz 1"
        val result = provideString.string(testStringRes, oneArg)

        TestCase.assertEquals(expectedString, result)
    }

    @Test
    fun stringWithTwoArgs_returnCorrectString() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val provideString = ProvideString.Base(context)

        @StringRes val testStringRes = R.string.score_result
        val firstArg = 5
        val secondArg = 10

        val expectedString = "5 из 10"
        val result = provideString.string(testStringRes, firstArg, secondArg)

        TestCase.assertEquals(expectedString, result)
    }
}