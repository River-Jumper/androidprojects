package com.example.componentpanel.ui.view.compositview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.componentpanel.R
import com.example.componentpanel.model.ToolBarItemData
import com.example.componentpanel.ui.view.baseview.ToolBarActionView


class ToolBarView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractCompositeView<ToolBarItemData, ToolBarActionView>(context, attrs) {

    override fun createItemView(data: ToolBarItemData): ToolBarActionView {
        val toolBarActionView = ToolBarActionView(context).apply {
            data.iconResId?.run {
                setImage(ToolBarActionView.ICON, this)
            }
            data.text?.run {
                setText(ToolBarActionView.TEXT, this)
            }
            data.onViewClickListener?.run {
                setViewClickListener(this)
            }
        }
        return toolBarActionView
    }

    override fun getLayoutResId(): Int = R.layout.sv_ll_tool_bar

    override fun getLinearLayoutResId(): Int = R.id.linearLayout_tool_bar

    override fun initLinearLayout() {
        linearLayout.orientation = LinearLayout.HORIZONTAL
    }
}