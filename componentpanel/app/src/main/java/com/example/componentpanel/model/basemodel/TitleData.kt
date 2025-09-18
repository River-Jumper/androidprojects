package com.example.componentpanel.model.basemodel

data class TitleData (
    val startIconResId: Int? = null,
    val mainTitle: String? = null,
    val subTitle: String? = null,
    val endIconResId: Int? = null,
    val midTitle: String? = null,
    val onStartIconClickedListener: (() -> Unit)? = null,
    val onEndIconClickedListener: (() -> Unit)? = null,
)