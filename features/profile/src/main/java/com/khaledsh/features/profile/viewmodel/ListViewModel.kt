package com.khaledsh.features.profile.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.khaledsh.core.base.BaseViewModel
import com.khaledsh.domain.usecases.profile.GetFollowersUseCase
import com.khaledsh.domain.usecases.profile.GetFollowingUseCase
import com.khaledsh.features.profile.models.ListType

import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val getFollowersUseCase: GetFollowersUseCase,
    private val getFollowingUseCase: GetFollowingUseCase
) :
    BaseViewModel<Any>() {

    suspend fun getData(listType: ListType, username: String) = when (listType) {
        ListType.FOLLOWERS -> getFollowersUseCase.invoke(GetFollowersUseCase.Params(username))
            .cachedIn(viewModelScope)
        ListType.FOLLOWING -> getFollowingUseCase.invoke(GetFollowingUseCase.Params(username))
            .cachedIn(viewModelScope)
    }

}