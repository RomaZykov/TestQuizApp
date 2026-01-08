package com.example.dailyquiztest.data.model.network

import com.example.dailyquiztest.data.model.network.model.NetworkQuizQuestionsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApi {

    @GET("api.php")
    suspend fun fetchQuizQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: Int,
        @Query("difficulty") difficulty: String
    ): Response<NetworkQuizQuestionsList>

    companion object {
        const val BASE_URL = "https://opentdb.com/"
    }
}