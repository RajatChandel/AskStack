package com.rchandel.askstack.domain

import com.rchandel.askstack.core.network.NetworkResult
import com.rchandel.askstack.domain.model.Question
import com.rchandel.askstack.domain.repository.StackOverflowRepository
import com.rchandel.askstack.domain.usecase.SearchQuestionsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchQuestionsUseCaseTest {

    private lateinit var useCase: SearchQuestionsUseCase
    private lateinit var repository: StackOverflowRepository

    @Before
    fun setUp() {
        repository = mockk()
        useCase = SearchQuestionsUseCase(repository)
    }

    @Test
    fun `when repository returns questions, use case should return success`() = runTest {
        val mockQuestions = listOf(
            Question("title1", "Author 1", 123123L, "link 1", "body1"),
            Question("title2", "Author 2", 123123L, "link 2", "body2")
        )
        coEvery { repository.searchQuestions("kotlin") } returns NetworkResult.Success(mockQuestions)
        
        val result = useCase("kotlin")

        assertTrue(result is NetworkResult.Success)
        assertEquals(mockQuestions, (result as NetworkResult.Success).data)
    }

    @Test
    fun `when repository returns error, use case should return error`() = runTest {
        val errorMessage = "Network Error"
        coEvery { repository.searchQuestions("compose") } returns NetworkResult.Error(errorMessage)

        val result = useCase("compose")

        assertTrue(result is NetworkResult.Error)
        assertEquals(errorMessage, (result as NetworkResult.Error).message)
    }

    @Test
    fun `when repository is loading, use case should return loading`() = runTest {
        coEvery { repository.searchQuestions("loading") } returns NetworkResult.Loading

        val result = useCase("loading")

        assertTrue(result is NetworkResult.Loading)
    }
}
