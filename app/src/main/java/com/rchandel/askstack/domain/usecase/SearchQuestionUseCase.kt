package com.rchandel.askstack.domain.usecase

import com.rchandel.askstack.core.network.NetworkResult
import com.rchandel.askstack.domain.model.Question
import com.rchandel.askstack.domain.repository.StackOverflowRepository
import javax.inject.Inject

class SearchQuestionsUseCase @Inject constructor(
    private val repository: StackOverflowRepository
) {
    suspend operator fun invoke(query: String): NetworkResult<List<Question>> {
        return repository.searchQuestions(query)
    }
}