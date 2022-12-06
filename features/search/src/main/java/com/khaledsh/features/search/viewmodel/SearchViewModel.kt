package com.khaledsh.features.search.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.khaledsh.core.base.BaseViewModel
import com.khaledsh.core.util.SingleLiveEvent
import com.khaledsh.domain.models.User
import com.khaledsh.domain.usecases.search.SearchUsersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val searchUsersUseCase: SearchUsersUseCase) :
    BaseViewModel<List<User>>() {

    val navigateToPreviousScreenEvent = SingleLiveEvent<Unit>()

    fun navigateBack() = navigateToPreviousScreenEvent.call()

    suspend fun searchUsers(query: String): Flow<PagingData<User>> =
        searchUsersUseCase.run(SearchUsersUseCase.Params(query)).cachedIn(viewModelScope)


}