package com.example.componentpanel.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

abstract class BaseBottomSheetView @JvmOverloads constructor(
    context: Context, 
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    protected val imageViews = mutableMapOf<String, ImageView>()
    protected val textViews = mutableMapOf<String, TextView>()
    
    // 点击事件
    private val imageClickListeners = mutableMapOf<String, (() -> Unit)?>()
    private val textClickListeners = mutableMapOf<String, (() -> Unit)?>()
    
    init {
        inflateLayout()
        collectViews()
        setupClickListeners()
    }

    // 传布局
    protected abstract fun inflateLayout()
    
    // 加载需要的view元素到map中
    // 示例：
    // imageViews["startIcon"] = findViewById(R.id.start_icon)
    // textViews["mainText"] = findViewById(R.id.main_text)
    protected abstract fun collectViews()

    //为所有视图设置点击监听器(这里其实是为了加个注册而已，后续暴露外部修改listeners就行了)
    private fun setupClickListeners() {
        imageViews.forEach { (key, imageView) ->
            imageView.setOnClickListener {
                imageClickListeners[key]?.invoke()
            }
        }
        
        textViews.forEach { (key, textView) ->
            textView.setOnClickListener {
                textClickListeners[key]?.invoke()
            }
        }
    }

    fun setImage(key: String, resId: Int) {
        imageViews[key]?.setImageResource(resId)
    }

    fun setImage(key: String, url: String) {
        imageViews[key]?.let { imageView ->
            if (url.isNotBlank()) {
                Glide.with(context)
                    .load(url)
                    .into(imageView)
            }
        }
    }

    fun setText(key: String, text: String) {
        textViews[key]?.text = text
    }

    fun setImageClickListener(key: String, listener: (() -> Unit)?) {
        imageClickListeners[key] = listener
    }

    fun setTextClickListener(key: String, listener: (() -> Unit)?) {
        textClickListeners[key] = listener
    }

    fun hasImage(key: String): Boolean {
        return imageViews.containsKey(key)
    }

    fun hasText(key: String): Boolean {
        return textViews.containsKey(key)
    }
}
