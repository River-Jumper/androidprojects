package com.example.componentpanel.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.componentpanel.R
import com.example.componentpanel.model.MenuListItemData
import com.example.componentpanel.model.MenuListGroupData
import com.example.componentpanel.model.ToolBarItemData
import com.example.componentpanel.ui.view.compositview.MenuListView
import com.example.componentpanel.ui.view.baseview.TitleBarView
import com.example.componentpanel.ui.view.compositview.ToolBarView
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
    private lateinit var titleBarView: TitleBarView
    private lateinit var titleBarViewModel: TitleBarViewModel

    private lateinit var toolBarView: ToolBarView
    private lateinit var menuListView: MenuListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initTitleViewModel()
        initToolBarView()
        initMenuListView()
    }

    private fun initToolBarView() {
        toolBarView = findViewById(R.id.item_tool_bar)
        toolBarView.setItems(generateToolBarData())
    }

    private fun initMenuListView() {
        menuListView = findViewById(R.id.item_menu_list)
        menuListView.setItems(generateMenuListData())
    }


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
    private fun generateToolBarData(): MutableList<ToolBarItemData> {
        val dataList = mutableListOf(
            ToolBarItemData(R.drawable.ic_save_as, "另存"),
            ToolBarItemData(R.drawable.ic_share, "分享"),
            ToolBarItemData(R.drawable.ic_print, "打印"),
            ToolBarItemData(R.drawable.ic_paper_tools, "论文工具"),
            ToolBarItemData(R.drawable.ic_find, "查找"),
            ToolBarItemData(null, "乐子1"),
            ToolBarItemData(null, "乐子2"),
            ToolBarItemData(null, "乐子3"),
        )
        return dataList
    }

    // 调试用途
    private fun initTitleViewModel() {
        titleBarView = findViewById(R.id.view_bottomSheet_title)
        titleBarViewModel = TitleBarViewModel()

        titleBarView.setImageClickListener(TitleBarView.Companion.STAR) {
            titleBarViewModel.toggleStarState()
        }

        titleBarViewModel.starState.observe(this) { starState ->
            if (starState) {
                titleBarView.setImage(TitleBarView.Companion.STAR, R.drawable.ic_star_yellow)
            }
            else {
                titleBarView.setImage(TitleBarView.Companion.STAR, R.drawable.ic_star_grey)
            }
        }

        titleBarViewModel.toastMessage.observe(this) { toastMessage ->
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
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