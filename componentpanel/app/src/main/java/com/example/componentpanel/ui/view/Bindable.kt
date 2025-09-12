package com.example.componentpanel.ui.view

interface Bindable<T> {
    fun bind(data: T)
    fun unbind()
}