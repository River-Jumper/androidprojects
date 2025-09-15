package com.example.componentpanel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.componentpanel.model.basemodel.MenuListGroupData

// 别名，只在当前.kt文件内生效
typealias MenuGroups = List<MenuListGroupData>

class FragmentViewModel : ViewModel() {
    private val stack = ArrayDeque<MenuGroups>()
    private val _curGroups = MutableLiveData<MenuGroups>()
    val curGroups: LiveData<MenuGroups> = _curGroups

    fun nextLevelGroups(nextGroups: MenuGroups) {
        // 加了个为空抛异常(是!!做的事情)
        // 但是是一个不好的行为，因为必然会崩溃
        // ps:这里的设计是有问题的
        stack.addLast(_curGroups.value!!)
        _curGroups.value = nextGroups
    }
    fun lastLevelGroups(): Boolean {
        return when (stack.size) {
            0 -> false
            else -> {
                _curGroups.value = stack.removeLast()
                true
            }
        }
    }
}