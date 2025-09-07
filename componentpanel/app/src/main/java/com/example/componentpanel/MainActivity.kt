package com.example.componentpanel

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.componentpanel.ui.BottomSheetTitleView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initTitleViewModel()
    }

    private fun initTitleViewModel() {
        bottomSheetTitleView = findViewById(R.id.view_bottomSheet_title)
        bottomSheetTitleViewModel = BottomSheetTitleViewModel()

        bottomSheetTitleView.setImageClickListener(BottomSheetTitleView.STAR) {
            bottomSheetTitleViewModel.toggleStarState()
        }

        bottomSheetTitleViewModel.starState.observe(this) { starState ->
            if (starState) {
                bottomSheetTitleView.setImage(BottomSheetTitleView.STAR, R.drawable.ic_star_yellow)
            }
            else {
                bottomSheetTitleView.setImage(BottomSheetTitleView.STAR, R.drawable.ic_star_grey)
            }
        }

        bottomSheetTitleViewModel.toastMessage.observe(this) { toastMessage ->
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

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