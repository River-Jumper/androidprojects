package com.example.componentpanel.ui.view.baseview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.componentpanel.R

class TitleBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AbstractBaseView(context, attrs) {
    companion object {
        const val ICON = "icon"
        const val MAIN_TITLE = "mainTitle"
        const val SUB_TITLE = "subTitle"
        const val STAR = "star"
    }
    
    override fun inflateLayout() {
        LayoutInflater.from(context).inflate(R.layout.title_bar, this, true)
    }
    
    override fun collectViews() {
        imageViews[ICON] = findViewById(R.id.imageView_bottomSheet_title_icon)
        imageViews[STAR] = findViewById(R.id.imageView_bottomSheet_title_star)
        textViews[MAIN_TITLE] = findViewById(R.id.textView_bottomSheet_title_mainTitle)
        textViews[SUB_TITLE] = findViewById(R.id.textView_bottomSheet_title_subTitle)
    }
}

/*
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
        setIconView(R.drawable.ic_title)
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
}*/
