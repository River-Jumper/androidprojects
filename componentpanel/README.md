# ComponentPanel - Android 组件面板项目

## 📖 项目概述

ComponentPanel 是一个展示 Android 组件化开发最佳实践的示例项目。该项目实现了一个功能丰富的底部抽屉面板，包含菜单列表、工具栏和标题栏等组件，展示了如何通过良好的架构设计实现组件间的灵活交互。

## 🎯 项目特色

- **组件化架构**：清晰的模块分离，每个组件职责明确
- **动态交互**：组件间可以相互控制和配置，展示高度灵活性
- **MVVM 模式**：使用 ViewModel 和 LiveData 实现数据绑定
- **可扩展设计**：易于添加新功能和组件
- **代码质量**：遵循 Android 开发最佳实践

## 🏗️ 项目架构

### 整体架构图

```
┌─────────────────────────────────────────────────────────────┐
│                    MainActivity                             │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │ BottomSheetMgr  │  │ DataInitializer │  │ Navigation  │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                      Fragment                              │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐ │
│  │ TitleView   │  │ MenuList    │  │ ToolGroupView       │ │
│  └─────────────┘  └─────────────┘  └─────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    ViewModel Layer                         │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐ │
│  │ TitleVM     │  │ MenuVM      │  │ ToolVM              │ │
│  └─────────────┘  └─────────────┘  └─────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                     Model Layer                            │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐ │
│  │ BaseModel   │  │ Observable  │  │ TestDataGenerator   │ │
│  └─────────────┘  └─────────────┘  └─────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

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

## 🎨 设计模式

### 1. MVVM 模式
```kotlin
// View 层
class TitleView : AbstractBaseView, Bindable<ObservableTitleData>

// ViewModel 层
class TitleViewModel : NavigatorViewModel<ObservableTitleData>

// Model 层
class ObservableTitleData : LiveData 包装
```

### 2. 观察者模式
```kotlin
// 数据变化自动更新 UI
data.startIconResId.observe(owner) { resId ->
    if (resId != null) setImage(START_ICON, resId)
}
```

### 3. 策略模式
```kotlin
// 不同按钮的不同行为策略
when(item.text.value) {
    "测试1" -> setupTest1Event(item)
    "测试2" -> setupTest2Event(item)
    "测试3" -> setupTest3Event(item)
}
```

### 4. 依赖注入
```kotlin
// 通过构造函数注入依赖
class DataInitializer(
    private val activity: AppCompatActivity,
    private val titleViewModel: TitleViewModel,
    private val toolViewModel: ToolViewModel,
    private val menuViewModel: MenuViewModel
)
```

## 🚀 核心功能

### 1. 动态组件交互
- **工具栏控制菜单**：工具栏按钮可以重新排列菜单列表
- **图标状态切换**：动态切换按钮图标状态
- **跨组件配置**：一个组件可以控制另一个组件的行为

### 2. 数据绑定系统
- **自动更新**：数据变化时 UI 自动更新
- **生命周期管理**：自动处理观察者的注册和注销
- **空安全**：使用 Kotlin 的空安全特性

### 3. 导航系统
- **栈式导航**：支持前进和后退
- **状态保持**：导航状态在配置变化时保持
- **深度链接**：支持多级菜单导航

## 📱 使用示例

### 基本使用
```kotlin
// 初始化组件
val dataInitializer = DataInitializer(
    activity = this,
    titleViewModel = titleViewModel,
    toolViewModel = toolViewModel,
    menuViewModel = menuViewModel
)

// 设置回调
dataInitializer.hideBottomSheet = bottomSheetManager::hide
dataInitializer.createFragment = ::createFragment
```

### 自定义视图
```kotlin
class CustomView : AbstractBaseView {
    override fun inflateLayout() {
        LayoutInflater.from(context).inflate(R.layout.custom_view, this, true)
    }
    
    override fun collectViews() {
        imageViews["icon"] = findViewById(R.id.icon)
        textViews["text"] = findViewById(R.id.text)
    }
}
```

## 🧪 测试功能

项目包含三个测试功能，展示组件间的动态交互：

1. **测试1 - 图标切换**：点击切换工具栏按钮图标
2. **测试2 - 工具栏重排**：随机重新排列工具栏按钮
3. **测试3 - 菜单重排**：工具栏按钮控制菜单列表重排

## 🔍 技术亮点

### 1. 延迟执行模式
```kotlin
// 使用 lambda 延迟执行，确保变量已赋值
titleViewModel.root?.setOnEndIconClickedListener {
    hideBottomSheet?.invoke()
}
```

### 2. 空安全编程
```kotlin
// 安全的空值检查
data.startIconResId.observe(owner) { resId ->
    if (resId != null) {
        setImage(START_ICON, resId)
    }
}
```

### 3. 函数引用
```kotlin
// 使用函数引用实现松耦合
dataInitializer.hideBottomSheet = bottomSheetManager::hide
dataInitializer.createFragment = ::createFragment
```

### 4. 递归防环检测
```kotlin
// 防止菜单列表成环的递归检测
private fun setupMenuList(groups: ObservableMenuListGroupsData, visited: MutableSet<ObservableMenuListGroupsData>) {
    if (visited.contains(groups)) return
    visited.add(groups)
    // ... 递归处理
}
```

## 📊 代码质量

| 维度 | 评分 | 说明 |
|------|------|------|
| 可读性 | 9/10 | 代码简洁，命名清晰 |
| 可维护性 | 8/10 | 职责分离，易于修改 |
| 可测试性 | 7/10 | 依赖注入，便于测试 |
| 性能 | 8/10 | 合理使用 LiveData 和生命周期 |
| 扩展性 | 9/10 | 良好的架构设计 |

## 🛠️ 开发环境

- **Android Studio**：最新版本
- **Kotlin**：1.8.0+
- **Min SDK**：21
- **Target SDK**：34
- **Gradle**：8.0+

## 📚 学习价值

这个项目展示了以下 Android 开发最佳实践：

1. **架构设计**：如何设计清晰的模块化架构
2. **组件化开发**：如何实现组件间的松耦合
3. **数据绑定**：如何使用 LiveData 实现响应式编程
4. **生命周期管理**：如何正确处理 Android 生命周期
5. **代码质量**：如何编写可维护、可测试的代码

## 🤝 贡献指南

1. Fork 项目
2. 创建功能分支
3. 提交更改
4. 推送到分支
5. 创建 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 👨‍💻 作者

- **学习阶段**：Android 开发第3周
- **项目目标**：展示组件化开发最佳实践
- **技术栈**：Kotlin + Android + MVVM

---

*这个项目展示了如何通过良好的架构设计实现复杂的 UI 交互，是学习 Android 组件化开发的优秀示例。*