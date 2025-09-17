package com.example.componentpanel.model.basemodel

data class ToolItemData(
    var iconResId: Int? = null,
    var text: String? = null,
    var onViewClickListener: (() -> Unit)? = null,
)