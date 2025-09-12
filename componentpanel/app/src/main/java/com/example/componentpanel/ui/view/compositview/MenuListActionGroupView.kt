package com.example.componentpanel.ui.view.compositview

import android.content.Context
import android.util.AttributeSet
import com.example.componentpanel.R
import com.example.componentpanel.model.basemodel.MenuListItemData
import com.example.componentpanel.ui.view.baseview.MenuListItemView

// 子项是每一个menu list的元素(MenuListActionView)
class MenuListActionGroupView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractCompositeView<MenuListItemData, MenuListItemView>(context, attrs) {

    override fun createItemView(data: MenuListItemData): MenuListItemView {
        val menuListItemView = MenuListItemView(context).apply {
            data.startIconResId?.run {
                setImage(MenuListItemView.START_ICON, this)
            }
            data.text?.run {
                setText(MenuListItemView.TEXT, this)
            }
            data.subText?.run {
                setText(MenuListItemView.SUB_TEXT, this)
            }
            data.endIconResId?.run {
                setImage(MenuListItemView.END_ICON, this)
            }
            data.onViewClickListener?.run {
                setViewClickListener(this)
            }
        }
        return menuListItemView
    }

    override fun getLayoutResId(): Int = R.layout.ll_item_menu_list_action_group

    override fun getLinearLayoutResId(): Int = R.id.linearLayout_menu_list_action_group

    override fun initLinearLayout() {}
}