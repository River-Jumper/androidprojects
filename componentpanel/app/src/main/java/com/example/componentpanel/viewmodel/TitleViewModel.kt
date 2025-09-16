package com.example.componentpanel.viewmodel

import com.example.componentpanel.model.basemodel.TitleData
import com.example.componentpanel.model.observablemodel.ObservableTitleData

class TitleViewModel : NavigatorViewModel<ObservableTitleData>() {
    fun setRoot(root: TitleData) {
        super.setRoot(ObservableTitleData(root))
    }
    // 包装了一层
    fun setStartIcon(resId: Int) {
        curItem?.setStartIconResId(resId)
    }
    fun setMainTitle(title: String) {
        curItem?.setMainTitle(title)
    }
    fun setSubTitle(subTitle: String) {
        curItem?.setSubTitle(subTitle)
    }
    fun setEndIcon(resId: Int) {
        curItem?.setEndIconResId(resId)
    }
}