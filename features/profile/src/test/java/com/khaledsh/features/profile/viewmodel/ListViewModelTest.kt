package com.khaledsh.features.profile.viewmodel

import com.khaledsh.domain.usecases.profile.GetFollowersUseCase
import com.khaledsh.domain.usecases.profile.GetFollowingUseCase
import com.khaledsh.features.profile.models.ListType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ListViewModelTest {


    private val mockGetFollowingUseCase: GetFollowingUseCase = mockk()
    private val mockGetFollowersUseCase: GetFollowersUseCase = mockk()
    private lateinit var viewModel: ListViewModel

    @BeforeEach
    fun setup() {
        viewModel = ListViewModel(mockGetFollowersUseCase, mockGetFollowingUseCase)
    }

    @Nested
    @DisplayName("getData()")
    inner class GetDataTest {

        @Test
        fun shouldFetchDataFromGetFollowersRepoWhenListTypeIsFollowers() {
            // Arrange
            val tType = ListType.FOLLOWERS
            val tUsername = "username"
            coEvery { mockGetFollowersUseCase.invoke(any()) } returns flow {
            }

            runBlocking {
                // Act
                viewModel.getData(tType, tUsername)


                // Assert
                coVerify(exactly = 1) { mockGetFollowersUseCase.invoke(any()) }
                coVerify(exactly = 0) { mockGetFollowingUseCase.invoke(any()) }
            }
        }

        @Test
        fun shouldFetchDataFromGetFollowingRepoWhenListTypeIsFollowing() {
            // Arrange
            val tType = ListType.FOLLOWING
            val tUsername = "username"
            coEvery { mockGetFollowingUseCase.invoke(any()) } returns flow {
            }

            runBlocking {
                // Act
                viewModel.getData(tType, tUsername)

                // Assert
                coVerify(exactly = 1) { mockGetFollowingUseCase.invoke(any()) }
                coVerify(exactly = 0) { mockGetFollowersUseCase.invoke(any()) }
            }
        }

    }
}