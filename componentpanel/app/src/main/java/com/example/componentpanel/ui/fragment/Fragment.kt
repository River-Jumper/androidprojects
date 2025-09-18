package com.example.componentpanel.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.componentpanel.R
import com.example.componentpanel.ui.view.baseview.TitleView
import com.example.componentpanel.ui.view.compositview.MenuListGroupsView
import com.example.componentpanel.viewmodel.MenuViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.componentpanel.ui.view.compositview.ToolGroupView
import com.example.componentpanel.viewmodel.TitleViewModel
import com.example.componentpanel.viewmodel.ToolViewModel


class Fragment : Fragment() {
    private lateinit var menuViewModel: MenuViewModel
    private lateinit var titleViewModel: TitleViewModel
    private lateinit var toolViewModel: ToolViewModel
    private lateinit var menuListGroupsView: MenuListGroupsView
    private lateinit var titleView: TitleView
    private lateinit var toolGroupView: ToolGroupView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menuViewModel = ViewModelProvider(requireActivity())[MenuViewModel::class.java]
        titleViewModel = ViewModelProvider(requireActivity())[TitleViewModel::class.java]
        toolViewModel = ViewModelProvider(requireActivity())[ToolViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        menuListGroupsView = view.findViewById(R.id.item_menu_list)
        titleView = view.findViewById(R.id.view_bottomSheet_title)
        toolGroupView = view.findViewById(R.id.item_tool_bar)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            goBack()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        if (menuViewModel.canGoBack()) {
            titleViewModel.curItem?.setOnStartIconClickedListener {
                goBack()
            }
        }
    }

    private fun goBack() {
        when(menuViewModel.canGoBack()) {
            true -> {
                stackGoBack()
                findNavController().popBackStack()
            }
            false -> requireActivity().finish()
        }
    }

    private fun bindData() {
        menuViewModel.curItem?.let { groups ->
            menuListGroupsView.bind(groups)
        }
        toolViewModel.curItem?.let { group ->
            toolGroupView.bind(group)
        }
        titleViewModel.curItem?.let { title ->
            titleView.bind(title)
        }
    }
    private fun stackGoBack() {
        if (menuViewModel.canGoBack()) {
            menuViewModel.goBack()
        }
        if (toolViewModel.canGoBack()) {
            toolViewModel.goBack()
        }
        if (titleViewModel.canGoBack()) {
            titleViewModel.goBack()
        }
    }
}