package com.example.componentpanel.ui.view.baseview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.componentpanel.R

class ToolBarActionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AbstractBaseView(context, attrs) {
    companion object {
        const val ICON = "icon"
        const val TEXT = "text"
    }
    
    override fun inflateLayout() {
        LayoutInflater.from(context).inflate(R.layout.item_tool_bar_action, this, true)
    }
    
    override fun collectViews() {
        imageViews[ICON] = findViewById(R.id.imageView_bottomSheet_square_icon)
        textViews[TEXT] = findViewById(R.id.textView_bottomSheet_square_text)
    }
}