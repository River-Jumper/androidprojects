package com.example.componentpanel.model.observablemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.componentpanel.model.basemodel.TitleData

class ObservableTitleData(data: TitleData) {
    private val _startIconResId = MutableLiveData<Int?>(data.startIconResId)
    private val _mainTitle = MutableLiveData<String?>(data.mainTitle)
    private val _subTitle = MutableLiveData<String?>(data.subTitle)
    private val _endIconResId = MutableLiveData<Int?>(data.endIconResId)
    private val _onEndIconClickedListener = MutableLiveData<(() -> Unit)?>(data.onEndIconClickedListener)

    val startIconResId: LiveData<Int?> = _startIconResId
    val mainTitle: LiveData<String?> = _mainTitle
    val subTitle: LiveData<String?> = _subTitle
    val endIconResId: LiveData<Int?> = _endIconResId
    val onEndIconClickedListener: LiveData<(() -> Unit)?> = _onEndIconClickedListener

    fun setStartIconResId(resId: Int?) {
        _startIconResId.value = resId
    }
    fun setMainTitle(mainTitle: String?) {
        _mainTitle.value = mainTitle
    }
    fun setSubTitle(subTitle: String?) {
        _subTitle.value = subTitle
    }
    fun setEndIconResId(resId: Int?) {
        _endIconResId.value = resId
    }
    fun setOnEndIconClickedListener(listener: (() -> Unit)?) {
        _onEndIconClickedListener.value = listener
    }
    fun setData(data: TitleData) {
        setStartIconResId(data.startIconResId)
        setMainTitle(data.mainTitle)
        setSubTitle(data.subTitle)
        setEndIconResId(data.endIconResId)
        setOnEndIconClickedListener(data.onEndIconClickedListener)
    }
}