package com.khaledsh.domain.usecases.profile

import androidx.paging.PagingData
import com.khaledsh.domain.models.User
import com.khaledsh.domain.repositories.UserRepository
import com.khaledsh.domain.usecases.profile.GetFollowersUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetFollowersUseCaseTest {

    private val mockUserRepository: UserRepository = mockk()
    private lateinit var useCase: GetFollowersUseCase

    @BeforeEach
    fun setup() {
        useCase = GetFollowersUseCase(mockUserRepository)
    }

    @Test
    fun shouldFetchFollowersFromRepository() {
        // Arrange
        val tUsername = "khaled sh"
        val tPagingData: PagingData<User> = mockk(relaxed = true)
        coEvery { mockUserRepository.getFollowers(tUsername) } returns flow {
            emit(tPagingData)
        }

        runBlocking {
            // Act
            val result = useCase.invoke(GetFollowersUseCase.Params(tUsername))

            // Assert
            Assertions.assertEquals(result.toList().size, 1) // Emitted 1 event
            coVerify(exactly = 1) { mockUserRepository.getFollowers(tUsername) }
        }

    }

}