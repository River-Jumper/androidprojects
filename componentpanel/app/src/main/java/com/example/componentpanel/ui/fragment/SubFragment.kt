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
import com.example.componentpanel.viewmodel.MainViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.componentpanel.model.observablemodel.ObservableMenuListGroupsData


class SubFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var menuListGroupsView: MenuListGroupsView
    private lateinit var titleView: TitleView

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
        menuListGroupsView = view.findViewById(R.id.item_menu_list)
        titleView = view.findViewById(R.id.view_bottomSheet_title)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("title")
        title?.run {
            titleView.setText(TitleView.MAIN_TITLE, this)
        }
        titleView.setImage(TitleView.START_ICON, R.drawable.ic_go_back)
        titleView.setImageClickListener(TitleView.START_ICON) {
            when(viewModel.canGoBack()) {
                true -> {
                    viewModel.goBack()
                    findNavController().popBackStack()
                }
                false -> requireActivity().finish()
            }
        }
        bindCurrentData()
    }

    override fun onResume() {
        super.onResume()
        bindCurrentData()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            when(viewModel.canGoBack()) {
                true -> {
                    viewModel.goBack()
                    findNavController().popBackStack()
                }
                false -> requireActivity().finish()
            }
        }
    }

    private fun bindCurrentData() {
        viewModel.curGroups?.let { groups ->
            menuListGroupsView.bind(groups)
        }
    }

    private fun createSubFragment(title: String, nextGroups: ObservableMenuListGroupsData) {
        // 1. 更新 ViewModel 栈
        viewModel.goForward(nextGroups)
        // findNavController().navigate(
        //     SubFragmentDirections.actionSubFragmentSelf(title)
        // )
    }
}