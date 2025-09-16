package com.example.componentpanel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class NavigatorViewModel<T> : ViewModel() {
    protected val stack = ArrayDeque<T>()
    var curItem: T? = null

    open fun setRoot(root: T) {
        stack.clear()
        stack.addLast(root)
        curItem = root
    }

    fun goForward(nextItem: T) {
        stack.addLast(nextItem)
        curItem = nextItem
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
                curItem = stack.last()
                true
            }
        }
    }
}