package com.rchandel.askstack.presentation.search.state

import com.rchandel.askstack.domain.model.Question

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val questions: List<Question> = emptyList(),
    val message: String? = null,
    var buttonEnabled : Boolean = false
)