package com.example.componentpanel.model.observablemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.componentpanel.model.basemodel.ToolGroupData

typealias ToolGroup = MutableList<ObservableToolItemData>
class ObservableToolGroupData(data: ToolGroupData) {
    private val _group = MutableLiveData<ToolGroup>()
    val group: LiveData<ToolGroup> = _group

    init {
        val initialGroup = mutableListOf<ObservableToolItemData>()
        data.group?.forEach { itemData ->
            initialGroup.add(ObservableToolItemData(itemData))
        }
        _group.value = initialGroup
    }

    // 更改数据，请务必调用这个setGroup方法
    // 读取数据，倒是可以使用group.value方法
    // 流程：setGroup(change(group.value))
    fun setGroup(group: ToolGroup) {
        _group.value = group
    }
}