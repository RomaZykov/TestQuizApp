package com.example.dailyquiztest.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherList {

    fun ui(): CoroutineDispatcher

    fun io(): CoroutineDispatcher

    class Base : DispatcherList {
        override fun ui(): CoroutineDispatcher = Dispatchers.Main

        override fun io(): CoroutineDispatcher = Dispatchers.IO
    }
}