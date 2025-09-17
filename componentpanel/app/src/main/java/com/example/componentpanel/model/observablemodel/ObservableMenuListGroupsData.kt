package com.example.componentpanel.model.observablemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.componentpanel.model.basemodel.MenuListGroupsData

typealias MenuListGroups = MutableList<ObservableMenuListGroupData>
class ObservableMenuListGroupsData(data: MenuListGroupsData) {
    private val _groups = MutableLiveData<MenuListGroups>()
    val groups: LiveData<MenuListGroups> = _groups

    init {
        val initialGroups = mutableListOf<ObservableMenuListGroupData>()
        data.groups?.forEach { itemData ->
            initialGroups.add(ObservableMenuListGroupData(itemData))
        }
        _groups.value = initialGroups
    }
    
    // 如果是增删数据，请务必调用这个setGroups方法
    // 读取数据，倒是可以使用groups.value方法
    // 流程：setGroups(change(getGroups()))
    fun setGroups(groups: MenuListGroups) {
        _groups.value = groups
    }

    fun getGroups() = groups.value?.toMutableList() ?: mutableListOf()
}