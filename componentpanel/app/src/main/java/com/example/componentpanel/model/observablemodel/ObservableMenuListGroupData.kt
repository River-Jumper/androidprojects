package com.example.componentpanel.model.observablemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.componentpanel.model.basemodel.MenuListItemData
import com.example.componentpanel.model.basemodel.MenuListGroupData

class ObservableMenuListGroupData(data: MenuListGroupData) {
    private val _group = MutableLiveData<List<MenuListItemData>?>(data.group)

    val group: LiveData<List<MenuListItemData>?> = _group

    fun setGroup(group: List<MenuListItemData>?) {
        _group.value = group
    }
    fun setData(data: MenuListGroupData) {
        setGroup(data.group)
    }       
}