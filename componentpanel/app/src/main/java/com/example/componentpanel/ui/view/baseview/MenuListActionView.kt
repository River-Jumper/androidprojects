package com.example.componentpanel.ui.view.baseview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.componentpanel.R

class MenuListActionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AbstractBaseView(context, attrs) {
    companion object {
        const val START_ICON = "startIcon"
        const val END_ICON = "endIcon"
        const val TEXT = "text"
        const val SUB_TEXT = "subText"
    }
    override fun inflateLayout() {
        LayoutInflater.from(context).inflate(R.layout.item_menu_list_action, this, true)
    }

    override fun collectViews() {
        imageViews[START_ICON] = findViewById(R.id.imageView_bottomSheet_bar_start_icon)
        imageViews[END_ICON] = findViewById(R.id.imageView_bottomSheet_bar_end_icon)
        textViews[TEXT] = findViewById(R.id.textView_bottomSheet_bar_text)
        textViews[SUB_TEXT] = findViewById(R.id.textView_bottomSheet_bar_subText)
    }
}

/*
class BottomSheetBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private var barStartIcon: ImageView
    private var text: TextView
    private var barEndIcon: ImageView

    var onBarStartIconClick: (() -> Unit)? = null
    var onTextClick: (() -> Unit)? = null
    var onBarEndIconClick: (() -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet_bar, this, true)
        barStartIcon = findViewById(R.id.imageView_bottomSheet_bar_start_icon)
        text = findViewById(R.id.textView_bottomSheet_bar_text)
        barEndIcon = findViewById(R.id.imageView_bottomSheet_bar_end_icon)

        barStartIcon.setOnClickListener {
            onBarStartIconClick?.invoke()
        }
        text.setOnClickListener {
            onTextClick?.invoke()
        }
        barEndIcon.setOnClickListener {
            onBarEndIconClick?.invoke()
        }

        setBarStartIcon(R.drawable.ic_page_setting)
        setText("页面设置")
        //setBarEndIcon(R.drawable.ic_drop_down)
    }

    fun setBarStartIcon(resId: Int) {
        barStartIcon.setImageResource(resId)
    }
    fun setText(text: String) {
        this.text.text = text
    }
    fun setBarEndIcon(resId: Int) {
        barEndIcon.setImageResource(resId)
    }
}*/
