package com.example.componentpanel.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.componentpanel.R

class BottomSheetSquareView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : BaseBottomSheetView(context, attrs) {
    companion object {
        const val ICON = "icon"
        const val TEXT = "text"
    }
    
    override fun inflateLayout() {
        LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet_square, this, true)
    }
    
    override fun collectViews() {
        imageViews[ICON] = findViewById(R.id.imageView_bottomSheet_square_icon)
        textViews[TEXT] = findViewById(R.id.textView_bottomSheet_square_text)
    }
    
    init {
        // 默认（只是效果演示）
        setImage(ICON, R.drawable.ic_save_as)
        setText(TEXT, "另存")
    }
}