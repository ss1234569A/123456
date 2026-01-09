# Android 笔记+待办 App 项目规划

## 1. MVP功能清单（按优先级）

### P0 - 核心功能（必须有）
1. **笔记管理**
   - 创建笔记（标题+内容）
   - 编辑笔记
   - 删除笔记
   - 笔记列表展示

2. **待办管理**
   - 创建待办事项
   - 标记完成/未完成
   - 删除待办
   - 待办列表展示

3. **数据持久化**
   - 本地数据库存储
   - 应用重启后数据保留

### P1 - 基础体验（应该有）
4. **搜索功能**
   - 笔记搜索
   - 待办搜索

5. **分类管理**
   - 笔记分类/标签
   - 待办优先级（高/中/低）

6. **时间管理**
   - 创建/修改时间显示
   - 待办截止日期

### P2 - 增强体验（可以有）
7. **UI优化**
   - 深色模式
   - 列表/网格视图切换

8. **数据管理**
   - 批量删除
   - 数据导出（JSON）

## 2. 技术方案

### 2.1 推荐架构
**MVVM + Clean Architecture（简化版）**

```
app/
├── data/              # 数据层
│   ├── local/        # 本地数据源
│   │   ├── dao/      # Room DAO
│   │   ├── entity/   # 数据库实体
│   │   └── database/ # 数据库配置
│   └── repository/   # 仓库实现
├── domain/           # 领域层
│   ├── model/        # 业务模型
│   └── repository/   # 仓库接口
├── ui/               # 表现层
│   ├── note/         # 笔记模块
│   │   ├── list/     # 列表页
│   │   └── detail/   # 详情页
│   ├── todo/         # 待办模块
│   │   ├── list/
│   │   └── detail/
│   ├── common/       # 通用组件
│   └── theme/        # 主题配置
└── di/               # 依赖注入
```

### 2.2 模块划分

**核心模块：**
- **note** - 笔记功能模块
- **todo** - 待办功能模块
- **common** - 通用组件和工具
- **data** - 数据访问层

### 2.3 数据模型设计

#### Note（笔记）实体
```kotlin
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val category: String = "",
    val createdAt: Long,
    val updatedAt: Long
)
```

#### Todo（待办）实体
```kotlin
@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.MEDIUM,
    val dueDate: Long? = null,
    val createdAt: Long,
    val updatedAt: Long
)

enum class Priority {
    LOW, MEDIUM, HIGH
}
```

### 2.4 技术栈选型

- **UI框架**: Jetpack Compose（现代化UI）
- **架构组件**: ViewModel + StateFlow
- **数据库**: Room（官方推荐）
- **导航**: Navigation Compose
- **依赖注入**: Hilt（简化DI）
- **协程**: Kotlin Coroutines（异步处理）
- **日期处理**: kotlinx-datetime

## 3. 里程碑拆解（5-8个可执行任务）

### 任务1: 项目初始化与基础架构搭建
**预计时间**: 1天
**验收标准**:
- ✅ 创建Android项目（Kotlin + Compose）
- ✅ 配置Gradle依赖（Compose/Room/Hilt/Navigation）
- ✅ 搭建基础目录结构
- ✅ 配置Hilt依赖注入
- ✅ 创建数据库基础配置
- ✅ 应用可正常编译运行

### 任务2: 数据层实现
**预计时间**: 1-2天
**验收标准**:
- ✅ 定义Note和Todo实体类
- ✅ 创建NoteDao和TodoDao（CRUD操作）
- ✅ 实现AppDatabase配置
- ✅ 创建Repository接口和实现
- ✅ 编写单元测试验证数据库操作

### 任务3: 笔记列表与详情页面
**预计时间**: 2-3天
**验收标准**:
- ✅ 实现笔记列表UI（Compose）
- ✅ 实现笔记详情/编辑UI
- ✅ 创建NoteViewModel
- ✅ 实现增删改查功能
- ✅ 添加空状态提示
- ✅ 功能测试通过

### 任务4: 待办列表与详情页面
**预计时间**: 2-3天
**验收标准**:
- ✅ 实现待办列表UI（支持勾选完成）
- ✅ 实现待办详情/编辑UI
- ✅ 创建TodoViewModel
- ✅ 实现增删改查和状态切换
- ✅ 添加优先级标识
- ✅ 功能测试通过

### 任务5: 导航与主界面集成
**预计时间**: 1天
**验收标准**:
- ✅ 实现底部导航栏（笔记/待办切换）
- ✅ 配置Navigation路由
- ✅ 实现页面跳转逻辑
- ✅ 添加FloatingActionButton（新建入口）
- ✅ 导航流程测试通过

