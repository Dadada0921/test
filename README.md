# XunyouLsposed

针对上传 APK `com.xunyou.rb` 的最小 LSPosed 模块模板。

## 当前功能

Hook:

`com.xunyou.rb.MyApplication.onCreate()`

当前只记录日志，不修改目标 App 的行为。

## 构建

使用 Android Studio 打开本目录。

需要能够解析：

`de.robv.android.xposed:api:82`

然后执行：

```bash
./gradlew :app:assembleDebug
```

APK 通常位于：

`app/build/outputs/apk/debug/app-debug.apk`

## LSPosed 配置

1. 安装生成的 APK。
2. 在 LSPosed 中启用模块。
3. 作用域只勾选 `com.xunyou.rb`。
4. 强制停止目标 App 后重新启动。
5. 查看 LSPosed 日志/Logcat，搜索：

`XunyouLsposed`

## 下一步

这个版本只是确认 Hook 链路正常。

如果你告诉我具体想修改 `com.xunyou.rb` 的什么功能，我可以继续从 APK 中定位对应类/方法，再把 Hook 写进 `MainHook.java`。
