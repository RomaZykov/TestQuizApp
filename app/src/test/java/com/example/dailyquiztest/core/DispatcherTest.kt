package com.example.dailyquiztest.core

import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Test

class DispatcherTest {

    @Test
    fun `base dispatcher should return correct dispatchers`() {
        val dispatcherList = DispatcherList.Base()

        val uiDispatcher = dispatcherList.ui()
        val ioDispatcher = dispatcherList.io()

        assertEquals(Dispatchers.Main, uiDispatcher)
        assertEquals(Dispatchers.IO, ioDispatcher)
    }
}