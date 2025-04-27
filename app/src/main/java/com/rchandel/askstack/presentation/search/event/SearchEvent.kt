package com.rchandel.askstack.presentation.search.event

import com.rchandel.askstack.domain.model.Question

sealed class SearchEvent {
    data class OnQueryChanged(val query: String) : SearchEvent()
    data object OnSearch : SearchEvent()
    data class OnQuestionClicked(val question: Question) : SearchEvent()
}