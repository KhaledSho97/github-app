package com.khaledsh.features.search.viewmodel

import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.khaledsh.domain.models.User
import com.khaledsh.domain.usecases.search.SearchUsersUseCase
import com.khaledsh.features.search.util.InstantExecutorExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class SearchViewModelTest {

    private val mockSearchUserUseCase: SearchUsersUseCase = mockk()
    private lateinit var viewModel: SearchViewModel

    private val mainThreadSurrogate = newSingleThreadContext("main thread")

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = SearchViewModel(mockSearchUserUseCase)
    }


    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun navigateBackShouldDispatchEvent() {
        // Arrange
        val tObserver: Observer<Unit> = mockk(relaxUnitFun = true)
        viewModel.navigateToPreviousScreenEvent.observeForever(tObserver)

        // Act
        viewModel.navigateBack()

        // Assert
        verify(exactly = 1) { tObserver.onChanged(any()) }
    }

    @Test
    fun searchUsersShouldFetchUsersFromUseCase() {
        // Arrange
        val tPagingData: PagingData<User> = mockk(relaxed = true)
        val tUsername = "khaledshoushara"

        coEvery { mockSearchUserUseCase.run(any()) } returns flow {
            emit(tPagingData)
        }

        runBlocking {
            // Act
            val result = viewModel.searchUsers(tUsername)

            // Assert
            assert(result.toList().size == 1) // Emitted 1 event
            coVerify(exactly = 1) { mockSearchUserUseCase.run(any()) }
        }

    }
}