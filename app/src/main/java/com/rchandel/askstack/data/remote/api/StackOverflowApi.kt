package com.rchandel.askstack.data.remote.api

import com.rchandel.askstack.data.remote.dto.QuestionDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StackOverflowApi {

    @GET("2.3/search/advanced?order=desc&sort=activity&site=stackoverflow")
    suspend fun searchQuestions(
        @Query("intitle") query: String,
        @Query("filter") filter: String = "withbody"
    ): Response<QuestionDto>
}