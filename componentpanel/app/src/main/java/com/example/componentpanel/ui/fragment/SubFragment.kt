package com.example.componentpanel.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.componentpanel.R
import com.example.componentpanel.model.basemodel.MenuListGroupData
import com.example.componentpanel.ui.view.baseview.TitleView
import com.example.componentpanel.ui.view.compositview.MenuListGroupsView
import com.example.componentpanel.viewmodel.FragmentViewModel

typealias MenuGroups = List<MenuListGroupData>

class SubFragment : Fragment() {
    private lateinit var viewModel: FragmentViewModel
    private lateinit var menuListGroupsView: MenuListGroupsView

    private lateinit var titleView: TitleView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[FragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet_sub, container, false)
        menuListGroupsView = view.findViewById(R.id.item_menu_list)
        titleView = view.findViewById(R.id.view_bottomSheet_title)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 这里的groups只能监听到总体的groups的变化
        // 也就是说：唯有将groups整个替换掉，才会出现界面的更新
        // 但是实在想不出怎么往下做了，先偷会懒
        /*viewModel.curGroups.observe(viewLifecycleOwner) { groups ->
            menuListGroupsView.setItems(groups)
        }*/
    }
}