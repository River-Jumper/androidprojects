package com.example.componentpanel.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.componentpanel.R

class BottomSheetBarGroupView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs)  {
    var recyclerView: RecyclerView
    init {
        // view
        LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet_bar_items, this, true)
        recyclerView = findViewById(R.id.recyclerView_barItems)
    }
}