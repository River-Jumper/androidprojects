package com.example.componentpanel.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.componentpanel.ui.view.BottomSheetBarView

data class BarItem(
    // val id: String,
    var barStartIconResId: Int?,
    var text: String?,
    val subText: String?,
    var barEndIconResId: Int?
)

class BarItemAdapter(private val barItems: List<BarItem>) : RecyclerView.Adapter<BarItemAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val barView = BottomSheetBarView(parent.context)
        barView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(barView)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val barItem = barItems[position]
        barItem.barStartIconResId?.run {
            holder.barItemView.setImage(BottomSheetBarView.START_ICON, this)
        }
        barItem.text?.run {
            holder.barItemView.setText(BottomSheetBarView.TEXT, this)
        }
        barItem.subText?.run {
            holder.barItemView.setText(BottomSheetBarView.SUB_TEXT, this)
        }
        barItem.barEndIconResId?.run {
            holder.barItemView.setImage(BottomSheetBarView.END_ICON, this)
        }
    }

    override fun getItemCount() = barItems.size

    class ViewHolder(val barItemView: BottomSheetBarView) : RecyclerView.ViewHolder(barItemView)

}