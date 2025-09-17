package com.example.componentpanel.ui.view.baseview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

abstract class AbstractBaseView @JvmOverloads constructor(
    context: Context, 
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    protected val imageViews = mutableMapOf<String, ImageView>()
    protected val textViews = mutableMapOf<String, TextView>()
    
    // 图标文字的点击事件
    private val imageClickListeners = mutableMapOf<String, (() -> Unit)?>()
    private val textClickListeners = mutableMapOf<String, (() -> Unit)?>()

    // 本身的点击事件
    private var viewClickListener: (() -> Unit)? = null
    init {
        inflateLayout()
        collectViews()
        setupClickListeners()
    }

    // 加载布局
    protected abstract fun inflateLayout()
    
    // 加载需要的view元素到map中
    // 小例子：
    // imageViews["startIcon"] = findViewById(R.id.start_icon)
    // textViews["mainText"] = findViewById(R.id.main_text)
    protected abstract fun collectViews()

    // 为所有视图设置点击监听器(这里其实是为了加个注册而已，后续修改对应的listeners就行了)
    // 后面发现其实对文字和图标添加监听其实没什么用，因为好像都是点击整个视图
    private fun setupClickListeners() {
        imageViews.forEach { (key, imageView) ->
            if (imageView.isClickable) {
                imageView.setOnClickListener {
                    imageClickListeners[key]?.invoke()
                }
            }
        }
        
        textViews.forEach { (key, textView) ->
            if (textView.isClickable) {
                textView.setOnClickListener {
                    textClickListeners[key]?.invoke()
                }
            }
        }

        // 本身的点击事件
        this.setOnClickListener {
            viewClickListener?.invoke()
        }
    }
    fun setImage(key: String, resId: Int) {
        imageViews[key]?.setImageResource(resId)
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
    fun setViewClickListener(listener: (() -> Unit)?) {
        viewClickListener = listener
    }
}

