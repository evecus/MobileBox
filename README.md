# MobileBox

📱 安卓手机版 TVBox — 白色现代界面，支持点播+直播

## 功能特性

- **点播**：支持主流订阅接口（TVBox JSON格式），内容网格浏览、搜索、收藏、历史
- **直播**：支持 M3U/TXT 直播源，频道分组、EPG节目单、多线路切换
- **播放器**：IjkPlayer（软解/硬解）+ ExoPlayer（HLS/DASH/RTSP）
- **界面**：纯白背景 Material Design 3，手机触控优化，底部 Tab 导航
- **投屏**：支持 DLNA 投屏

## 界面说明

| 页面 | 说明 |
|------|------|
| 首页 | 分类 Tab + 3列影视卡片网格 |
| 搜索 | 历史记录 + 热门搜索 |
| 搜索结果 | 左侧来源过滤 + 右侧结果列表 |
| 播放 | 顶部播放器（可全屏）+ 选集/线路 |
| 直播 | 顶部播放器 + 频道分组列表 |
| 我的 | 收藏、历史、订阅配置、播放器设置 |

## 编译

### 本地编译

```bash
./gradlew assembleArm64Release     # arm64-v8a
./gradlew assembleArmeabiRelease   # armeabi-v7a
```

### GitHub Actions 自动构建

推送 tag 自动构建并发布 Release：

```bash
git tag v1.0.0
git push origin v1.0.0
```

也可在 GitHub → Actions → Build & Release → Run workflow 手动触发。

## 配置

1. 安装 APK 后进入「我的」
2. 「订阅接口」→ 填入 TVBox API JSON 地址
3. 「直播订阅」→ 填入 M3U 或 TXT 直播源地址
4. 「播放器」→ 选择 IjkPlayer / ExoPlayer / 系统播放器
5. 「解码方式」→ 软解（兼容性好）/ 硬解（性能好）

## 致谢

基于 [takagen99/Box](https://github.com/takagen99/Box) 和 [q215613905/TVBoxOS](https://github.com/q215613905/TVBoxOS) 的核心逻辑重构。
