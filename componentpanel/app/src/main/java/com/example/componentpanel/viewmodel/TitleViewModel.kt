package com.example.componentpanel.viewmodel

import com.example.componentpanel.model.basemodel.TitleData
import com.example.componentpanel.model.observablemodel.ObservableTitleData

class TitleViewModel : NavigatorViewModel<ObservableTitleData>() {
    fun initRoot(root: TitleData) {
        super.setStackRoot(ObservableTitleData(root))
    }
}