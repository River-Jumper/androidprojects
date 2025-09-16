package com.example.componentpanel.viewmodel

import com.example.componentpanel.model.basemodel.MenuListGroupsData
import com.example.componentpanel.model.observablemodel.ObservableMenuListGroupsData

class MenuViewModel : NavigatorViewModel<ObservableMenuListGroupsData>() {
    fun setRoot(root: MenuListGroupsData) {
        super.setRoot(ObservableMenuListGroupsData(root))
    }
}