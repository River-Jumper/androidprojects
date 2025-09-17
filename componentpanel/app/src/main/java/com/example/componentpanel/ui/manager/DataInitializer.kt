package com.example.componentpanel.ui.manager

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.componentpanel.R
import com.example.componentpanel.model.observablemodel.ObservableMenuListGroupsData
import com.example.componentpanel.model.observablemodel.ObservableToolGroupData
import com.example.componentpanel.viewmodel.MenuViewModel
import com.example.componentpanel.viewmodel.TitleViewModel
import com.example.componentpanel.viewmodel.ToolViewModel
import com.example.componentpanel.viewmodel.data.TestDataGenerator

class DataInitializer(
    private val activity: AppCompatActivity,
    private val titleViewModel: TitleViewModel,
    private val toolViewModel: ToolViewModel,
    private val menuViewModel: MenuViewModel
) {

    var createFragment: ((String, ObservableMenuListGroupsData) -> Unit)? = null
    var hideBottomSheet: (() -> Unit)? = null

    init {
        menuViewModel.initRoot(TestDataGenerator.generateMenuListData())
        toolViewModel.initRoot(TestDataGenerator.generateToolBarData())
        titleViewModel.initRoot(TestDataGenerator.generateTitleData())

        // 注意，这里有个延迟执行的机制
        // 点击时才被执行，此时hideBottomSheet已经不为null了
        // 直接赋值会是一个null值
        titleViewModel.root?.setOnEndIconClickedListener {
            hideBottomSheet?.invoke()
        }
        toolViewModel.root?.run {
            setupToolBar(this)
        }
        testToolBar()
        menuViewModel.root?.run {
            setupMenuList(this)
        }
    }

    private fun testToolBar() {
        toolViewModel.root?.group?.value?.forEach { item ->
            when(item.text.value) {
                "测试1" -> {
                    item.setOnViewClickListener {
                        when(item.iconResId.value) {
                            R.drawable.ic_star_yellow -> {
                                item.setIconResId(R.drawable.ic_star_grey)
                            }
                            R.drawable.ic_star_grey -> {
                                item.setIconResId(R.drawable.ic_star_yellow)
                            }
                            else -> {
                                item.setIconResId(R.drawable.ic_star_yellow)
                            }
                        }
                    }
                }
                "测试2" -> {
                    // 尝试打乱了toolBar的顺序
                    item.setOnViewClickListener {
                        toolViewModel.root?.getGroup()?.run {
                            this.shuffle()
                            toolViewModel.root?.setGroup(
                                this
                            )
                        }
                    }
                }
                "测试3" -> {
                    // 尝试打乱了menuList的顺序
                    item.setOnViewClickListener {
                        when(item.iconResId.value) {
                            null -> {
                                item.setIconResId(R.drawable.ic_fun)
                            }
                            R.drawable.ic_fun -> {
                                menuViewModel.root?.getGroups()?.let { groups ->
                                    groups.forEach { group ->
                                        group.getGroup().run {
                                            this.shuffle()
                                            group.setGroup(this)
                                        }
                                    }
                                    groups.shuffle()
                                    menuViewModel.root?.setGroups(groups)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupToolBar(group: ObservableToolGroupData) {
        group.group.value?.forEach { item ->
            item.setOnViewClickListener {
                Toast.makeText(activity, "我是${item.text.value}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupMenuList(groups: ObservableMenuListGroupsData) {
        setupMenuList(groups, mutableSetOf())
    }
    private fun setupMenuList(groups: ObservableMenuListGroupsData, visited: MutableSet<ObservableMenuListGroupsData>) {
        // 如果已经访问过这个节点，直接返回，防止成环
        if (visited.contains(groups)) {
            return
        }
        visited.add(groups)

        groups.groups.value?.forEach { group ->
            group.group.value?.forEach { item ->
                // 能够添加监听的条件：item.nextGroups不为空
                when(val nextGroups = item.nextGroups.value) {
                    null -> {
                        item.setOnViewClickListener {
                            Toast.makeText(activity, "我是${item.text.value}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else -> {
                        item.setOnViewClickListener {
                            createFragment?.invoke(item.text.value ?: "", nextGroups)
                        }
                        // 进入下一次递归
                        setupMenuList(nextGroups, visited)
                    }
                }
            }
        }
    }

}

