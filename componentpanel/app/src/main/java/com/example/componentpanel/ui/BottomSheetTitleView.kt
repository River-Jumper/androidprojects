package com.example.componentpanel.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.componentpanel.R

class BottomSheetTitleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val iconView: ImageView
    private val mainTitle: TextView
    private val subTitle: TextView
    private val starView: ImageView

    // public
    var onImageViewClickedListener: (() -> Unit)? = null
    var onMainTitleClickedListener: (() -> Unit)? = null
    var onSubTitleClickedListener: (() -> Unit)? = null
    var onStarViewClickedListener: (() -> Unit)? = null

    init {
        // view
        LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet_title, this, true)
        iconView = findViewById(R.id.imageView_bottomSheet_title_icon)
        mainTitle = findViewById(R.id.textView_bottomSheet_title_mainTitle)
        subTitle = findViewById(R.id.textView_bottomSheet_title_subTitle)
        starView = findViewById(R.id.imageView_bottomSheet_title_star)
        // listener
        iconView.setOnClickListener {
            onImageViewClickedListener?.invoke()
        }
        mainTitle.setOnClickListener {
            onMainTitleClickedListener?.invoke()
        }
        subTitle.setOnClickListener {
            onSubTitleClickedListener?.invoke()
        }
        starView.setOnClickListener {
            onStarViewClickedListener?.invoke()
        }
        // default
        setIconView(R.drawable.ic_temp)
        setStartView(R.drawable.ic_star_grey)
        setMainTitle("新笔记")
        setSubTitle("字数：0")
    }
    // ToDo:
    // 注意了！这里的图标资源只是能够加载R.drawable...这样的资源
    // 考虑一下，如果由外部配置来指定图片资源，又该怎么办
    fun setIconView(resId: Int) {
        iconView.setImageResource(resId)
    }
    fun setStartView(resId: Int) {
        starView.setImageResource(resId)
    }
    fun setMainTitle(text: String) {
        mainTitle.text = text
    }
    fun setSubTitle(text: String) {
        subTitle.text = text
    }
}