# 快速开始指南

5分钟快速上手Android笔记+待办App！

## 🚀 快速开始（3步）

### 1️⃣ 打开项目
```bash
# 使用Android Studio打开项目目录
File → Open → 选择 android-note-todo-app 文件夹
```

### 2️⃣ 等待同步
- Android Studio会自动下载依赖（首次需要几分钟）
- 看到"Gradle sync finished"即可

### 3️⃣ 运行应用
- 连接Android设备或启动模拟器
- 点击绿色▶️按钮
- 完成！

## 📱 最低要求

- **Android Studio**: Hedgehog (2023.1.1)+
- **Android设备**: Android 7.0 (API 24)+
- **网络**: 首次构建需要下载依赖

## 🎯 核心功能速览

### 笔记
- 📝 创建/编辑笔记
- 🏷️ 添加分类标签
- 🔍 搜索笔记内容

### 待办
- ✅ 勾选完成状态
- 🎨 三级优先级（高/中/低）
- 📅 设置截止日期
- ⚠️ 过期提醒

## 🛠️ 构建APK

### Debug版本（测试用）
```
Build → Build Bundle(s) / APK(s) → Build APK(s)
```
APK位置: `app/build/outputs/apk/debug/app-debug.apk`

### Release版本（发布用）
详见 [BUILD_GUIDE.md](BUILD_GUIDE.md) 的完整说明

## ❓ 遇到问题？

### 同步失败
```bash
File → Invalidate Caches → Invalidate and Restart
```

### 设备未识别
- 检查USB调试是否开启
- 尝试更换USB线或端口

### 编译错误
```bash
Build → Clean Project
Build → Rebuild Project
```

## 📚 更多信息

- 📖 完整构建指南: [BUILD_GUIDE.md](BUILD_GUIDE.md)
- 📋 项目规划: [PROJECT_PLAN.md](PROJECT_PLAN.md)
- 📝 项目说明: [README.md](README.md)

## 🎉 开始使用

项目已包含完整的代码实现，直接运行即可体验所有功能！

---

**提示**: 首次运行会创建数据库，所有数据存储在本地，无需网络连接。
