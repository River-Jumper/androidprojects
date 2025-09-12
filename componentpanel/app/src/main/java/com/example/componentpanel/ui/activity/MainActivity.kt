package com.example.componentpanel.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.componentpanel.R
import com.example.componentpanel.model.basemodel.MenuListItemData
import com.example.componentpanel.model.basemodel.MenuListGroupData
import com.example.componentpanel.model.basemodel.TitleData
import com.example.componentpanel.model.basemodel.ToolGroupData
import com.example.componentpanel.model.basemodel.ToolItemData
import com.example.componentpanel.model.observablemodel.ObservableTitleData
import com.example.componentpanel.model.observablemodel.ObservableToolGroupData
import com.example.componentpanel.model.observablemodel.ObservableToolItemData
import com.example.componentpanel.ui.view.compositview.MenuListView
import com.example.componentpanel.ui.view.baseview.TitleView
import com.example.componentpanel.ui.view.compositview.ToolGroupView
import com.example.componentpanel.viewmodel.TitleBarViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    companion object {
        private const val EXPANDED_OFFSET_RATIO = 0f
        private const val ALPHA_CALCULATION_DIVISOR = 2f
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var bottomSheetTopArea: View
    private lateinit var titleView: TitleView
    private lateinit var titleBarViewModel: TitleBarViewModel

    private lateinit var toolGroupView: ToolGroupView
    private lateinit var menuListView: MenuListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initTitleView()
        initToolBarView()
        // initMenuListView()
    }

    // 调试用途：动态添加子项，动态更改点击事件
    private fun initToolBarView() {
        toolGroupView = findViewById(R.id.test_tool)
        val toolGroupData = ToolGroupData(generateToolBarData())
        val observableToolGroupData = ObservableToolGroupData(toolGroupData)

        observableToolGroupData.group.value?.forEach { itemData ->
            when (itemData.text.value) {
                "乐子1" -> {
                    itemData.setOnViewClickListener{
                        // 获取当前列表的一个可变副本，用可变的副本来操作，无需建立一个全新的
                        // 话说这还不如直接把_group变为公有成员呢
                        val currentList = observableToolGroupData.group.value?.toMutableList() ?: mutableListOf()
                        currentList.add(ObservableToolItemData(ToolItemData(R.drawable.ic_star_yellow, "星星")))
                        observableToolGroupData.setGroup(currentList)
                    }
                }
                "乐子2" -> {
                    itemData.setOnViewClickListener{
                        // 把乐子1的点击事件给修改了
                        // 顺便给它改了个图标
                        observableToolGroupData.group.value?.forEach { itemData ->
                            if (itemData.text.value == "乐子1") {
                                itemData.setIconResId(R.drawable.ic_star_grey)
                                itemData.setOnViewClickListener{
                                    val currentList = observableToolGroupData.group.value?.toMutableList() ?: mutableListOf()
                                    currentList.add(ObservableToolItemData(ToolItemData(R.drawable.ic_star_grey, "灰色星星")))
                                    observableToolGroupData.setGroup(currentList)
                                }
                            }
                        }
                    }
                }
                "乐子3" -> {
                    itemData.setOnViewClickListener{
                        // 把乐子1的点击事件给修改了
                        // 顺便给它改了个图标
                        observableToolGroupData.group.value?.forEach { itemData ->
                            if (itemData.text.value == "乐子1") {
                                itemData.setIconResId(R.drawable.ic_star_yellow)
                                itemData.setOnViewClickListener{
                                    val currentList = observableToolGroupData.group.value?.toMutableList() ?: mutableListOf()
                                    currentList.add(ObservableToolItemData(ToolItemData(R.drawable.ic_star_yellow, "黄色星星")))
                                    observableToolGroupData.setGroup(currentList)
                                }
                            }
                        }
                    }
                }
                else -> {
                    // 处理其他情况
                }
            }
        }

        toolGroupView.post {
            toolGroupView.bind(observableToolGroupData)
        }
    }

    /*
    private fun initMenuListView() {
        menuListView = findViewById(R.id.item_menu_list)
        menuListView.setItems(generateMenuListData())
    }*/


    // 调试用途：生成menu list测试数据
    private fun generateMenuListData(): MutableList<MenuListGroupData> {
        // 各个组内项
        val barItemsList1 = mutableListOf(
            MenuListItemData(R.drawable.ic_rename, "重命名", null, null),
            MenuListItemData(R.drawable.ic_move_copy, "移动或复制", null, null),
            MenuListItemData(R.drawable.ic_send_to, "发送到", "电脑、手机等其他设备", null),
            MenuListItemData(R.drawable.ic_add_to, "添加到", null, R.drawable.ic_drop_down),
        )
        val barItemList2 = mutableListOf(
            MenuListItemData(R.drawable.ic_export_pdf, "输出为pdf", null, null),
            MenuListItemData(R.drawable.ic_export_image, "输出为图片", null, null),
            MenuListItemData(R.drawable.ic_more_export, "更多输出转换", "合并、瘦身", R.drawable.ic_drop_down),
            MenuListItemData(R.drawable.ic_ai_convert, "AI生成转换", "PPT、截图", R.drawable.ic_drop_down),
        )
        val barItemList3 = mutableListOf(
            MenuListItemData(R.drawable.ic_tts, "语音朗读", null, null),
            MenuListItemData(R.drawable.ic_all_services, "全部服务", null, R.drawable.ic_drop_down),
        )
        val barItemList4 = mutableListOf(
            MenuListItemData(R.drawable.ic_backup_restore, "备份与恢复", null, R.drawable.ic_drop_down),
            MenuListItemData(R.drawable.ic_lock_auth, "加密与认证", null, R.drawable.ic_drop_down),
            MenuListItemData(R.drawable.ic_open_location, "打开文件位置", null, null),
            MenuListItemData(R.drawable.ic_doc_info, "文档信息", null, null),
        )
        val barItemList5 = mutableListOf(
            MenuListItemData(R.drawable.ic_help_feedback, "帮助与反馈", null, null),
        )
        val barItemList6 = mutableListOf(
            MenuListItemData(R.drawable.ic_backup_restore, "备份与恢复", null, R.drawable.ic_drop_down),
            MenuListItemData(R.drawable.ic_lock_auth, "加密与认证", null, R.drawable.ic_drop_down),
            MenuListItemData(R.drawable.ic_open_location, "打开文件位置", null, null),
            MenuListItemData(R.drawable.ic_doc_info, "文档信息", null, null),
        )

        // 总体的组
        val groups = mutableListOf(
            MenuListGroupData(barItemsList1),
            MenuListGroupData(barItemList2),
            MenuListGroupData(barItemList3),
            MenuListGroupData(barItemList4),
            MenuListGroupData(barItemList5),
            MenuListGroupData(barItemList6),
        )

        return groups
    }
    // 调试用途：生成tool bar测试数据
    private fun generateToolBarData(): MutableList<ToolItemData> {
        val dataList = mutableListOf(
            ToolItemData(R.drawable.ic_save_as, "另存"),
            ToolItemData(R.drawable.ic_share, "分享"),
            ToolItemData(R.drawable.ic_print, "打印"),
            ToolItemData(R.drawable.ic_paper_tools, "论文工具"),
            ToolItemData(R.drawable.ic_find, "查找"),
            ToolItemData(null, "乐子1"),
            ToolItemData(null, "乐子2"),
            ToolItemData(null, "乐子3"),
        )
        return dataList
    }

    // 调试用途
    private fun initTitleView() {
        titleView = findViewById(R.id.test_title)

        val titleData = TitleData(
            R.drawable.ic_title,
            "新笔记",
            "字数：0",
            R.drawable.ic_star_grey,
            null
        )
        val observableTitleData = ObservableTitleData(titleData)

        // 仅仅作为一个运行时切换的小例子：通过纯粹的操纵数据来改变视图
        observableTitleData.setOnEndIconClickedListener {
            when (observableTitleData.endIconResId.value) {
                R.drawable.ic_star_grey -> {
                    observableTitleData.setEndIconResId(R.drawable.ic_star_yellow)
                }
                R.drawable.ic_star_yellow -> {
                    observableTitleData.setEndIconResId(R.drawable.ic_star_grey)
                }
            }
        }
        // 延迟绑定，确保视图已经附加到窗口
        titleView.post {
            titleView.bind(observableTitleData)
        }
    }

    // 其实这里的透明度计算，和各种隐藏显示底部抽屉的逻辑是最想塞到viewModel的地方，但是目前不知道怎么塞
    private fun initView() {
        val bottomSheet: View = findViewById(R.id.bottomSheet)
        bottomSheetTopArea = findViewById(R.id.bottom_sheet_top_area)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.isFitToContents = false
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        
        // 设置滑动处理，让RecyclerView能够正常滑动
        bottomSheetBehavior.isDraggable = true

        bottomSheet.post {
            val parentHeight = (bottomSheet.parent as View).height
            bottomSheetBehavior.expandedOffset = (parentHeight * EXPANDED_OFFSET_RATIO).toInt()
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        bottomSheetTopArea.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bottomSheetTopArea.visibility = View.VISIBLE
                    }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // 改变透明度：使用平方根函数创建平滑的透明度过渡效果(怎么会想到用平方根呢？)
                val alpha = sqrt((slideOffset + 1) / ALPHA_CALCULATION_DIVISOR)
                bottomSheetTopArea.alpha = alpha
            }

        })

        findViewById<Button>(R.id.show).setOnClickListener {
            if (bottomSheetTopArea.isGone) {
                showBottomSheet()
            } else if (bottomSheetTopArea.isVisible) {
                hideBottomSheet()
            }
        }

        bottomSheetTopArea.setOnClickListener {
            hideBottomSheet()
        }
    }

    private fun showBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetTopArea.visibility = View.VISIBLE
    }
    private fun hideBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetTopArea.visibility = View.GONE
    }
}