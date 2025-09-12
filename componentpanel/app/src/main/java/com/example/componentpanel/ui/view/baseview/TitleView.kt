package com.example.componentpanel.ui.view.baseview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.componentpanel.R
import com.example.componentpanel.model.observablemodel.ObservableTitleData
import com.example.componentpanel.ui.view.Bindable

class TitleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AbstractBaseView(context, attrs), Bindable<ObservableTitleData> {
    companion object {
        const val START_ICON = "startIcon"
        const val MAIN_TITLE = "mainTitle"
        const val SUB_TITLE = "subTitle"
        const val END_ICON = "endIcon"
    }
    
    override fun inflateLayout() {
        LayoutInflater.from(context).inflate(R.layout.title_bar, this, true)
    }
    
    override fun collectViews() {
        imageViews[START_ICON] = findViewById(R.id.imageView_bottomSheet_title_icon)
        imageViews[END_ICON] = findViewById(R.id.imageView_bottomSheet_title_star)
        textViews[MAIN_TITLE] = findViewById(R.id.textView_bottomSheet_title_mainTitle)
        textViews[SUB_TITLE] = findViewById(R.id.textView_bottomSheet_title_subTitle)
    }

    private var boundData: ObservableTitleData? = null
    private var lifecycleOwner: LifecycleOwner? = null
    private var pendingData: ObservableTitleData? = null

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
    

    override fun bind(data: ObservableTitleData) {
        unbind()
        this.boundData = data
        
        // 如果 lifecycleOwner 还没有准备好，先保存数据，等 onAttachedToWindow 时再绑定
        val owner = lifecycleOwner
        if (owner == null) {
            pendingData = data
            return
        }
        data.startIconResId.observe(owner) { resId ->
            if (resId != null) {
                setImage(START_ICON, resId)
            }
        }
        data.mainTitle.observe(owner) { text ->
            if (text != null) {
                setText(MAIN_TITLE, text)
            }
        }
        data.subTitle.observe(owner) { text ->
            if (text != null) {
                setText(SUB_TITLE, text)
            }
        }
        data.endIconResId.observe(owner) { resId ->
            if (resId != null) {
                setImage(END_ICON, resId)
            }
        }
        data.onEndIconClickedListener.observe(owner) { listener ->
            setImageClickListener(END_ICON, listener)
        }
    }

    override fun unbind() {
        if (boundData != null && lifecycleOwner != null) {
            boundData?.startIconResId?.removeObservers(lifecycleOwner!!)
            boundData?.mainTitle?.removeObservers(lifecycleOwner!!)
            boundData?.subTitle?.removeObservers(lifecycleOwner!!)
            boundData?.endIconResId?.removeObservers(lifecycleOwner!!)
            boundData?.onEndIconClickedListener?.removeObservers(lifecycleOwner!!)
        }
        boundData = null
        setImageClickListener(END_ICON, null)
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
