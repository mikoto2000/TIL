---
title: Tauri 2.0 の Android プラグインの Example を動かす
author: mikoto2000
date: 2024/07/22
---

# やること

Tauri 2.0 の Android プラグインを作りたくなったので、まずは Example を動かすところからやってみる。


# 前提

- OS: Windows 11 Pro 23H2 ビルド 22631.3880
- Docker Desktop: Version 4.32.0 (157355)
- tauri2 と  Android ビルドの環境が構築済みであること。
    - See: [docker-images/tauri2/Dockerfile at master · mikoto2000/docker-images](https://github.com/mikoto2000/docker-images/blob/master/tauri2/Dockerfile)
    - rustup: 1.27.1
    - cargo: 1.79.0
    - rustc: 1.79.0
- Windows 上に Android Studio がインストール済みであり、 `adb` コマンドが使えること

# プラグインのひな形プロジェクト(Example) を作成

```sh
$ npx @tauri-apps/cli@next plugin new --android example
✔ What should be the Android Package ID for your plugin? · com.plugin.example
```

# api プロジェクトのビルド

これで、フロントエンドから `import { ping } from "tauri-plugin-example";` して `ping(...)` できるようになる。

```sh
cd tauri-plugin-example/
npm i
npm run build
```

# Example アプリのビルド

「プラグインプロジェクト単体でビルド」という概念は無いので、 Example プロジェクトのビルドを行う。

```sh
cd example/tauri-app
npm i
```

`@tauri-apps/cli@2.0.0-beta.22` 時点では `package.json` の生成に~~バグがあり(node or npm のバージョンかも？)~~
推奨環境を利用していないため、`npm error Unsupported URL Type "link:": link:../../` と言われてしまうため、パスを修正する。

```sh
sed -i -e 's/link://' ./package.json
```

そして Android プロジェクトのビルド。

`identifier` が `com.tauri.dev`, `version` が `0.0.0`  のままだとビルドができないのでこれを書き換えてビルド。

```
sed -i -e 's/com\.tauri\.dev/dev.mikoto2000.study.android.plugin.example/' ./src-tauri/tauri.conf.json
sed -i -e 's/0\.0\.0/0.0.1/' ./src-tauri/tauri.conf.json
npm run tauri android init
npm run tauri android build -- --target aarch64 -d
```

これで、 `src-tauri/gen/android/app/build/outputs/apk/universal/debug/app-universal-debug.apk` に apk ファイルができるので、 Android 端末かエミュレーターか WSA にインストールする。


# 動作確認

自分の環境では物理端末が一番手っ取り早かったのでそれにインストール。

```sh
adb.exe install \\wsl.localhost\Ubuntu-24.04\home\mikoto\project\TIL\tauri\2.0.0-beta\plugin\android\firststep\tauri-plugin-example\examples\tauri-app\src-tauri\gen\android\app\build\outputs\apk\universal\debug\app-universal-debug.apk
```

![tauri2 0-plugin-example_001](https://github.com/user-attachments/assets/4900c093-8199-4f24-a51b-61f4a77369d5)

Ping したら Pong が返ってくる。良さそう。

自分のプロジェクトに組み込みたい場合には、この Example プロジェクトの真似をして `package.json` と `Cargo.toml` にプラグインプロジェクトを登録すれば OK.


# 変更履歴

| 日付 | 変更内容 |
|------|----------|
| 2024/7/22-1 | 新規作成 |
| 2024/7/22-2 | 「バグがあり」という記載が間違のようなので記載修正。 |

