package com.example.componentpanel.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.componentpanel.R
import com.example.componentpanel.ui.adapter.BarItem
import com.example.componentpanel.ui.adapter.GridAdapter
import com.example.componentpanel.ui.adapter.GridData
import com.example.componentpanel.ui.adapter.Group
import com.example.componentpanel.ui.adapter.GroupAdapter
import com.example.componentpanel.ui.view.BottomSheetTitleView
import com.example.componentpanel.viewmodel.BottomSheetTitleViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    companion object {
        private const val EXPANDED_OFFSET_RATIO = 0.1f
        private const val ALPHA_CALCULATION_DIVISOR = 2f
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var bottomSheetTopArea: View
    private lateinit var bottomSheetTitleView: BottomSheetTitleView
    private lateinit var bottomSheetTitleViewModel: BottomSheetTitleViewModel
    private lateinit var gridRecyclerView: RecyclerView
    private lateinit var groupsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initTitleViewModel()
        initGridRecyclerView()
        initGroupRecyclerView()
    }



    // 调试用途
    private fun initGroupRecyclerView() {
        groupsRecyclerView = findViewById(R.id.recyclerView_barGroups)
        groupsRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        
        // 设置RecyclerView的滑动处理
        groupsRecyclerView.isNestedScrollingEnabled = true
        groupsRecyclerView.setHasFixedSize(false)

        // 各个组内项
        val barItemsList1 = mutableListOf(
            BarItem(R.drawable.ic_rename, "重命名", null, null),
            BarItem(R.drawable.ic_move_copy, "移动或复制", null, null),
            BarItem(R.drawable.ic_send_to, "发送到", "电脑、手机等其他设备", null),
            BarItem(R.drawable.ic_add_to, "添加到", null, R.drawable.ic_drop_down),
        )
        val barItemList2 = mutableListOf(
            BarItem(R.drawable.ic_export_pdf, "输出为pdf", null, null),
            BarItem(R.drawable.ic_export_image, "输出为图片", null, null),
            BarItem(R.drawable.ic_more_export, "更多输出转换", "合并、瘦身", R.drawable.ic_drop_down),
            BarItem(R.drawable.ic_ai_convert, "AI生成转换", "PPT、截图", R.drawable.ic_drop_down),
        )
        val barItemList3 = mutableListOf(
            BarItem(R.drawable.ic_tts, "语音朗读", null, null),
            BarItem(R.drawable.ic_all_services, "全部服务", null, R.drawable.ic_drop_down),
        )
        val barItemList4 = mutableListOf(
            BarItem(R.drawable.ic_backup_restore, "备份与恢复", null, R.drawable.ic_drop_down),
            BarItem(R.drawable.ic_lock_auth, "加密与认证", null, R.drawable.ic_drop_down),
            BarItem(R.drawable.ic_open_location, "打开文件位置", null, null),
            BarItem(R.drawable.ic_doc_info, "文档信息", null, null),
        )
        val barItemList5 = mutableListOf(
            BarItem(R.drawable.ic_help_feedback, "帮助与反馈", null, null),
        )

        // 总体的组
        val groups = mutableListOf(
            Group(barItemsList1),
            Group(barItemList2),
            Group(barItemList3),
            Group(barItemList4),
            Group(barItemList5),
        )

        groupsRecyclerView.adapter = GroupAdapter(groups)
    }
    // 调试用途
    private fun initGridRecyclerView() {
        gridRecyclerView = findViewById(R.id.recyclerView_grid)
        gridRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val dataList = mutableListOf(
            GridData(R.drawable.ic_save_as, "另存"),
            GridData(R.drawable.ic_share, "分享"),
            GridData(R.drawable.ic_print, "打印"),
            GridData(R.drawable.ic_paper_tools, "论文工具"),
            GridData(R.drawable.ic_find, "查找"),
            GridData(null, "乐子1"),
            GridData(null, "乐子2"),
            GridData(null, "乐子3"),
        )
        val adapter = GridAdapter(dataList)
        adapter.getItem(5)?.onTextClickListener = {
            adapter.addItem(
                newItem =  GridData(R.drawable.ic_star_yellow
                    , "新增"),
            )
        }
        adapter.getItem(6)?.onTextClickListener = {
            adapter.getItem(0)?.iconResId = R.drawable.ic_star_grey
        }
        adapter.getItem(7)?.onTextClickListener = {
            adapter.getItem(0)?.iconResId = R.drawable.ic_drop_down
        }
        gridRecyclerView.adapter = adapter
    }

    // 调试用途
    private fun initTitleViewModel() {
        bottomSheetTitleView = findViewById(R.id.view_bottomSheet_title)
        bottomSheetTitleViewModel = BottomSheetTitleViewModel()

        bottomSheetTitleView.setImageClickListener(BottomSheetTitleView.Companion.STAR) {
            bottomSheetTitleViewModel.toggleStarState()
        }

        bottomSheetTitleViewModel.starState.observe(this) { starState ->
            if (starState) {
                bottomSheetTitleView.setImage(BottomSheetTitleView.Companion.STAR, R.drawable.ic_star_yellow)
            }
            else {
                bottomSheetTitleView.setImage(BottomSheetTitleView.Companion.STAR, R.drawable.ic_star_grey)
            }
        }

        bottomSheetTitleViewModel.toastMessage.observe(this) { toastMessage ->
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
                // 改变透明度：使用平方根函数创建平滑的透明度过渡效果
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