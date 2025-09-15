package com.example.componentpanel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.componentpanel.model.basemodel.MenuListGroupsData
import com.example.componentpanel.model.observablemodel.ObservableMenuListGroupsData

class MainViewModel : ViewModel() {
    private val stack = ArrayDeque<ObservableMenuListGroupsData>()// 约定：维持当前页面的Groups一定等同于栈顶元素
    var curGroups: ObservableMenuListGroupsData? = null

    fun setRootGroups(rootGroups: ObservableMenuListGroupsData) {
        stack.clear()
        stack.addLast(rootGroups)
        curGroups = rootGroups
    }

    fun goForward(nextGroups: ObservableMenuListGroupsData) {
        stack.addLast(nextGroups)
        curGroups = nextGroups
    }

    fun canGoBack(): Boolean {
        return stack.size > 1
    }
    fun goBack(): Boolean {
        return when (stack.size) {
            0 -> false
            1 -> false
            else -> {
                stack.removeLast()
                curGroups = stack.last()
                true
            }
        }
    }
}