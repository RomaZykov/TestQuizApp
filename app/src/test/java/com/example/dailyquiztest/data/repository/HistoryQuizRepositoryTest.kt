package com.example.dailyquiztest.data.repository

import com.example.dailyquiztest.core.dummyHistoryResults
import com.example.dailyquiztest.domain.repository.HistoryQuizRepository
import com.example.testing.data.local.FakeLocalHistoryDataSource
import com.example.testing.data.mapper.FakeDomainToLocalQuizResultMapper
import com.example.testing.data.mapper.FakeLocalToDomainQuizResultMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class HistoryQuizRepositoryTest {

    private lateinit var historyRepository: HistoryQuizRepository

    private lateinit var localHistoryDataSource: FakeLocalHistoryDataSource

    private lateinit var fakeLocalToDomainQuizResultMapper: FakeLocalToDomainQuizResultMapper
    private lateinit var fakeDomainToLocalQuizResultMapper: FakeDomainToLocalQuizResultMapper

    @Before
    fun setup() {
        localHistoryDataSource = FakeLocalHistoryDataSource()
        fakeLocalToDomainQuizResultMapper = FakeLocalToDomainQuizResultMapper()
        fakeDomainToLocalQuizResultMapper = FakeDomainToLocalQuizResultMapper()
        historyRepository = HistoryQuizRepositoryImpl(
            localHistoryDataSource = localHistoryDataSource,
            mapperFromLocalToDomain = fakeLocalToDomainQuizResultMapper,
            mapperFromDomainToLocal = fakeDomainToLocalQuizResultMapper
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `historyRepository save correct as local`() = runTest(
        UnconfinedTestDispatcher()
    ) {
        historyRepository.saveQuizResult(dummyHistoryResults.first())

        assertEquals(1, fakeDomainToLocalQuizResultMapper.mapCalledCount)
        assertEquals(0, fakeLocalToDomainQuizResultMapper.mapCalledCount)
        assertEquals(
            listOf(dummyHistoryResults.first()),
            actual = historyRepository.fetchQuizResults().first()
        )
    }
}
