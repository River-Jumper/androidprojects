package com.example.componentpanel

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.core.view.isVisible
import androidx.core.view.isGone

class MainActivity : AppCompatActivity() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    fun initView() {
        val bottomSheet: View = findViewById(R.id.bottomSheet)
        val bottomSheetTopArea = findViewById<View>(R.id.bottom_sheet_top_area)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.isFitToContents = false
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheet.post {
            val parentHeight = (bottomSheet.parent as View).height
            bottomSheetBehavior.expandedOffset = (parentHeight * 0.1).toInt()
        }

        // 打开
        findViewById<Button>(R.id.show).setOnClickListener {
            if (bottomSheetTopArea.isGone) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                bottomSheetTopArea.visibility = View.VISIBLE
            }
            else if (bottomSheetTopArea.isVisible) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                bottomSheetTopArea.visibility = View.GONE
            }
        }
        // 按钮关闭
        findViewById<Button>(R.id.closeButton).setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            bottomSheetTopArea.visibility = View.GONE
        }
        // 点击上方区域关闭
        bottomSheetTopArea.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            bottomSheetTopArea.visibility = View.GONE
        }
    }
}