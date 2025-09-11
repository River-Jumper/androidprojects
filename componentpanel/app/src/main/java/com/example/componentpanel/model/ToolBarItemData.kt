package com.example.componentpanel.model

data class ToolBarItemData(
    // val id: String,
    var iconResId: Int? = null,
    var text: String? = null,
    var onViewClickListener: (() -> Unit)? = null,
)