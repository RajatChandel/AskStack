package com.rchandel.askstack.domain.repository

import com.rchandel.askstack.core.network.NetworkResult
import com.rchandel.askstack.domain.model.Question

interface StackOverflowRepository {
    suspend fun searchQuestions(query: String): NetworkResult<List<Question>>
}