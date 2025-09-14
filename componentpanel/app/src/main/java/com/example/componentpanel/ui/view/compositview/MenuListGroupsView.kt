package com.example.componentpanel.ui.view.compositview

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.componentpanel.R
import com.example.componentpanel.model.basemodel.MenuListGroupData
import com.example.componentpanel.model.observablemodel.ObservableMenuListGroupData
import com.example.componentpanel.model.observablemodel.ObservableMenuListGroupsData
import com.example.componentpanel.ui.view.Bindable

// 子项是menu list的一个组(GroupView)
class MenuListGroupsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractCompositeView<ObservableMenuListGroupData, MenuListGroupView>(context, attrs)
, Bindable<ObservableMenuListGroupsData>{

    override fun createItemView(data: ObservableMenuListGroupData): MenuListGroupView {
        val menuListGroupView = MenuListGroupView(context)
        menuListGroupView.post {
            menuListGroupView.bind(data)
        }
        return menuListGroupView
    }

    override fun getLayoutResId(): Int = R.layout.sv_ll_menu_list

    override fun getLinearLayoutResId(): Int = R.id.linearLayout_menu_list

    override fun initLinearLayout() { }

    private var boundData: ObservableMenuListGroupsData? = null
    private var lifecycleOwner: LifecycleOwner? = null
    private var pendingData: ObservableMenuListGroupsData? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lifecycleOwner = findViewTreeLifecycleOwner()
        pendingData?.let { data ->
            pendingData = null
            bind(data)
        }
    }

    override fun onDetachedFromWindow() {
        unbind()
        super.onDetachedFromWindow()
    }

    override fun bind(data: ObservableMenuListGroupsData) {
        unbind()
        this.boundData = data

        val owner = lifecycleOwner
        if (owner == null) {
            pendingData = data
            return
        }

        data.groups.observe(owner) { groups ->
            setItems(groups)
        }
    }

    override fun unbind() {
        if (boundData != null && lifecycleOwner != null) {
            boundData?.groups?.removeObservers(lifecycleOwner!!)
        }
        boundData = null
    }
}