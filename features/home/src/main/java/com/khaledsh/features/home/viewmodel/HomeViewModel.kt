package com.khaledsh.features.home.viewmodel

import com.khaledsh.core.base.BaseViewModel
import com.khaledsh.core.util.SingleLiveEvent
import javax.inject.Inject

enum class Option {
    SEARCH
}

class HomeViewModel @Inject constructor() : BaseViewModel<Any>() {

    val itemClickedEvent = SingleLiveEvent<Option>()

    fun onItemClick(option: Option) = itemClickedEvent.postValue(option)

}