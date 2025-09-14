package com.example.componentpanel.ui.view.compositview

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.componentpanel.R
import com.example.componentpanel.model.basemodel.MenuListItemData
import com.example.componentpanel.model.observablemodel.ObservableMenuListGroupData
import com.example.componentpanel.model.observablemodel.ObservableMenuListItemData
import com.example.componentpanel.ui.view.Bindable
import com.example.componentpanel.ui.view.baseview.MenuListItemView

// 子项是每一个menu list的元素(MenuListActionView)
class MenuListGroupView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractCompositeView<ObservableMenuListItemData, MenuListItemView>(context, attrs)
, Bindable<ObservableMenuListGroupData> {

    override fun createItemView(data: ObservableMenuListItemData): MenuListItemView {
        val menuListItemView = MenuListItemView(context)
        menuListItemView.post {
            menuListItemView.bind(data)
        }
        return menuListItemView
    }

    override fun getLayoutResId(): Int = R.layout.ll_item_menu_list_action_group

    override fun getLinearLayoutResId(): Int = R.id.linearLayout_menu_list_action_group

    override fun initLinearLayout() {}

    private var boundData: ObservableMenuListGroupData? = null
    private var lifecycleOwner: LifecycleOwner? = null
    private var pendingData: ObservableMenuListGroupData? = null

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
    override fun bind(data: ObservableMenuListGroupData) {
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