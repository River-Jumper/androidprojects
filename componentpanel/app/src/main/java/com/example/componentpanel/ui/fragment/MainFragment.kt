package com.example.componentpanel.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.componentpanel.R
import com.example.componentpanel.ui.view.baseview.TitleView
import com.example.componentpanel.ui.view.compositview.MenuListGroupsView
import com.example.componentpanel.ui.view.compositview.ToolGroupView
import com.example.componentpanel.viewmodel.MainViewModel
import androidx.lifecycle.ViewModelProvider

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var titleView: TitleView
    private lateinit var toolGroupView: ToolGroupView
    private lateinit var menuListGroupsView: MenuListGroupsView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        titleView = view.findViewById(R.id.view_bottomSheet_title)
        toolGroupView = view.findViewById(R.id.item_tool_bar)
        menuListGroupsView = view.findViewById(R.id.item_menu_list)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}