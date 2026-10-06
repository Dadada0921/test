# XunyouLsposed — GitHub 自动构建版

这是针对 `com.xunyou.rb` 的 LSPosed 模块模板。

## 手机端构建

1. 在 GitHub 新建一个空的公开或私有仓库。
2. 将本目录中的所有文件上传到仓库根目录。
3. 打开 GitHub 的 **Actions**。
4. 选择 **Build LSPosed APK**。
5. 点击 **Run workflow**。
6. 构建完成后打开该次 workflow 的运行页面。
7. 在 **Artifacts** 中下载 `XunyouLsposed-debug`。
8. 解压后得到 `app-debug.apk`。

也可以直接向 `main`/`master` 分支 push 文件，workflow 会自动构建。

## 本模块

目标包名：

`com.xunyou.rb`

Hook：

`com.xunyou.rb.MyApplication.onCreate()`

目前只打印日志，不修改目标 App 行为。

## 注意

这是 Debug APK，安装时 Android/LSPosed 可能要求允许安装未知来源应用。
