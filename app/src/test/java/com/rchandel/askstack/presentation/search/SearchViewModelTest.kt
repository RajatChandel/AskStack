package com.rchandel.askstack.presentation.search

import app.cash.turbine.test
import com.rchandel.askstack.core.network.NetworkResult
import com.rchandel.askstack.domain.model.Question
import com.rchandel.askstack.domain.usecase.SearchQuestionsUseCase
import com.rchandel.askstack.presentation.search.event.SearchEvent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var searchQuestionsUseCase: SearchQuestionsUseCase
    private val mockConnectivityObserver =
        mockk<com.rchandel.askstack.core.network.ConnectivityObserver>()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        searchQuestionsUseCase = mockk()
        viewModel = SearchViewModel(searchQuestionsUseCase, mockConnectivityObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `on query change, uiState should update query and buttonEnabled`() = runTest {
        val query = "stryker"
        viewModel.onEvent(SearchEvent.OnQueryChanged(query))

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(query, state.query)
            assertEquals(true, state.buttonEnabled)
        }
    }

    @Test
    fun `on query change with short query, buttonEnabled should be false`() = runTest {
        val query = "st"

        viewModel.onEvent(SearchEvent.OnQueryChanged(query))

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(query, state.query)
            assertEquals(false, state.buttonEnabled)
        }
    }

    @Test
    fun `on question clicked, selectedQuestion should update`() = runTest {
        val question = Question("title1", "Author 1", 123123L, "link 1", "body1")

        viewModel.onEvent(SearchEvent.OnQuestionClicked(question))
        advanceUntilIdle()

        viewModel.selectedQuestion.test {
            assertEquals(question, awaitItem())
        }
    }

    @Test
    fun `clearSelectedQuestion should reset selectedQuestion`() = runTest {
        val question = Question("title1", "Author 1", 123123L, "link 1", "body1")
        viewModel.onEvent(SearchEvent.OnQuestionClicked(question))

        viewModel.clearSelectedQuestion()

        viewModel.selectedQuestion.test {
            assertEquals(null, awaitItem())
        }
    }
}