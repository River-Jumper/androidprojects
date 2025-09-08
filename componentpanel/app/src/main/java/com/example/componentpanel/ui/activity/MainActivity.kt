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
import com.example.componentpanel.ui.adapter.GridAdapter
import com.example.componentpanel.ui.adapter.GridData
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initTitleViewModel()
        initGridRecyclerView()
    }

    // 功能调试用途
    private fun initGridRecyclerView() {
        gridRecyclerView = findViewById(R.id.recyclerView_grid)
        gridRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val dataList = mutableListOf(
            GridData("reload", R.drawable.ic_save_as, "另存"),
            GridData("share", R.drawable.ic_share, "分享"),
            GridData("print", R.drawable.ic_print, "打印"),
            GridData("paperTools", R.drawable.ic_paper_tools, "论文工具"),
            GridData("find", R.drawable.ic_find, "查找"),
        )
        val adapter = GridAdapter(dataList)
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