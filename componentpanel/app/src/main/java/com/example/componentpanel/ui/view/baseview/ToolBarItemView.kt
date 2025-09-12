package com.example.componentpanel.ui.view.baseview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.componentpanel.R
import com.example.componentpanel.model.observablemodel.ObservableToolItemData
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.componentpanel.ui.view.Bindable

class ToolBarItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AbstractBaseView(context, attrs), Bindable<ObservableToolItemData> {
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

    private var boundData: ObservableToolItemData? = null
    private var lifecycleOwner: LifecycleOwner? = null
    private var pendingData: ObservableToolItemData? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lifecycleOwner = findViewTreeLifecycleOwner()
        // 如果有待绑定的数据，现在进行绑定
        pendingData?.let { data ->
            pendingData = null
            bind(data)
        }
    }
    
    override fun onDetachedFromWindow() {
        unbind()
        super.onDetachedFromWindow()
    }

    override fun bind(data: ObservableToolItemData) {
        unbind()
        this.boundData = data
        
        // 如果 lifecycleOwner 还没有准备好，先保存数据，等 onAttachedToWindow 时再绑定
        val owner = lifecycleOwner
        if (owner == null) {
            pendingData = data
            return
        }
        data.iconResId.observe(owner) { resId ->
            if (resId != null) {
                setImage(ICON, resId)
            }
        }
        data.text.observe(owner) { text ->
            if (text != null) {
                setText(TEXT, text)
            }
        }
        data.onViewClickListener.observe(owner) { listener ->
            setViewClickListener(listener)
        }
    }

    override fun unbind() {
        if (boundData != null && lifecycleOwner != null) {
            boundData?.iconResId?.removeObservers(lifecycleOwner!!)
            boundData?.text?.removeObservers(lifecycleOwner!!)
            boundData?.onViewClickListener?.removeObservers(lifecycleOwner!!)
        }
        boundData = null
        setViewClickListener(null)
    }
}