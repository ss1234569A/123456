# APK构建指南

由于项目需要完整的Gradle Wrapper环境，推荐使用以下方法构建APK：

## 方法1: 使用Android Studio（最简单）

### 步骤：
1. 用Android Studio打开项目目录 `android-note-todo-app`
2. 等待Gradle自动同步完成（首次需要下载依赖）
3. 点击菜单：`Build → Build Bundle(s) / APK(s) → Build APK(s)`
4. 等待构建完成
5. 点击通知中的"locate"查看APK

**APK位置**: `app/build/outputs/apk/debug/app-debug.apk`

## 方法2: 初始化Gradle Wrapper

如果您想使用命令行构建，需要先初始化Gradle Wrapper：

### Windows系统：
```cmd
cd android-note-todo-app

# 如果已安装Gradle，使用以下命令初始化wrapper
gradle wrapper --gradle-version 8.2

# 然后构建APK
gradlew.bat assembleDebug
```

### Linux/Mac系统：
```bash
cd android-note-todo-app

# 如果已安装Gradle，使用以下命令初始化wrapper
gradle wrapper --gradle-version 8.2

# 赋予执行权限
chmod +x gradlew

# 然后构建APK
./gradlew assembleDebug
```

## 方法3: 使用Android Studio命令行工具

如果您安装了Android Studio，可以使用其自带的Gradle：

### Windows:
```cmd
cd android-note-todo-app
"C:\Program Files\Android\Android Studio\jbr\bin\java.exe" -jar "C:\Program Files\Android\Android Studio\plugins\gradle\lib\gradle-launcher-8.2.jar" assembleDebug
```

### 或者使用Android Studio的Terminal:
1. 在Android Studio中打开项目
2. 打开底部的Terminal标签
3. 运行: `./gradlew assembleDebug` (Linux/Mac) 或 `gradlew.bat assembleDebug` (Windows)

## 构建输出

构建成功后，APK文件位置：
- **Debug APK**: `app/build/outputs/apk/debug/app-debug.apk`
- **Release APK**: `app/build/outputs/apk/release/app-release.apk`

## 常见问题

### Q: 提示"gradlew: command not found"
**A**: Gradle Wrapper未初始化，请使用方法1（Android Studio）或先运行 `gradle wrapper`

### Q: 提示"JAVA_HOME not set"
**A**: 需要安装JDK 17并设置环境变量：
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%

# Linux/Mac
export JAVA_HOME=/path/to/jdk-17
export PATH=$JAVA_HOME/bin:$PATH
```

### Q: 构建失败，提示SDK未找到
**A**: 需要设置Android SDK路径，在项目根目录创建 `local.properties`:
```properties
sdk.dir=C\:\\Users\\YourName\\AppData\\Local\\Android\\Sdk
# 或 Mac/Linux
sdk.dir=/Users/YourName/Library/Android/sdk
```

## 推荐方式

**强烈推荐使用方法1（Android Studio）**，因为：
- ✅ 自动处理所有依赖
- ✅ 自动配置SDK路径
- ✅ 提供可视化构建进度
- ✅ 更容易排查错误
- ✅ 一键完成所有配置

## 安装APK

构建完成后，将APK传输到Android设备：
```bash
# 使用ADB安装
adb install app/build/outputs/apk/debug/app-debug.apk

# 或直接将APK文件复制到手机，点击安装
```

---

**提示**: 首次构建需要下载大量依赖（约500MB-1GB），请确保网络连接稳定。
