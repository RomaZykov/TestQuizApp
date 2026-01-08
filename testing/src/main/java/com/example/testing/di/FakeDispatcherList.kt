package com.example.testing.di

import com.example.dailyquiztest.core.DispatcherList
import com.example.dailyquiztest.core.di.DispatchersModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DispatchersModule::class])
object TestDispatchersModule {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Singleton
    @Provides
    fun provideTestDispatcher(): TestDispatcher {
        return UnconfinedTestDispatcher()
    }

    @Provides
    @Singleton
    fun provideDispatcherList(dispatcher: TestDispatcher): DispatcherList = FakeDispatcherList(dispatcher)
}

@OptIn(ExperimentalCoroutinesApi::class)
class FakeDispatcherList(private val dispatcher: TestDispatcher) : DispatcherList {
    var wasIoCalled = false
    var wasUiCalled = false

    override fun io(): CoroutineDispatcher {
        wasIoCalled = true
        return dispatcher
    }

    override fun ui(): CoroutineDispatcher {
        wasUiCalled = true
        return dispatcher
    }
}
