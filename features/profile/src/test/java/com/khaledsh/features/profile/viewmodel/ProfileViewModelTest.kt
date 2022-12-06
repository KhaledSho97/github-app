package com.khaledsh.features.profile.viewmodel

import androidx.lifecycle.Observer
import com.khaledsh.domain.models.*

import com.khaledsh.domain.usecases.profile.GetUserUseCase
import com.khaledsh.features.profile.models.ListType
import com.khaledsh.features.profile.util.InstantExecutorExtension
import com.khaledsh.features.profile.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith


@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class ProfileViewModelTest {

    private val mockGetUserUserUseCase: GetUserUseCase = mockk()
    private lateinit var viewModel: ProfileViewModel

    private val mainThreadSurrogate = newSingleThreadContext("main thread")

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = ProfileViewModel(mockGetUserUserUseCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Nested
    @DisplayName("fetchProfile")
    inner class FetchProfileTest {

        @Test
        fun shouldFetchTheProfileFromUseCase() {
            // Arrange
            val tUsername = "khaledshoushara"
            val tUser = User(
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

            coEvery { mockGetUserUserUseCase(any()) } returns flow {
                emit(Success(tUser))
            }

            runBlockingTest {
                // Act
                viewModel.fetchProfile(tUsername)

                // Assert
                verify(exactly = 1) { mockGetUserUserUseCase(any()) }
                assertEquals(viewModel.successData.getOrAwaitValue(), tUser)
            }

        }

        @Test
        fun errorDataShouldBeSetWhenUseCaseEmitsFailure() {
            // Arrange
            val tUsername = "khaledshoushara"
            val tMessage = "this is an error"
            val tFailure = Failure(message = tMessage)


            coEvery { mockGetUserUserUseCase(any()) } returns flow {
                emit(tFailure)
            }

            runBlockingTest {
                // Act
                viewModel.fetchProfile(tUsername)

                // Assert
                verify(exactly = 1) { mockGetUserUserUseCase(any()) }
                assertEquals(viewModel.errorData.getOrAwaitValue(), tFailure)
            }
        }

        @Test
        fun stateDataShouldBeSetWhenUseCaseEmitsAState() {
            // Arrange
            val tUsername = "khaledshoushara"
            val tState = State.Loading


            coEvery { mockGetUserUserUseCase(any()) } returns flow {
                emit(tState)
            }

            runBlockingTest {
                // Act
                viewModel.fetchProfile(tUsername)

                // Assert
                verify(exactly = 1) { mockGetUserUserUseCase(any()) }
                assertEquals(viewModel.stateData.getOrAwaitValue(), tState)
            }
        }

    }

    @Test
    fun navigateToListFragmentShouldDispatchEvent() {
        // Arrange
        val tListType = ListType.FOLLOWING

        val observer: Observer<ListType> = mockk(relaxUnitFun = true)
        viewModel.navigateToListFragmentEvent.observeForever(observer)

        // Act
        viewModel.navigateToListFragment(tListType)

        // Assert
        verify(exactly = 1) { observer.onChanged(tListType) }
    }
}