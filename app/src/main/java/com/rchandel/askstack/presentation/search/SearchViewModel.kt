package com.rchandel.askstack.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rchandel.askstack.core.network.ConnectivityObserver
import com.rchandel.askstack.core.network.NetworkResult
import com.rchandel.askstack.domain.model.Question
import com.rchandel.askstack.domain.usecase.SearchQuestionsUseCase
import com.rchandel.askstack.presentation.search.event.SearchEvent
import com.rchandel.askstack.presentation.search.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchQuestionsUseCase: SearchQuestionsUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState
    private val _selectedQuestion = MutableStateFlow<Question?>(null)
    val selectedQuestion = _selectedQuestion.asStateFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChanged -> {
                _uiState.update {
                    it.copy(
                        query = event.query,
                        questions = it.questions,
                        buttonEnabled = enableButton(event.query),
                        message = getMessageForQuery(event.query)
                    )
                }
            }

            is SearchEvent.OnSearch -> {
                searchQuestions()
            }

            is SearchEvent.OnQuestionClicked -> {
                viewModelScope.launch {
                    _selectedQuestion.value = event.question
                }
            }
        }
    }

    fun clearSelectedQuestion() {
        _selectedQuestion.value = null
    }

    fun enableButton(query: String): Boolean {
        return query.length >= 3
    }

    private fun searchQuestions() {
        val query = _uiState.value.query
        if (query.length < 3) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = searchQuestionsUseCase(query)) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            questions = result.data,
                            error = null
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message,
                            questions = emptyList()
                        )
                    }
                }

                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun getMessageForQuery(query: String): String? {
        return when (query.length) {
            0 -> "Enter three or more characters"
            1 -> "Two more characters required"
            2 -> "One more character required"
            else -> null
        }
    }
}
