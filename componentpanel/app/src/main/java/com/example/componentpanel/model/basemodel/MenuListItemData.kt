package com.example.componentpanel.model.basemodel

data class MenuListItemData(
    val startIconResId: Int? = null,
    val text: String? = null,
    val subText: String? = null,
    val endIconResId: Int? = null,
    val nextGroups: MenuListGroupsData? = null,
    val onViewClickListener: (() -> Unit)? = null,
)
