package com.example.componentpanel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TitleBarViewModel : ViewModel() {
    private val _starState = MutableLiveData(false)
    val starState: LiveData<Boolean> = _starState

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    fun toggleStarState() {
        _starState.value?.let {
            _starState.value = !it
        }

        _starState.value?.run {
            if (this) {
                _toastMessage.value = "收藏"
            }
            else {
                _toastMessage.value = "取消收藏"
            }
        }
    }
}