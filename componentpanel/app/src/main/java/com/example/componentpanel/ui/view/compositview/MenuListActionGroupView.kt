package com.example.componentpanel.ui.view.compositview

import android.content.Context
import android.util.AttributeSet
import com.example.componentpanel.R
import com.example.componentpanel.model.MenuListItemData
import com.example.componentpanel.ui.view.baseview.MenuListActionView

// 子项是每一个menu list的元素(MenuListActionView)
class MenuListActionGroupView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractCompositeView<MenuListItemData, MenuListActionView>(context, attrs) {

    override fun createItemView(data: MenuListItemData): MenuListActionView {
        val menuListActionView = MenuListActionView(context).apply {
            data.startIconResId?.run {
                setImage(MenuListActionView.START_ICON, this)
            }
            data.text?.run {
                setText(MenuListActionView.TEXT, this)
            }
            data.subText?.run {
                setText(MenuListActionView.SUB_TEXT, this)
            }
            data.endIconResId?.run {
                setImage(MenuListActionView.END_ICON, this)
            }
            data.onViewClickListener?.run {
                setViewClickListener(this)
            }
        }
        return menuListActionView
    }

    override fun getLayoutResId(): Int = R.layout.ll_item_menu_list_action_group

    override fun getLinearLayoutResId(): Int = R.id.linearLayout_menu_list_action_group

    override fun initLinearLayout() {}
}