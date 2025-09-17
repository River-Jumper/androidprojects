package com.example.componentpanel.viewmodel.data

import com.example.componentpanel.R
import com.example.componentpanel.model.basemodel.MenuListItemData
import com.example.componentpanel.model.basemodel.MenuListGroupData
import com.example.componentpanel.model.basemodel.MenuListGroupsData
import com.example.componentpanel.model.basemodel.TitleData
import com.example.componentpanel.model.basemodel.ToolGroupData
import com.example.componentpanel.model.basemodel.ToolItemData

object TestDataGenerator {
    fun generateMenuListData(): MenuListGroupsData {
        // 添加到
        val addToGroups = MenuListGroupsData(
            mutableListOf(
                MenuListGroupData(
                    mutableListOf(
                        MenuListItemData(R.drawable.ic_start, "星标"),
                        MenuListItemData(R.drawable.ic_label, "标签"),
                        MenuListItemData(R.drawable.ic_add_shortcut, "添加快捷方式到"),
                        MenuListItemData(R.drawable.ic_add_quickaccess, "添加到快速访问"),
                        MenuListItemData(R.drawable.ic_add_home, "添加到主屏幕"),
                    )
                )
            )
        )

        val moreExportChangeGroups = MenuListGroupsData(
            mutableListOf(
                MenuListGroupData(
                    mutableListOf(
                        MenuListItemData(R.drawable.ic_export_text, "导出高亮文本"),
                        MenuListItemData(R.drawable.ic_extract_images, "批量提取图片"),
                        MenuListItemData(R.drawable.ic_save_template, "保存为自定义模板", subText = "减少重复性工作"),
                        MenuListItemData(R.drawable.ic_extract_page, "提取页面"),
                        MenuListItemData(R.drawable.ic_merge_docs, "合并文档"),
                        // 这里改动了一下，测试了一下两级页面
                        MenuListItemData(R.drawable.ic_doc_optimize, "文档瘦身", endIconResId = R.drawable.ic_go_forward, nextGroups = addToGroups),
                    )
                )
            )
        )

        val aiConvertChangeGroups = MenuListGroupsData.fromItemsList(
            mutableListOf(
                mutableListOf(
                    MenuListItemData(R.drawable.ic_ai_ppt, "AI生成PPT"),
                    MenuListItemData(R.drawable.ic_ai_mindmap, "AI文档脑图"),
                )
            )
        )

        val encryptionGroups = MenuListGroupsData.fromItemsList(
            mutableListOf(
                mutableListOf(
                    MenuListItemData(R.drawable.ic_doc_lock, "文档加密"),
                    MenuListItemData(R.drawable.ic_doc_verify, "文档认证"),
                )
            )
        )

        val backupGroups = MenuListGroupsData.fromItemsList(
            mutableListOf(
                mutableListOf(
                    MenuListItemData(R.drawable.ic_doc_repair, "文档修复"),
                    MenuListItemData(R.drawable.ic_history, "历史版本"),
                )
            )
        )

        // 各个组内项
        val barItemsList1 = mutableListOf(
            MenuListItemData(R.drawable.ic_rename, "重命名", null, null),
            MenuListItemData(R.drawable.ic_move_copy, "移动或复制", null, null),
            MenuListItemData(R.drawable.ic_send_to, "发送到", "电脑、手机等其他设备", null),
            MenuListItemData(R.drawable.ic_add_to, "添加到", null, R.drawable.ic_go_forward, nextGroups = addToGroups),
        )
        val barItemList2 = mutableListOf(
            MenuListItemData(R.drawable.ic_export_pdf, "输出为pdf", null, null),
            MenuListItemData(R.drawable.ic_export_image, "输出为图片", null, null),
            MenuListItemData(R.drawable.ic_more_export, "更多输出转换", "合并、瘦身", R.drawable.ic_go_forward, nextGroups = moreExportChangeGroups),
            MenuListItemData(R.drawable.ic_ai_convert, "AI生成转换", "PPT、截图", R.drawable.ic_go_forward, nextGroups = aiConvertChangeGroups),
        )
        val barItemList3 = mutableListOf(
            MenuListItemData(R.drawable.ic_tts, "语音朗读", null, null),
            MenuListItemData(R.drawable.ic_all_services, "全部服务", null, ),
        )
        val barItemList4 = mutableListOf(
            MenuListItemData(R.drawable.ic_backup_restore, "备份与恢复", null, R.drawable.ic_go_forward, nextGroups = backupGroups),
            MenuListItemData(R.drawable.ic_lock_auth, "加密与认证", null, R.drawable.ic_go_forward, nextGroups = encryptionGroups),
            MenuListItemData(R.drawable.ic_open_location, "打开文件位置", null, null),
            MenuListItemData(R.drawable.ic_doc_info, "文档信息", null, null),
        )
        val barItemList5 = mutableListOf(
            MenuListItemData(R.drawable.ic_help_feedback, "帮助与反馈", null, null),
        )

        // 总体的组
        val groups = mutableListOf(
            barItemsList1,
            barItemList2,
            barItemList3,
            barItemList4,
            barItemList5,
        )

        return MenuListGroupsData.fromItemsList(groups)
    }


    fun generateToolBarData(): ToolGroupData {
        val dataList = mutableListOf(
            ToolItemData(R.drawable.ic_save_as, "另存"),
            ToolItemData(R.drawable.ic_share, "分享"),
            ToolItemData(R.drawable.ic_print, "打印"),
            ToolItemData(R.drawable.ic_paper_tools, "论文工具"),
            ToolItemData(R.drawable.ic_find, "查找"),
            ToolItemData(null, "测试1"),
            ToolItemData(null, "测试2"),
            ToolItemData(null, "测试3"),
        )
        return ToolGroupData(dataList)
    }

    fun generateTitleData(): TitleData {
        return TitleData(
            R.drawable.ic_title,
            "新笔记",
            "字数：0",
            R.drawable.ic_close,
            null,
        )
    }


}
