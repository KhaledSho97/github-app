package com.khaledsh.features.profile.viewmodel

import androidx.lifecycle.viewModelScope
import com.khaledsh.core.base.BaseViewModel
import com.khaledsh.core.util.SingleLiveEvent
import com.khaledsh.domain.models.User
import com.khaledsh.domain.models.handle
import com.khaledsh.domain.usecases.profile.GetUserUseCase
import com.khaledsh.features.profile.models.ListType
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) :
    BaseViewModel<User>() {


    val navigateToListFragmentEvent = SingleLiveEvent<ListType>()

    fun fetchProfile(username: String) {
        viewModelScope.launch {
            getUserUseCase(GetUserUseCase.Params(username)).collect {
                it.handle(::handleState, ::handleFailure, ::handleSuccess)
            }
        }
    }

    fun navigateToListFragment(listType: ListType) = navigateToListFragmentEvent.postValue(listType)

}