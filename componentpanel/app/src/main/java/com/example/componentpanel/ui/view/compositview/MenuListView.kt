package com.example.componentpanel.ui.view.compositview

import android.content.Context
import android.util.AttributeSet
import com.example.componentpanel.R
import com.example.componentpanel.model.basemodel.MenuListGroupData

// 子项是menu list的一个组(GroupView)
class MenuListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractCompositeView<MenuListGroupData, MenuListActionGroupView>(context, attrs) {

    override fun createItemView(data: MenuListGroupData): MenuListActionGroupView {
        val menuListActionGroupView = MenuListActionGroupView(context).apply {
            data.group?.run {
                setItems(this)
            }
        }
        return menuListActionGroupView
    }

    override fun getLayoutResId(): Int = R.layout.sv_ll_menu_list

    override fun getLinearLayoutResId(): Int = R.id.linearLayout_menu_list

    override fun initLinearLayout() { }
}