package com.example.testing.di

import com.example.dailyquiztest.core.DispatcherList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class FakeDispatcherList : DispatcherList {
    var wasIoCalled = false
    var wasUiCalled = false

    override fun io(): CoroutineDispatcher {
        wasIoCalled = true
        return UnconfinedTestDispatcher()
    }

    override fun ui(): CoroutineDispatcher {
        wasUiCalled = true
        return UnconfinedTestDispatcher()
    }
}
