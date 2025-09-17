package com.example.componentpanel.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.componentpanel.R
import com.example.componentpanel.model.basemodel.TitleData
import com.example.componentpanel.model.observablemodel.ObservableMenuListGroupsData
import com.example.componentpanel.model.observablemodel.ObservableTitleData
import com.example.componentpanel.ui.manager.BottomSheetManager
import com.example.componentpanel.viewmodel.MenuViewModel
import com.example.componentpanel.ui.fragment.FragmentDirections
import com.example.componentpanel.ui.manager.DataInitializer
import com.example.componentpanel.viewmodel.ToolViewModel
import com.example.componentpanel.viewmodel.TitleViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        // BottomSheet 相关常量
        private val BOTTOM_SHEET_ID = R.id.bottomSheet
        private val TOP_AREA_ID = R.id.bottom_sheet_top_area
        private val TOGGLE_BUTTON_ID = R.id.show
        private val FRAGMENT_CONTAINER_ID = R.id.fragment_container
    }

    private lateinit var navController: NavController
    private lateinit var bottomSheetManager: BottomSheetManager
    private lateinit var dataInitializer: DataInitializer

    private val menuViewModel: MenuViewModel by viewModels()
    private val titleViewModel: TitleViewModel by viewModels()
    private val toolViewModel: ToolViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomSheet()
        initData()
        initNavigation()
    }
    private fun initBottomSheet() {
        bottomSheetManager = BottomSheetManager(this)
        bottomSheetManager.init(
            bottomSheetId = BOTTOM_SHEET_ID,
            topAreaId = TOP_AREA_ID,
            toggleButtonId = TOGGLE_BUTTON_ID
        )
    }

    private fun initData() {
        dataInitializer = DataInitializer(this, titleViewModel, toolViewModel, menuViewModel)
        // 函数引用的赋值
        dataInitializer.hideBottomSheet = bottomSheetManager::hide
        dataInitializer.createFragment = ::createFragment
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(FRAGMENT_CONTAINER_ID) as? androidx.navigation.fragment.NavHostFragment
        navController = navHostFragment?.navController ?: return
    }

    private fun createFragment(title: String, nextGroups: ObservableMenuListGroupsData) {
        menuViewModel.goForward(nextGroups)
        titleViewModel.goForward(
            ObservableTitleData(
                TitleData(
                    startIconResId = R.drawable.ic_go_back,
                    midTitle = title,
                )
            )
        )
        toolViewModel.goForward(null)

        navController.navigate(
            FragmentDirections.actionFragmentSelf()
        )
    }
}