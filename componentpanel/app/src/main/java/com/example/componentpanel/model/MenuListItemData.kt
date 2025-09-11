package com.example.componentpanel.model

data class MenuListItemData(
    // val id: String,
    val startIconResId: Int? = null,
    val text: String? = null,
    val subText: String? = null,
    val endIconResId: Int? = null,
    val onViewClickListener: (() -> Unit)? = null,
    var nextGroups: List<MenuListGroupData>? = null,
)
