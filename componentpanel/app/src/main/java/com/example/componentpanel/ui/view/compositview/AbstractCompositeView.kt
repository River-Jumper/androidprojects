package com.example.componentpanel.ui.view.compositview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout

// 做 从数据（这里是专指可观测数据）到视图的映射
abstract class AbstractCompositeView<ItemData, ItemView> (
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) where ItemView : View{
    protected val linearLayout: LinearLayout
    protected abstract fun createItemView(data: ItemData): ItemView
    protected abstract fun getLayoutResId(): Int
    protected abstract fun getLinearLayoutResId(): Int
    protected abstract fun initLinearLayout()

    init {
        LayoutInflater.from(context).inflate(getLayoutResId(), this, true)
        linearLayout = findViewById(getLinearLayoutResId())
        initLinearLayout()
    }
    fun setItems(items: List<ItemData>) {
        linearLayout.removeAllViews()
        items.forEach { item ->
            linearLayout.addView(createItemView(item))
        }
    }
}