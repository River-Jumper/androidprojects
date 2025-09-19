# ComponentPanel - Android 组件面板
### [WPS智能文档链接](https://365.kdocs.cn/l/cndl2eKTGbA8?from=koa&reqtype=kdocs&startTime=1756969105583&createDirect=true&newFile=true)

## 🏗️ 项目架构

### 包结构

```
com.example.componentpanel/
├── model/                          # 数据模型层
│   ├── basemodel/                  # 基础数据模型
│   │   ├── MenuListItemData.kt
│   │   ├── MenuListGroupData.kt
│   │   ├── TitleData.kt
│   │   └── ToolItemData.kt
│   └── observablemodel/            # 可观察数据模型
│       ├── ObservableMenuListItemData.kt
│       ├── ObservableTitleData.kt
│       └── ObservableToolItemData.kt
├── ui/                            # UI 层
│   ├── activity/                  # Activity
│   │   └── MainActivity.kt
│   ├── fragment/                  # Fragment
│   │   └── Fragment.kt
│   ├── manager/                   # 管理器
│   │   ├── BottomSheetManager.kt
│   │   └── DataInitializer.kt
│   └── view/                      # 自定义视图
│       ├── baseview/              # 基础视图
│       ├── compositview/          # 复合视图
│       └── Bindable.kt            # 数据绑定接口
└── viewmodel/                     # ViewModel 层
    ├── data/                      # 数据生成器
    │   └── TestDataGenerator.kt
    ├── MenuViewModel.kt
    ├── TitleViewModel.kt
    └── ToolViewModel.kt
```

## 🔧 核心组件

### 1. MainActivity
- **职责**：应用入口，组件协调
- **特点**：简洁的初始化逻辑，职责分离
- **关键方法**：
  - `initBottomSheet()` - 初始化底部抽屉
  - `initData()` - 初始化数据
  - `initNavigation()` - 初始化导航

### 2. BottomSheetManager
- **职责**：管理底部抽屉的显示/隐藏
- **特点**：封装了复杂的 BottomSheetBehavior 逻辑
- **核心功能**：
  - 抽屉状态管理
  - 动画效果控制
  - 用户交互处理

### 3. DataInitializer
- **职责**：数据初始化和事件绑定
- **特点**：集中管理所有数据相关逻辑
- **核心功能**：
  - 测试数据生成
  - 事件监听器设置
  - 组件间交互配置

### 4. 自定义视图系统

#### AbstractBaseView
- **作用**：所有自定义视图的基类
- **特点**：
  - 统一的视图管理
  - 自动数据绑定
  - 生命周期管理

#### 复合视图
- **MenuListGroupsView**：菜单列表组视图
- **ToolGroupView**：工具栏组视图
- **TitleView**：标题栏视图

## 🧪 测试功能

项目包含三个测试功能，展示组件间的动态交互：

1. **测试1 - 图标切换**：点击切换工具栏按钮图标
2. **测试2 - 工具栏重排**：随机重新排列工具栏按钮
3. **测试3 - 菜单重排**：工具栏按钮控制菜单列表重排