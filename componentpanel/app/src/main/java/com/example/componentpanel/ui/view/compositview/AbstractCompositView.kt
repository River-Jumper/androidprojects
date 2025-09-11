package com.example.componentpanel.ui.view.compositview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.example.componentpanel.ui.view.baseview.AbstractBaseView

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

    private fun checkIndex(index: Int) {
        require(index in 0 until linearLayout.childCount) {
            "Invalid index = ${index}. Must be between 0 and ${linearLayout.childCount - 1}."
        }
    }
    fun setItems(items: List<ItemData>) {
        linearLayout.removeAllViews()
        items.forEach { item ->
            linearLayout.addView(createItemView(item))
        }
    }

    fun addItemAt(data: ItemData, index: Int? = null) {
        index?.run {
            // 这里可以让index为长度本身，因为插入是向index前插入的，想要插入到最后一个元素之后需要index = size
            require(index in 0 .. linearLayout.childCount) {
                "Invalid index = ${index}. Must be between 0 and ${linearLayout.childCount}."
            }
            linearLayout.addView(createItemView(data), index)
        } ?: linearLayout.addView(createItemView(data)) // ?: 仅仅是为了学习是否null的不同行为的语法糖，这里默认添加到队尾
    }

    fun removeItemAt(index: Int) {
        checkIndex(index)
        linearLayout.removeViewAt(index)
    }
    fun getItemAt(index: Int): ItemView? {
        checkIndex(index)
        // as?：如果不能安全转换，则返回null
        return linearLayout.getChildAt(index) as? ItemView
    }

}