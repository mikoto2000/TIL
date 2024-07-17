---
title: Tauri のプラグインを作る(Hello, World! 編)
author: mikoto2000
date: 2024/7/17
---

# やること

最終目標は「Tauri2.0 の Android インテント送信プラグインを作る」なのだけど、
プラグイン自体がよくわからないので最小構成で作ってみる。

作るものは hello-world プラグイン。

プラグインの各ライフサイクルイベント時に標準出力を出力するのと、
プラグインが提供するコマンド `pluginHello` コマンドを実装する。

# 利用される側(ぷらぐイン)の作成

## プラグインプロジェクトの作成

```sh
cd ${STUDY_ROOT}
npx @tauri-apps/cli plugin init --api -d . -n helloworld
cd tauri-plugin-helloworld
npm i
```

## バックエンドプラグイン実装

`setup`, `on_webview_ready`, `on_page_load` 時に plintln! するのと、
プラグインコマンドとして hello を定義し、
呼ばれたら Rust 側で `Hello, from Plugin!!!" と標準出力する。

`tauri-plugin-helloworld/src/commands.rs`:

```rs
use tauri::command;

// プラグインコマンドの定義
// 普通のコマンドと変わらない
#[command]
pub async fn hello() {
  println!("Hello, from Plugin!!!");
}
```

`tauri-plugin-helloworl/dsrc/libs.rs`:

```rs
mod commands;

use tauri::{
    plugin::{Builder, TauriPlugin},
    Runtime,
};

// プラグインの初期化
// init 関数内で Builder を組み立て、 build した結果を返却する。
pub fn init<R: Runtime>() -> TauriPlugin<R> {
    println!("Initialize helloworld plugin!");
    Builder::new("helloworld")
       .setup(|_app| {
           println!("Setup!");
           Ok(())
       })
       .on_webview_ready(|_window| {
           println!("OnWebViewReady!");
       })
       .on_page_load(|_window, _payload| {
           println!("OnPageLoad!");
       })
      .invoke_handler(tauri::generate_handler![commands::hello])
      .build()
}
```


## フロントエンドプラグイン実装

フロントエンドプラグインを作らないと、先ほど作った `hello` コマンドは

```ts
await invoke('plugin:helloworld|hello')
```

という形で呼び出す必要がある。

これは面倒なのでラッパーを作る。

`tauri-plugin-helloworld/webview-src/index.ts`:

```sh
import { invoke } from '@tauri-apps/api/tauri'

export async function hello() {
  await invoke('plugin:helloworld|hello')
}
```

作成した後、 `npm run build` を実行すると、ビルド結果が `tauri-plugin-helloworl/dwebview-dist` に出力される。


# 利用する側の作成

## プラグインを利用するプロジェクトの作成

作成したプラグインプロジェクトの隣に、プラグインを利用する側のアプリケーションプロジェクトを作成する。

```sh
$ cd ${STUDY_ROOT}
$ npm create tauri-app@latest

> tauri-plugin-helloworld-api@0.0.0 npx
> create-tauri-app

✔ Project name · app
✔ Choose which language to use for your frontend · TypeScript / JavaScript - (pnpm, yarn, npm, bun)
✔ Choose your package manager · npm
✔ Choose your UI template · React - (https://react.dev/)
✔ Choose your UI flavor · TypeScript

Template created! To get started run:
  cd app
  npm install
  npm run tauri dev

$ cd app
$ npm i
$ npm run tauri dev
```

## バックエンドプロジェクトのクレート更新(`Cargo.toml` の更新)

`Cargo.toml` に作成したプラグインプロジェクトを登録する。

`app/src-tauri/Cargo.toml`:

```toml
[package]
name = "app"
version = "0.0.0"
description = "A Tauri App"
authors = ["you"]
edition = "2021"

# See more keys and their definitions at https://doc.rust-lang.org/cargo/reference/manifest.html

[build-dependencies]
tauri-build = { version = "1", features = [] }

[dependencies]
tauri = { version = "1", features = ["shell-open"] }
serde = { version = "1", features = ["derive"] }
serde_json = "1"
tauri-plugin-helloworld = { path = "../../tauri-plugin-helloworld" } # この行を追加(本来は crates.io に公開したものを使う)

[features]
# This feature is used for production builds or when a dev server is not specified, DO NOT REMOVE!!
custom-protocol = ["tauri/custom-protocol"]
```

## プラグインの登録

アプリの `Builder` にプラグインを登録する。

`app/src-tauri/src/main.rs`:

```rs
// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

// Learn more about Tauri commands at https://tauri.app/v1/guides/features/command
#[tauri::command]
fn greet(name: &str) -> String {
    format!("Hello, {}! You've been greeted from Rust!", name)
}

fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![greet])
        .plugin(tauri_plugin_helloworld::init()) // この行を追加でプラグインを有効化
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

## フロントエンドのパッケージ追加(`package.json` の更新)

`dependencies` に `"tauri-plugin-helloworld2-api": "file:../tauri-plugin-helloworld/webview-dist/"` という形で作成したパッケージを追加。

```sh
npm i ../tauri-plugin-helloworkd
```


## フロントエンドからプラグインコマンドを呼び出す

プラグインコマンドを呼び出すために、ボタンひとつだけのアプリに作り替える。

`app/src/App.tsx`:

```ts
import { hello } from "tauri-plugin-helloworld-api";
import "./App.css";

function App() {

  return (
    <div className="container">
      <button onClick={() => hello()}>hello</button>
    </div>
  );
}

export default App;
```

# 動作確認

`Initialize helloworld plugin!`, `Setup!`, `OnWebViewReady!`, `OnPageLoad!` が出力されるし、
ボタンを押すと `Hello, from Plugin!!!` と表示される。

良さそう。以上。


# 参考資料

- [Tauri のプラグインを作る(Hello, World! 編) at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/plugin/firststep)
- [Tauri Plugins | Tauri Apps](https://tauri.app/v1/guides/features/plugin)
- [Plugin Development | Tauri](https://v2.tauri.app/develop/plugins/)
- [plugins-workspace/plugins at v2 · tauri-apps/plugins-workspace](https://github.com/tauri-apps/plugins-workspace/tree/v2/plugins)

