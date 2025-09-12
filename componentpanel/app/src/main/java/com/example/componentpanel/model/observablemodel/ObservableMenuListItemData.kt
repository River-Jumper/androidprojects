package com.example.componentpanel.model.observablemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.componentpanel.model.basemodel.MenuListGroupData
import com.example.componentpanel.model.basemodel.MenuListItemData

typealias Groups = List<MenuListGroupData>
class ObservableMenuListItemData(data: MenuListItemData) {
    private val _startIconResId = MutableLiveData<Int?>(data.startIconResId)
    private val _text = MutableLiveData<String?>(data.text)
    private val _subText = MutableLiveData<String?>(data.subText)
    private val _endIconResId = MutableLiveData<Int?>(data.endIconResId)
    private val _onViewClickListener = MutableLiveData<(() -> Unit)?>(data.onViewClickListener)
    private val _nextGroups = MutableLiveData<Groups?>(data.nextGroups)

    val startIconResId: LiveData<Int?> = _startIconResId
    val text: LiveData<String?> = _text
    val subText: LiveData<String?> = _subText
    val endIconResId: LiveData<Int?> = _endIconResId
    val onViewClickListener: LiveData<(() -> Unit)?> = _onViewClickListener
    val nextGroups: LiveData<Groups?> = _nextGroups

    fun setStartIconResId(resId: Int?) {
        _startIconResId.value = resId
    }
    fun setText(text: String?) {
        _text.value = text
    }
    fun setSubText(subText: String?) {
        _subText.value = subText
    }
    fun setEndIconResId(resId: Int?) {
        _endIconResId.value = resId
    }
    fun setOnViewClickListener(listener: (() -> Unit)?) {
        _onViewClickListener.value = listener
    }
    fun setNextGroups(groups: Groups?) {
        _nextGroups.value = groups
    }
    fun setData(data: MenuListItemData) {
        setStartIconResId(data.startIconResId)
        setText(data.text)
        setSubText(data.subText)
        setEndIconResId(data.endIconResId)
        setOnViewClickListener(data.onViewClickListener)
        setNextGroups(data.nextGroups)
    }
}