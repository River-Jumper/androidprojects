package com.example.componentpanel.ui.view.baseview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.componentpanel.R
import com.example.componentpanel.model.observablemodel.ObservableMenuListItemData
import com.example.componentpanel.ui.view.Bindable

class MenuListItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AbstractBaseView(context, attrs), Bindable<ObservableMenuListItemData> {
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

    private var boundData: ObservableMenuListItemData? = null
    private var lifecycleOwner: LifecycleOwner? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lifecycleOwner = findViewTreeLifecycleOwner()
    }

    override fun onDetachedFromWindow() {
        unbind()
        super.onDetachedFromWindow()
    }

    override fun bind(data: ObservableMenuListItemData) {
        unbind()
        this.boundData = data
        val owner = lifecycleOwner ?: return
        data.startIconResId.observe(owner) { resId ->
            if (resId != null) {
                setImage(START_ICON, resId)
            }
        }
        data.text.observe(owner) { text ->
            if (text != null) {
                setText(TEXT, text)
            }
        }
        data.subText.observe(owner) { subText ->
            if (subText != null) {
                setText(SUB_TEXT, subText)
            }
        }
        data.endIconResId.observe(owner) { resId ->
            if (resId != null) {
                setImage(END_ICON, resId)
            }
        }
        data.onViewClickListener.observe(owner) { listener ->
            setViewClickListener(listener)
        }
    }

    override fun unbind() {
        if (boundData != null && lifecycleOwner != null) {
            boundData?.startIconResId?.removeObservers(lifecycleOwner!!)
            boundData?.text?.removeObservers(lifecycleOwner!!)
            boundData?.subText?.removeObservers(lifecycleOwner!!)
            boundData?.endIconResId?.removeObservers(lifecycleOwner!!)
            boundData?.onViewClickListener?.removeObservers(lifecycleOwner!!)
        }
        boundData = null
        setViewClickListener(null)
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
