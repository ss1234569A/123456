# Android 笔记+待办 App

一个简洁高效的离线笔记和待办事项管理应用，采用现代化的Android开发技术栈。

## 功能特性

### 笔记管理
- ✅ 创建、编辑、删除笔记
- ✅ 笔记分类/标签
- ✅ 搜索笔记（标题和内容）
- ✅ 按分类筛选
- ✅ 显示创建和修改时间

### 待办管理
- ✅ 创建、编辑、删除待办事项
- ✅ 勾选完成/未完成
- ✅ 三级优先级（高/中/低）
- ✅ 设置截止日期
- ✅ 过期提醒（高亮显示）
- ✅ 搜索待办
- ✅ 按状态筛选（全部/未完成/已完成）
- ✅ 批量清除已完成

### 通用功能
- ✅ 离线本地存储
- ✅ Material Design 3 设计
- ✅ 深色模式支持
- ✅ 流畅的动画和交互

## 技术栈

- **语言**: Kotlin
- **UI框架**: Jetpack Compose
- **架构**: MVVM + Clean Architecture
- **数据库**: Room
- **依赖注入**: Hilt
- **导航**: Navigation Compose
- **异步处理**: Kotlin Coroutines + Flow
- **最低SDK**: 24 (Android 7.0)
- **目标SDK**: 34 (Android 14)

## 项目结构

```
app/
├── data/                    # 数据层
│   ├── local/              # 本地数据源
│   │   ├── dao/            # Room DAO
│   │   ├── entity/         # 数据库实体
│   │   └── database/       # 数据库配置
│   ├── mapper/             # 数据转换
│   └── repository/         # 仓库实现
├── domain/                 # 领域层
│   ├── model/              # 业务模型
│   └── repository/         # 仓库接口
├── ui/                     # 表现层
│   ├── note/               # 笔记模块
│   │   ├── list/           # 列表页
│   │   └── detail/         # 详情页
│   ├── todo/               # 待办模块
│   │   ├── list/
│   │   └── detail/
│   ├── navigation/         # 导航配置
│   └── theme/              # 主题配置
└── di/                     # 依赖注入
```

## 构建说明

### 环境要求
- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 17
- Android SDK 34
- Gradle 8.0+

### 构建步骤

1. 克隆项目
```bash
git clone <repository-url>
cd android-note-todo-app
```

2. 打开Android Studio
   - File -> Open -> 选择项目目录

3. 同步Gradle
   - Android Studio会自动提示同步，点击"Sync Now"
   - 或手动点击 File -> Sync Project with Gradle Files

4. 运行应用
   - 连接Android设备或启动模拟器
   - 点击运行按钮（绿色三角形）或按 Shift+F10

5. 构建APK
   - Debug版本: Build -> Build Bundle(s) / APK(s) -> Build APK(s)
   - Release版本: Build -> Generate Signed Bundle / APK

## 数据库结构

### Notes表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键（自增） |
| title | String | 标题 |
| content | String | 内容 |
| category | String | 分类 |
| createdAt | Long | 创建时间 |
| updatedAt | Long | 修改时间 |

### Todos表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键（自增） |
| title | String | 标题 |
| description | String | 描述 |
| isCompleted | Boolean | 是否完成 |
| priority | Priority | 优先级 |
| dueDate | Long? | 截止日期 |
| createdAt | Long | 创建时间 |
| updatedAt | Long | 修改时间 |

## 开发指南

### 添加新功能
1. 在对应的层级创建文件
2. 遵循现有的架构模式
3. 使用Hilt进行依赖注入
4. 编写单元测试

### 代码规范
- 遵循Kotlin官方代码风格
- 使用有意义的变量和函数名
- 添加必要的注释
- 保持函数简洁（单一职责）

## 后续计划（v2+）

- [ ] 云同步功能
- [ ] 用户登录
- [ ] 富文本编辑器
- [ ] 图片附件支持
- [ ] 提醒通知
- [ ] 数据导出/导入
- [ ] Widget桌面小部件
- [ ] 分享功能

## 许可证

MIT License

## 联系方式

如有问题或建议，欢迎提Issue。
