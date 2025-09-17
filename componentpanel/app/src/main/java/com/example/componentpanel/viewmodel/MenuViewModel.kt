package com.example.componentpanel.viewmodel

import com.example.componentpanel.model.basemodel.MenuListGroupsData
import com.example.componentpanel.model.observablemodel.ObservableMenuListGroupsData

class MenuViewModel : NavigatorViewModel<ObservableMenuListGroupsData>() {
    fun initRoot(root: MenuListGroupsData) {
        super.setStackRoot(ObservableMenuListGroupsData(root))
    }
}