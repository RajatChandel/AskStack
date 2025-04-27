package com.rchandel.askstack.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rchandel.askstack.core.components.InternetBanner
import com.rchandel.askstack.core.components.LoadingIndicator
import com.rchandel.askstack.core.util.formatTimestamp
import com.rchandel.askstack.domain.model.Question
import com.rchandel.askstack.presentation.detail.QuestionDetailContent
import com.rchandel.askstack.presentation.search.components.ErrorText
import com.rchandel.askstack.presentation.search.components.QuestionItem
import com.rchandel.askstack.presentation.search.event.SearchEvent
import com.rchandel.askstack.presentation.search.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {

    val isInternetAvailable by viewModel.isInternetAvailable.collectAsState()

    val focusManager = LocalFocusManager.current
    val state by viewModel.uiState.collectAsState()
    val selectedQuestion by viewModel.selectedQuestion.collectAsState()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val scope = rememberCoroutineScope()

    if (selectedQuestion != null) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.clearSelectedQuestion()
            },
            sheetState = sheetState
        ) {
            QuestionDetailContent(
                question = selectedQuestion!!,
                onClose = {
                    viewModel.clearSelectedQuestion()
                }
            )
        }
    }

    Scaffold(
        topBar = {
            InternetBanner(isInternetAvailable)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            SearchBar(
                query = state.query,
                onQueryChange = { viewModel.onEvent(SearchEvent.OnQueryChanged(it)) },
                onSearch = { viewModel.onEvent(SearchEvent.OnSearch) },
                focusManager = focusManager
            )

            when {
                state.isLoading -> {
                    LoadingIndicator()
                }
                state.error != null -> {
                    ErrorText(message = state.error!!)
                }
                state.message != null -> {
                    InfoText(message = state.message!!)
                }
                else -> {
                    QuestionList(
                        questions = state.questions,
                        onItemClick = { question ->
                            viewModel.onEvent(SearchEvent.OnQuestionClicked(question))
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun InfoText(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun QuestionList(
    questions: List<Question>,
    onItemClick: (Question) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(questions.size) { index ->
            QuestionItem(question = questions[index], onClick = { onItemClick(questions[index]) })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