### 任务6: 搜索功能实现
**预计时间**: 1-2天
**验收标准**:
- ✅ 在笔记列表添加搜索框
- ✅ 在待办列表添加搜索框
- ✅ 实现实时搜索（标题+内容）
- ✅ 添加搜索结果为空提示
- ✅ 搜索性能优化（防抖）

### 任务7: 分类与时间功能
**预计时间**: 1-2天
**验收标准**:
- ✅ 笔记添加分类/标签功能
- ✅ 待办添加截止日期选择器
- ✅ 待办按优先级排序
- ✅ 显示创建/修改时间
- ✅ 过期待办高亮提示

### 任务8: UI优化与测试
**预计时间**: 1-2天
**验收标准**:
- ✅ 实现Material Design 3主题
- ✅ 添加深色模式支持
- ✅ 优化动画和交互体验
- ✅ 完成端到端测试
- ✅ 修复已知bug
- ✅ 性能优化（列表滚动流畅）

## 4. 初始化工程依赖清单

### 4.1 项目配置
- **Kotlin版本**: 1.9.20+
- **Gradle版本**: 8.0+
- **compileSdk**: 34
- **minSdk**: 24（覆盖95%+设备）
- **targetSdk**: 34

### 4.2 核心依赖

#### Jetpack Compose
```gradle
// Compose BOM（统一版本管理）
implementation platform('androidx.compose:compose-bom:2024.01.00')
implementation 'androidx.compose.ui:ui'
implementation 'androidx.compose.ui:ui-tooling-preview'
implementation 'androidx.compose.material3:material3'
implementation 'androidx.activity:activity-compose:1.8.2'
implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0'
implementation 'androidx.lifecycle:lifecycle-runtime-compose:2.7.0'

// Compose调试工具
debugImplementation 'androidx.compose.ui:ui-tooling'
debugImplementation 'androidx.compose.ui:ui-test-manifest'
```

#### Room数据库
```gradle
def room_version = "2.6.1"
implementation "androidx.room:room-runtime:$room_version"
implementation "androidx.room:room-ktx:$room_version"
ksp "androidx.room:room-compiler:$room_version"

// 可选：Room测试
testImplementation "androidx.room:room-testing:$room_version"
```

#### Navigation
```gradle
def nav_version = "2.7.6"
implementation "androidx.navigation:navigation-compose:$nav_version"
```

#### Hilt依赖注入
```gradle
def hilt_version = "2.48"
implementation "com.google.dagger:hilt-android:$hilt_version"
ksp "com.google.dagger:hilt-compiler:$hilt_version"
implementation "androidx.hilt:hilt-navigation-compose:1.1.0"
```

#### 协程
```gradle
def coroutines_version = "1.7.3"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
```

#### 日期时间
```gradle
implementation "org.jetbrains.kotlinx:kotlinx-datetime:0.5.0"
```

#### 测试依赖
```gradle
// 单元测试
testImplementation 'junit:junit:4.13.2'
testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3'
testImplementation 'app.cash.turbine:turbine:1.0.0'

// Android测试
androidTestImplementation 'androidx.test.ext:junit:1.1.5'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
androidTestImplementation platform('androidx.compose:compose-bom:2024.01.00')
androidTestImplementation 'androidx.compose.ui:ui-test-junit4'
```

### 4.3 Gradle插件配置

**项目级 build.gradle**:
```gradle
plugins {
    id 'com.android.application' version '8.2.0' apply false
    id 'org.jetbrains.kotlin.android' version '1.9.20' apply false
    id 'com.google.dagger.hilt.android' version '2.48' apply false
    id 'com.google.devtools.ksp' version '1.9.20-1.0.14' apply false
}
```

**应用级 build.gradle**:
```gradle
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'com.google.dagger.hilt.android'
    id 'com.google.devtools.ksp'
}
```

## 5. 开发建议

### 5.1 开发顺序
1. 先完成数据层（稳定的基础）
2. 再实现UI层（逐个模块）
3. 最后优化体验（搜索、分类等）

### 5.2 代码规范
- 使用Kotlin官方代码风格
- 遵循SOLID原则
- 单一职责：ViewModel只处理UI逻辑
- 依赖倒置：通过接口解耦

### 5.3 测试策略
- 数据层：单元测试（DAO、Repository）
- UI层：集成测试（关键流程）
- 手动测试：完整用户场景

## 6. 后续扩展方向（v2+）

- 云同步功能（Firebase/自建服务器）
- 用户登录与多设备同步
- 富文本编辑器
- 图片/附件支持
- 提醒通知功能
- 数据备份与恢复
- Widget桌面小部件
- 分享功能

---

**项目预计总工期**: 10-15天（全职开发）

**关键成功因素**:
- 保持简单：v1专注核心功能
- 快速迭代：先跑通流程再优化
- 用户体验：流畅的交互和清晰的信息架构
