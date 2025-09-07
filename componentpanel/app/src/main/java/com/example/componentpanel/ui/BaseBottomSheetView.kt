package com.example.componentpanel.ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

// 这个基类之所以出现，是我在ui文件下面写了两个之后view的kotlin类之后，觉得代码太重复了导致的
// 发现目前只有textView和imageView(在整个大作业阶段只有这个)，并且就需要做两件事：为所有的元素添加setter和setOnClicked方法
// 于是和ai扯皮，我说我要简化一下这里的重复代码，它前后给了两套方案，一是工厂加基类
// 我觉得太麻烦了，告诉他我不关心布局类型，不关系布局样式，我只要拿到所有元素，并且设置setter和setOnClicked方法而已
// 于是ai给出了一个interface的实现，我阅读了一下代码，觉得简直不像人类，而且重复的问题没有解决，实现起来反而更复杂了
// 然后我这个时候有一点想法：我跟他说，我能不能整一个基类，基类的成员中只有imageView和textView的两种list（以便不定数量的添加）
// 然后基类实现一种方法，能够为这两个list里面的所有元素实现setter和setOnClicked方法，
// 派生类只需要在list中注册自己的view元素，并且注册完毕调用这个方法就行了
// ai觉得很棒，但是认为在list中管理不是很好区分，毕竟各个view的id不同
// 于是给了我将list转换为map的建议，这样就能和id绑定了，我觉得棒极了，然后就采用了这个方案
// 感觉这段扯皮还是蛮有意思的，现在是周日，将要去吃饭了，小小修改一下ai的代码，并且暂且记录一下
/**
 * 底部弹窗视图的抽象基类
 * 提供统一的ImageView和TextView管理功能
 * 
 * 使用方式：
 * 1. 继承此类
 * 2. 实现 inflateLayout() 方法加载布局
 * 3. 实现 collectViews() 方法收集视图到Map中
 * 4. 通过 setImage()、setText()、setImageClickListener()、setTextClickListener() 方法操作视图
 */
abstract class BaseBottomSheetView @JvmOverloads constructor(
    context: Context, 
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    
    // 使用Map存储视图，避免顺序依赖
    protected val imageViews = mutableMapOf<String, ImageView>()
    protected val textViews = mutableMapOf<String, TextView>()
    
    // 点击事件存储
    private val imageClickListeners = mutableMapOf<String, (() -> Unit)?>()
    private val textClickListeners = mutableMapOf<String, (() -> Unit)?>()
    
    init {
        inflateLayout()
        collectViews()
        setupClickListeners()
    }
    
    /**
     * 子类实现：加载布局文件
     */
    protected abstract fun inflateLayout()
    
    /**
     * 子类实现：收集视图到Map中
     * 示例：
     * imageViews["startIcon"] = findViewById(R.id.start_icon)
     * textViews["mainText"] = findViewById(R.id.main_text)
     */
    protected abstract fun collectViews()
    
    /**
     * 基类实现：为所有视图设置点击监听器(这里其实是为了加个注册而已，后续暴露外部修改listeners就行了)
     */
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
    
    /**
     * 设置图片资源
     * @param key 图片视图的标识符
     * @param resId 图片资源ID
     */
    fun setImage(key: String, resId: Int) {
        imageViews[key]?.setImageResource(resId)
    }
    
    /**
     * 设置文本内容
     * @param key 文本视图的标识符
     * @param text 文本内容
     */
    fun setText(key: String, text: String) {
        textViews[key]?.text = text
    }
    
    /**
     * 设置图片点击事件
     * @param key 图片视图的标识符
     * @param listener 点击事件回调，传null可移除监听器
     */
    fun setImageClickListener(key: String, listener: (() -> Unit)?) {
        imageClickListeners[key] = listener
    }
    
    /**
     * 设置文本点击事件
     * @param key 文本视图的标识符
     * @param listener 点击事件回调，传null可移除监听器
     */
    fun setTextClickListener(key: String, listener: (() -> Unit)?) {
        textClickListeners[key] = listener
    }
    
    /**
     * 获取所有图片视图的key列表
     * @return 图片视图标识符列表
     */
    fun getImageKeys(): List<String> {
        return imageViews.keys.toList()
    }
    
    /**
     * 获取所有文本视图的key列表
     * @return 文本视图标识符列表
     */
    fun getTextKeys(): List<String> {
        return textViews.keys.toList()
    }
    
    /**
     * 检查是否存在指定的图片视图
     * @param key 图片视图标识符
     * @return 是否存在
     */
    fun hasImage(key: String): Boolean {
        return imageViews.containsKey(key)
    }
    
    /**
     * 检查是否存在指定的文本视图
     * @param key 文本视图标识符
     * @return 是否存在
     */
    fun hasText(key: String): Boolean {
        return textViews.containsKey(key)
    }
}
