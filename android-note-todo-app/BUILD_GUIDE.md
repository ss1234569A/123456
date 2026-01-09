# Android Studio 构建指南

本文档详细说明如何在Android Studio中构建和运行此项目。

## 前置要求

### 必需软件
1. **Android Studio Hedgehog (2023.1.1)** 或更高版本
   - 下载地址: https://developer.android.com/studio
   
2. **JDK 17**
   - Android Studio通常自带，也可以单独安装
   - 验证: 在终端运行 `java -version`

3. **Android SDK**
   - SDK Platform 34 (Android 14)
   - SDK Build-Tools 34.0.0
   - Android SDK Platform-Tools
   - Android SDK Command-line Tools

### 推荐配置
- 内存: 至少8GB RAM（推荐16GB）
- 硬盘: 至少10GB可用空间
- 操作系统: Windows 10/11, macOS 10.14+, 或 Linux

## 步骤1: 导入项目

### 方法A: 从文件系统打开
1. 启动Android Studio
2. 选择 **File → Open**
3. 导航到 `android-note-todo-app` 目录
4. 点击 **OK**

### 方法B: 从欢迎界面打开
1. 启动Android Studio
2. 在欢迎界面点击 **Open**
3. 选择项目目录
4. 点击 **OK**

## 步骤2: Gradle同步

项目打开后，Android Studio会自动开始Gradle同步。

### 如果自动同步失败
1. 点击顶部的 **File → Sync Project with Gradle Files**
2. 或点击工具栏的 🔄 图标

### 常见同步问题

#### 问题1: Gradle版本不兼容
```
解决方案:
1. 打开 gradle/wrapper/gradle-wrapper.properties
2. 确认 distributionUrl 指向 Gradle 8.0+
3. 重新同步
```

#### 问题2: SDK未安装
```
解决方案:
1. 打开 Tools → SDK Manager
2. 在 SDK Platforms 标签页，勾选 Android 14.0 (API 34)
3. 在 SDK Tools 标签页，确保已安装:
   - Android SDK Build-Tools 34
   - Android SDK Platform-Tools
   - Android SDK Command-line Tools
4. 点击 Apply 下载安装
```

#### 问题3: 网络问题（国内用户）
```
解决方案:
在项目根目录的 build.gradle.kts 中添加国内镜像:

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/google") }
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    google()
    mavenCentral()
}
```

## 步骤3: 配置运行设备

### 选项A: 使用真实设备
1. 在Android设备上启用开发者选项:
   - 设置 → 关于手机 → 连续点击"版本号"7次
2. 启用USB调试:
   - 设置 → 开发者选项 → USB调试
3. 用USB线连接设备到电脑
4. 在设备上允许USB调试授权

### 选项B: 使用模拟器
1. 点击 **Tools → Device Manager**
2. 点击 **Create Device**
3. 选择设备型号（推荐: Pixel 6）
4. 选择系统镜像（推荐: API 34, Android 14）
5. 点击 **Finish**
6. 启动模拟器

## 步骤4: 运行应用

### Debug模式运行
1. 确保设备/模拟器已连接
2. 在工具栏选择目标设备
3. 点击绿色的 ▶️ 运行按钮
4. 或按快捷键 **Shift + F10** (Windows/Linux) 或 **Control + R** (macOS)

### 查看日志
1. 打开底部的 **Logcat** 标签页
2. 选择应用包名: `com.example.notetodo`
3. 查看运行时日志

## 步骤5: 构建APK

### Debug APK（用于测试）
1. 选择 **Build → Build Bundle(s) / APK(s) → Build APK(s)**
2. 等待构建完成
3. 点击通知中的 **locate** 查看APK位置
4. APK路径: `app/build/outputs/apk/debug/app-debug.apk`

### Release APK（用于发布）

#### 5.1 创建签名密钥
```bash
# 在项目根目录执行
keytool -genkey -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-key-alias
```

按提示输入:
- 密钥库密码
- 个人信息
- 密钥密码

#### 5.2 配置签名
在 `app/build.gradle.kts` 中添加:

```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("../my-release-key.jks")
            storePassword = "your-keystore-password"
            keyAlias = "my-key-alias"
            keyPassword = "your-key-password"
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            // ... 其他配置
        }
    }
}
```

#### 5.3 构建Release APK
1. 选择 **Build → Generate Signed Bundle / APK**
2. 选择 **APK**，点击 **Next**
3. 选择密钥库文件，输入密码
4. 选择 **release** 构建类型
5. 点击 **Finish**
6. APK路径: `app/build/outputs/apk/release/app-release.apk`

## 步骤6: 安装APK到设备

### 方法A: 通过ADB安装
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### 方法B: 直接传输
1. 将APK文件传输到Android设备
2. 在设备上打开文件管理器
3. 点击APK文件
4. 允许安装未知来源应用（如需要）
5. 点击安装

## 常见问题排查

### 问题1: 编译错误 "Unresolved reference"
```
原因: Gradle同步未完成或失败
解决: File → Invalidate Caches → Invalidate and Restart
```

### 问题2: "Manifest merger failed"
```
原因: AndroidManifest.xml配置冲突
解决: 检查 app/src/main/AndroidManifest.xml 是否正确
```

### 问题3: Room数据库错误
```
原因: 数据库schema变更
解决: 
1. 卸载应用
2. 重新安装
或在代码中添加: .fallbackToDestructiveMigration()
```

### 问题4: Hilt依赖注入错误
```
原因: 缺少@HiltAndroidApp注解
解决: 确认Application类有@HiltAndroidApp注解
```

### 问题5: Compose预览不显示
```
解决:
1. 确保函数有@Preview注解
2. Build → Clean Project
3. Build → Rebuild Project
```

## 性能优化建议

### 加快构建速度
在 `gradle.properties` 中添加:
```properties
org.gradle.jvmargs=-Xmx4096m -XX:MaxMetaspaceSize=1024m
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.configuration-cache=true
```

### 减小APK体积
在 `app/build.gradle.kts` 中:
```kotlin
android {
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
```

## 调试技巧

### 1. 使用断点调试
- 在代码行号左侧点击设置断点
- 点击 🐞 Debug按钮运行
- 使用调试工具栏控制执行流程

### 2. 查看数据库内容
```bash
# 连接到设备
adb shell

# 进入应用数据目录
cd /data/data/com.example.notetodo/databases/

# 使用sqlite3查看
sqlite3 note_todo_database
.tables
SELECT * FROM notes;
```

### 3. 使用Database Inspector
1. 运行应用
2. 打开 **View → Tool Windows → App Inspection**
3. 选择 **Database Inspector** 标签页
4. 查看实时数据库内容

## 下一步

构建成功后，您可以:
1. 在设备上测试所有功能
2. 根据需求修改代码
3. 添加新功能
4. 发布到应用商店

## 获取帮助

如遇到问题:
1. 查看 Android Studio 的 Build 输出
2. 检查 Logcat 日志
3. 参考项目的 README.md
4. 查阅 Android 官方文档: https://developer.android.com

祝您构建顺利！
