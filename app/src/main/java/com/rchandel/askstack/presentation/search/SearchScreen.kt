package com.rchandel.askstack.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rchandel.askstack.R
import com.rchandel.askstack.core.components.InternetBanner
import com.rchandel.askstack.core.components.LoadingIndicator
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

    val focusManager = LocalFocusManager.current
    val state by viewModel.uiState.collectAsState()
    val selectedQuestion by viewModel.selectedQuestion.collectAsState()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = dimensionResource(id = R.dimen.spacing_large), horizontal = dimensionResource(id = R.dimen.spacing_large))
    ) {
        SearchBar(
            query = state.query,
            onQueryChange = { viewModel.onEvent(SearchEvent.OnQueryChanged(it)) },
            onSearch = { viewModel.onEvent(SearchEvent.OnSearch) },
            focusManager = focusManager,
            enabled = state.buttonEnabled
        )

        when {
            state.isLoading -> {
                LoadingIndicator()
            }

            state.error != null -> {
                ErrorText(message = state.error!!)
            }

            else -> {
                if (state.message != null) {
                    InfoText(message = state.message!!)
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                if (state.questions.isNotEmpty()) {
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
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopStart
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
    ) {
        items(questions.size) { index ->
            QuestionItem(question = questions[index], onClick = { onItemClick(questions[index]) })
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
        }
    }
}


