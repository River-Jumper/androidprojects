package com.example.componentpanel.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.componentpanel.ui.view.BottomSheetBarGroupView



data class Group(
    // val id: String,
    val items: MutableList<BarItem>
)

class GroupAdapter(private val groups: List<Group>) : RecyclerView.Adapter<GroupAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val groupView = BottomSheetBarGroupView(parent.context)
        groupView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(groupView)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val group = groups[position]
        holder.bind(group.items)
    }

    override fun getItemCount() = groups.size

    class ViewHolder(val groupView: BottomSheetBarGroupView) : RecyclerView.ViewHolder(groupView) {
        fun bind(items: List<BarItem>) {
            groupView.recyclerView.layoutManager =
                LinearLayoutManager(groupView.context, LinearLayoutManager.VERTICAL, false)
            groupView.recyclerView.adapter = BarItemAdapter(items)
        }
    }

}