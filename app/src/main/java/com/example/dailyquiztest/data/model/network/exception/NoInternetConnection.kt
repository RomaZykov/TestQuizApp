package com.example.dailyquiztest.data.model.network.exception

import okio.IOException

class NoInternetConnection(override val message: String = "") : IOException(message)