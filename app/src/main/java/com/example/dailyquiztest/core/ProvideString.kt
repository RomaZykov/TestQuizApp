package com.example.dailyquiztest.core

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ProvideString {

    fun string(@StringRes stringRes: Int, vararg args: Any): String

    class Base @Inject constructor(@ApplicationContext private val context: Context) : ProvideString {
        override fun string(stringRes: Int, vararg args: Any): String {
            return context.getString(stringRes, *args)
        }
    }
}