package com.example.componentpanel.viewmodel

import androidx.lifecycle.ViewModel

abstract class NavigatorViewModel<T> : ViewModel() {
    private val stack = ArrayDeque<T>()
    var root: T? = null
    var curItem: T? = null

    protected fun setStackRoot(root: T) {
        stack.clear()
        stack.addLast(root)
        this.root = root
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