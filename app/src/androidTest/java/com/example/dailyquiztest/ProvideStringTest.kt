package com.example.dailyquiztest

import androidx.annotation.StringRes
import androidx.test.platform.app.InstrumentationRegistry
import com.example.dailyquiztest.core.ProvideString
import junit.framework.TestCase
import org.junit.Test

class ProvideStringTest {
    @Test
    fun string_returnsCorrectString() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val provideString = ProvideString.Base(context)

        @StringRes val testStringRes = R.string.start_quiz_button_text

        val expectedString = context.getString(testStringRes)
        val result = provideString.string(testStringRes)

        TestCase.assertEquals(expectedString, result)
    }
}