package com.example.componentpanel.viewmodel

import androidx.lifecycle.ViewModel
import com.example.componentpanel.model.basemodel.ToolGroupData
import com.example.componentpanel.model.observablemodel.ObservableToolGroupData

class ToolViewModel : NavigatorViewModel<ObservableToolGroupData?>() {
    fun setRoot(root: ToolGroupData) {
        super.setRoot(ObservableToolGroupData(root))
    }
}