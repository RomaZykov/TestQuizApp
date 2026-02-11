package com.example.dailyquiztest.data.model.network.exception

import android.os.Message

class ServiceUnavailableException(override val message: String) : Exception(message)