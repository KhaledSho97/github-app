package com.khaledsh.domain.usecases.profile

import com.khaledsh.domain.models.Success
import com.khaledsh.domain.models.User
import com.khaledsh.domain.models.UserType
import com.khaledsh.domain.repositories.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetUserUseCaseTest {

    private val mockRepository: UserRepository = mockk()
    private lateinit var useCase: GetUserUseCase

    @BeforeEach
    fun setup() {
        useCase = GetUserUseCase(mockRepository)
    }


    @Test
    fun shouldFetchCachedUserFirstAndThenUserFromApi() {
        // Arrange
        val tUsername = "khaled sh"

        val dbUser =
            User(1, UserType.NORMAL, tUsername, null, "avatar value", null, null, null, null)

        val networkUser = User(
            1,
            UserType.NORMAL,
            tUsername,
            "Khaled Shoushara",
            "avatar value",
            "bio value",
            10,
            25,
            30,
        )

        coEvery { mockRepository.getUserFromLocalDb(tUsername) } returns dbUser
        coEvery { mockRepository.getUser(tUsername) } returns Success(networkUser)

        runBlocking {
            // Act
            val result = useCase.invoke(GetUserUseCase.Params(tUsername))

            // Assert
            // Should emit 4 events: Loading, DBUser, NetworkUser, Loaded
            val events = result.toList()
            assert(events.size == 4)

            val resultDbUser = (events[1] as Success<User>).successData
            assert(dbUser == resultDbUser)

            val resultNetworkUser = (events[2] as Success<User>).successData
            assert(networkUser == resultNetworkUser)
        }

    }

}