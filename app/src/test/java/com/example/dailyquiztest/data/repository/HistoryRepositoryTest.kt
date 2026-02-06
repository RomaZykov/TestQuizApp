package com.example.dailyquiztest.data.repository

import com.example.testing.stub.stubHistories
import com.example.dailyquiztest.domain.repository.HistoryRepository
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

class HistoryRepositoryTest {

    private lateinit var historyRepository: HistoryRepository
    private lateinit var localHistoryDataSource: FakeLocalHistoryDataSource
    private lateinit var fakeLocalToDomainQuizResultMapper: FakeLocalToDomainQuizResultMapper
    private lateinit var fakeDomainToLocalQuizResultMapper: FakeDomainToLocalQuizResultMapper

    @Before
    fun setup() {
        localHistoryDataSource = FakeLocalHistoryDataSource()
        fakeLocalToDomainQuizResultMapper = FakeLocalToDomainQuizResultMapper()
        fakeDomainToLocalQuizResultMapper = FakeDomainToLocalQuizResultMapper()
        historyRepository = HistoryRepositoryImpl(
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
        historyRepository.saveQuizResult(stubHistories.first())

        assertEquals(1, fakeDomainToLocalQuizResultMapper.mapCalledCount)
        assertEquals(0, fakeLocalToDomainQuizResultMapper.mapCalledCount)
        assertEquals(
            listOf(stubHistories.first()),
            actual = historyRepository.fetchQuizResults().first()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `historyRepository delete all histories`() = runTest(
        UnconfinedTestDispatcher()
    ) {
        stubHistories.forEach {
            historyRepository.saveQuizResult(it)
        }

        assertEquals(7, fakeDomainToLocalQuizResultMapper.mapCalledCount)
        assertEquals(0, fakeLocalToDomainQuizResultMapper.mapCalledCount)

        stubHistories.forEach {
            historyRepository.deleteQuizResult(it.number)
        }

        assertEquals(
            emptyList(),
            actual = historyRepository.fetchQuizResults().first()
        )
    }
}
