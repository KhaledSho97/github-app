package com.khaledsh.domain.usecases.search

import androidx.paging.PagingData
import com.khaledsh.domain.models.User
import com.khaledsh.domain.repositories.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchUsersUseCaseTest {

    private val mockUserRepository: UserRepository = mockk()
    private lateinit var useCase: SearchUsersUseCase

    @BeforeEach
    fun setup() {
        useCase = SearchUsersUseCase(mockUserRepository)
    }

    @Test
    fun shouldFetchUsersFromRepository() {
        // Arrange
        val tUsername = "khaled sh"
        val tPagingData: PagingData<User> = mockk(relaxed = true)
        coEvery { mockUserRepository.searchUsers(tUsername) } returns flow {
            emit(tPagingData)
        }

        runBlocking {
            // Act
            val result = useCase.run(SearchUsersUseCase.Params(tUsername))

            // Assert
            assertEquals(result.toList().size, 1) // Emitted 1 event
            coVerify(exactly = 1) { mockUserRepository.searchUsers(tUsername) }
        }

    }

}