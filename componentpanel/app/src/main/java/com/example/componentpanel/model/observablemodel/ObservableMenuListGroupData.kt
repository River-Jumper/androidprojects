package com.example.componentpanel.model.observablemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.componentpanel.model.basemodel.MenuListItemData
import com.example.componentpanel.model.basemodel.MenuListGroupData

typealias MenuGroup = MutableList<ObservableMenuListItemData>
class ObservableMenuListGroupData(data: MenuListGroupData) {
    private val _group = MutableLiveData<MenuGroup>()

    val group: LiveData<MenuGroup> = _group

    init {
        val initialGroup = mutableListOf<ObservableMenuListItemData>()
        data.group?.forEach { itemData ->
            initialGroup.add(ObservableMenuListItemData(itemData))
        }
        _group.value = initialGroup
    }

    // 如果是增删数据，请务必调用这个setGroup方法
    // 读取数据，倒是可以使用group.value方法
    // 流程：setGroup(change(getGroup()))
    fun setGroup(group: MenuGroup) {
        _group.value = group
    }

    fun getGroup() = group.value?.toMutableList() ?: mutableListOf()
}