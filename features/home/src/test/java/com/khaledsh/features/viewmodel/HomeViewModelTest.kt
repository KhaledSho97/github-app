package com.khaledsh.features.viewmodel

import androidx.lifecycle.Observer
import com.khaledsh.features.home.viewmodel.HomeViewModel
import com.khaledsh.features.home.viewmodel.Option
import com.khaledsh.features.util.InstantExecutorExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @BeforeEach
    private fun setup() {
        viewModel = HomeViewModel()
    }

    @Test
    fun onItemClickShouldDispatchEventWithTheItemPassed() {
        // Arrange
        val tOption = Option.SEARCH
        val observer: Observer<Option> = mockk(relaxUnitFun = true)
        viewModel.itemClickedEvent.observeForever(observer)

        // Act
        viewModel.onItemClick(tOption)

        // Assert
        verify(exactly = 1) { observer.onChanged(tOption) }
    }
}