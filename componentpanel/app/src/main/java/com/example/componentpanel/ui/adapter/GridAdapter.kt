package com.example.componentpanel.ui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.componentpanel.ui.view.BottomSheetSquareView

// 一个思考：这么层层封装下去，写好的square的自定义原子view的各种接口已经被埋住了
// 难道要把接口层层传上去吗？实在太麻烦了，不如把square给暴露出去
// 那遇到了一个问题：凭什么认为用户能够拿着一个position->square的映射就能够方便地找到？
// 而且这个position还会随着修改一变再变
// 能不能像在设计BaseBottomSheetView的时候一样，通过id(名字)来访问呢？
// 这样的话，需要在这个adapter内维护一张map(id->position)
// 同时将GridData的数据结构加上id，这样连id都是用户自定义注册的了
// (完成了，但是看上去好丑)(TODO 并且没做如果id重复了怎么办，重复了会出错的，这里之后再改)

// 但是这里有另一个思考：请问，暴露出去的square到底是什么呢？
// 使用的recyclerView，那么是通过onBindViewHolder动态创建的square视图
// 这样带来了两个问题：1. 还没有在屏幕上显示的square，不能够被访问到（因为压根没创建）
// 2. 即使为其动态绑定了listener，一旦square滑出屏幕，所有资源又被回收了，下次来还是不带listener的
// 那么只能将listener(是一个函数引用(应该))放置在GridData里面了，在onBindViewHolder里面反复绑定
// 认为性能会有一点问题，但是先不管了
// 而且感觉这样GridData的设计好奇怪啊，一个data类既要管理数据又要管理点击事件
// 没有想出解决方法，暂时先委屈一下GridData(为了表现高耦合性，暂时放置在同一个.kt下)
// 如果要考虑到线程安全问题，那就死翘翘了（这里做得很差啊）

data class GridData(
    // var id: String,
    var iconResId: Int?,
    var text: String?,
    var onImageClickListener: (() -> Unit)? = null,
    var onTextClickListener: (() -> Unit)? = null
)

class GridAdapter(private val itemList: MutableList<GridData>)
    : RecyclerView.Adapter<GridAdapter.ViewHolder>() {
    // 包裹的是自定义视图（小方块阵列）
    class ViewHolder(val squareView: BottomSheetSquareView)
        : RecyclerView.ViewHolder(squareView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = BottomSheetSquareView(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val data = itemList[position]
        data.iconResId?.run {
            holder.squareView.setImage(BottomSheetSquareView.ICON, this)
        }
        data.text?.run {
            holder.squareView.setText(BottomSheetSquareView.TEXT, this)
        }
        data.onImageClickListener?.run {
            holder.squareView.setImageClickListener(BottomSheetSquareView.ICON, this)
        }
        data.onTextClickListener?.run {
            holder.squareView.setTextClickListener(BottomSheetSquareView.TEXT, this)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    // 尝试在这里加入增删改查接口
    private fun checkPosition(position: Int): Boolean {
        return position >= 0 && position <= itemList.size
    }
    fun getItem(position: Int): GridData? {
        if (!checkPosition(position)) {
            throw IndexOutOfBoundsException("Position $position is out of bounds")
        }
        return itemList[position]
    }
    fun addItem(position: Int = itemList.size, newItem: GridData) {
        if (!checkPosition(position)) {
            throw IndexOutOfBoundsException("Position $position is out of bounds")
        }
        itemList.add(position, newItem)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        if (!checkPosition(position)) {
            throw IndexOutOfBoundsException("Position $position is out of bounds")
        }
            itemList.removeAt(position)
            notifyItemRemoved(position)
    }

    fun updateItem(position: Int, newItem: GridData) {
        if (!checkPosition(position)) {
            throw IndexOutOfBoundsException("Position $position is out of bounds")
        }
        itemList[position] = newItem
        notifyItemChanged(position)
    }
    // 整个替换
    // 这里的替换等同于重新绘制
    // 有一种差量重绘的方法，暂时没有看懂
    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newList: List<GridData>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }
/*
    // 有关map的废稿
    // 初始化map
    private val idToPosition = mutableMapOf<String, Int>()
    init {
        initializePositionMappings()
    }
    private fun initializePositionMappings() {
        idToPosition.clear()
        itemList.forEachIndexed { index, data ->
            idToPosition[data.id] = index
        }
    }
    // 维护元素个数改变之后map中的位置更新
    private fun updatePositionMappings(affectedPosition: Int, offset: Int) {
        idToPosition.forEach { (id, position) ->
            if (position >= affectedPosition) {
                idToPosition[id] = position + offset
            }
        }
    }

    fun getItemById(id: String): GridData? {
        return itemList.find { it.id == id }
    }
    fun addItemById(id: String, newItem: GridData) {
        val position = idToPosition[id]
        position?.run {
            addItem(this, newItem)
        }
    }
    fun removeItemById(id: String) {
        val position = idToPosition[id]
        if(position != -1) {
            idToPosition.remove(id)
            position?.run {
                removeItem(this)
            }
        }
    }
    fun updateItemById(id: String, newItem: GridData) {
        val position = idToPosition[id]
        position?.run {
            updateItem(this, newItem)
        }
    }*/
}