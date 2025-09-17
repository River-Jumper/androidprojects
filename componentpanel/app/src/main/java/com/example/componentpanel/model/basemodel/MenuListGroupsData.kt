package com.example.componentpanel.model.basemodel

data class MenuListGroupsData(
    val groups: MutableList<MenuListGroupData>? = null
) {
    companion object {
        fun fromItemsList(itemsList: MutableList<MutableList<MenuListItemData>>): MenuListGroupsData {
            return MenuListGroupsData(
                groups = itemsList.map { items ->
                    MenuListGroupData(group = items)
                }.toMutableList()
            )
        }
    }
}