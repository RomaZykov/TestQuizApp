package com.example.dailyquiztest.core.exception

import com.example.dailyquiztest.R
import com.example.dailyquiztest.core.ProvideString
import com.example.dailyquiztest.data.model.network.exception.NoInternetConnection
import com.example.dailyquiztest.data.model.network.exception.ServiceUnavailableException
import okio.IOException
import javax.inject.Inject

interface QuizHandleError {
    fun handle(e: Exception): Exception

    class QuizFailureFactory @Inject constructor(private val provideString: ProvideString) : QuizHandleError {
        override fun handle(e: Exception): Exception {
            return when (e) {
                is IOException -> NoInternetConnection(provideString.string(R.string.no_connection_exception))
                is ServiceUnavailableException -> ServiceUnavailableException(
                    provideString.string(
                        R.string.service_unavailable_exception,
                        e.message
                    )
                )

                else -> GenericException(provideString.string(R.string.generic_exception))
            }
        }
    }
}