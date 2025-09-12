package com.example.componentpanel.model.observablemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.componentpanel.model.basemodel.ToolItemData

class ObservableToolItemData(data: ToolItemData) {
    private val _iconResId = MutableLiveData<Int?>(data.iconResId)
    private val _text = MutableLiveData<String?>(data.text)
    private val _onViewClickListener = MutableLiveData<(() -> Unit)?>(data.onViewClickListener)

    val iconResId: LiveData<Int?> = _iconResId
    val text: LiveData<String?> = _text
    val onViewClickListener: LiveData<(() -> Unit)?> = _onViewClickListener

    fun setIconResId(resId: Int?) {
        _iconResId.value = resId
    }
    fun setText(text: String?) {
        _text.value = text
    }
    fun setOnViewClickListener(listener: (() -> Unit)?) {
        _onViewClickListener.value = listener
    }
    fun setData(data: ToolItemData) {
        setIconResId(data.iconResId)
        setText(data.text)
        setOnViewClickListener(data.onViewClickListener)
    }
}