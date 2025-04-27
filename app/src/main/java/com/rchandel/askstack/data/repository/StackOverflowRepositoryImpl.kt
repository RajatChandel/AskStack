package com.rchandel.askstack.data.repository

import com.rchandel.askstack.core.network.NetworkResult
import com.rchandel.askstack.core.network.NetworkResult.Error
import com.rchandel.askstack.core.network.NetworkResult.Success
import com.rchandel.askstack.data.mappers.toDomain
import com.rchandel.askstack.data.remote.api.StackOverflowApi
import com.rchandel.askstack.domain.model.Question
import com.rchandel.askstack.domain.repository.StackOverflowRepository
import javax.inject.Inject

class StackOverflowRepositoryImpl @Inject constructor(
    private val api: StackOverflowApi
) : StackOverflowRepository {


    override suspend fun searchQuestions(query: String): NetworkResult<List<Question>> {
        return try {
            val response = api.searchQuestions(query)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val questions = body.items.map { it.toDomain() }
                    Success(questions)
                } else {
                    Error("No data available")
                }
            } else {
                Error(response.message())
            }
        } catch (e: Exception) {
            Error(e.localizedMessage ?: "An error occurred")
        }
    }
}