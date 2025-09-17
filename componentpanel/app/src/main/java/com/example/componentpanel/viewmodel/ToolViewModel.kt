package com.example.componentpanel.viewmodel

import com.example.componentpanel.model.basemodel.ToolGroupData
import com.example.componentpanel.model.observablemodel.ObservableToolGroupData

class ToolViewModel : NavigatorViewModel<ObservableToolGroupData?>() {
    fun initRoot(root: ToolGroupData) {
        super.setStackRoot(ObservableToolGroupData(root))
    }
}