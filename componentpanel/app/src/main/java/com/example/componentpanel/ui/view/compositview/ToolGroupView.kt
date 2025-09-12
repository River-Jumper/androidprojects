package com.example.componentpanel.ui.view.compositview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.componentpanel.R
import com.example.componentpanel.model.basemodel.ToolItemData
import com.example.componentpanel.model.observablemodel.ObservableToolGroupData
import com.example.componentpanel.ui.view.Bindable
import com.example.componentpanel.ui.view.baseview.ToolBarItemView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.componentpanel.model.observablemodel.ObservableToolItemData


class ToolGroupView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractCompositeView<ObservableToolItemData, ToolBarItemView>(context, attrs), Bindable<ObservableToolGroupData> {

    // 创建每个子视图的时候就做好了子视图的数据绑定工作
    override fun createItemView(data: ObservableToolItemData): ToolBarItemView {
        val toolBarItemView = ToolBarItemView(context)
        toolBarItemView.post {
            toolBarItemView.bind(data)
        }
        return toolBarItemView
    }

    override fun getLayoutResId(): Int = R.layout.sv_ll_tool_bar

    override fun getLinearLayoutResId(): Int = R.id.linearLayout_tool_bar

    override fun initLinearLayout() {
        linearLayout.orientation = LinearLayout.HORIZONTAL
    }

    private var boundData: ObservableToolGroupData? = null
    private var lifecycleOwner: LifecycleOwner? = null
    private var pendingData: ObservableToolGroupData? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lifecycleOwner = findViewTreeLifecycleOwner()
        // 如果有待绑定的数据，现在进行绑定
        pendingData?.let { data ->
            pendingData = null
            bind(data)
        }
    }

    override fun onDetachedFromWindow() {
        unbind()
        super.onDetachedFromWindow()
    }

    override fun bind(data: ObservableToolGroupData) {
        unbind()
        this.boundData = data
        
        // 如果 lifecycleOwner 还没有准备好，先保存数据，等 onAttachedToWindow 时再绑定
        val owner = lifecycleOwner
        if (owner == null) {
            pendingData = data
            return
        }
        
        data.group.observe(owner) { group ->
            setItems(group)
        }
    }

    override fun unbind() {
        if (boundData != null && lifecycleOwner != null) {
            boundData?.group?.removeObservers(lifecycleOwner!!)
        }
        boundData = null
    }
}