---
title: Tauri で XDG Base Directory を取得する
author: mikoto2000
date: 2024/7/1
---

この辺りに則っていると考えることが少なくなるので、利用していきましょう。

# 前提

- 開発環境構築は済んでいるものとする
    - See: [mikoto2000/tauri](https://github.com/mikoto2000/docker-images/blob/master/tauri/Dockerfile)
- Tauri:
    - tauri-cli: 1.5.14
    - rust: 1.78.0
    - node: v22.2.0
- [delete-default-event](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/delete-default-event) をベースに作業をしたので、そのディレクトリとの差分を取ってください


# 実装

今回は、以下 3 つの箇所から取得するのを実際に試してみる。

- 初期化時(setup 関数内)
- イベント内
- コマンド処理内

基本的には、 App か AppHandle から PathResolver を取得し、そこから各ディレクトリを取得する。


■ `src-tauri/src/main.rs`:

前述の 3 つのタイミングでディレクトリを取得して表示するように実装。

```rs
// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use tauri::{api::path::*, AppHandle, Manager};

// コマンドでは、引数に自動挿入される AppHandle から pathresolver が取得できる。
#[tauri::command]
fn print_xdg_base_directories(app_handle: AppHandle) {
    println!("==== コマンド内で取得して表示開始 ====");

    // PathResolver の取得
    let path_resolver = app_handle.path_resolver();

    let resource_dir = path_resolver.resource_dir();
    println!("resource_dir: {:?}", resource_dir);

    let resolve_resource = path_resolver.resolve_resource("../assets/logo.svg");
    println!("resolve_resource: {:?}", resolve_resource);

    let app_config_dir = path_resolver.app_config_dir();
    println!("app_config_dir: {:?}", app_config_dir);

    let app_data_dir = path_resolver.app_data_dir();
    println!("app_data_dir: {:?}", app_data_dir);

    let app_local_data_dir = path_resolver.app_local_data_dir();
    println!("app_local_data_dir: {:?}", app_local_data_dir);

    let app_cache_dir = path_resolver.app_cache_dir();
    println!("app_cache_dir: {:?}", app_cache_dir);

    let app_log_dir = path_resolver.app_log_dir();
    println!("app_log_dir: {:?}", app_log_dir);

    println!("==== コマンド内で取得して表示終了 ====");
}

fn main() {
    tauri::Builder::default()
        .setup(|app| {
            // setup 関数では、app から path_resolve を取得すれば OK.
            println!("==== setup 関数内で取得して表示開始 ====");

            // PathResolver の取得
            let path_resolver = app.path_resolver();

            let resource_dir = path_resolver.resource_dir();
            println!("resource_dir: {:?}", resource_dir);

            let resolve_resource = path_resolver.resolve_resource("../assets/logo.svg");
            println!("resolve_resource: {:?}", resolve_resource);

            let app_config_dir = path_resolver.app_config_dir();
            println!("app_config_dir: {:?}", app_config_dir);

            let app_data_dir = path_resolver.app_data_dir();
            println!("app_data_dir: {:?}", app_data_dir);

            let app_local_data_dir = path_resolver.app_local_data_dir();
            println!("app_local_data_dir: {:?}", app_local_data_dir);

            let app_cache_dir = path_resolver.app_cache_dir();
            println!("app_cache_dir: {:?}", app_cache_dir);

            let app_log_dir = path_resolver.app_log_dir();
            println!("app_log_dir: {:?}", app_log_dir);

            // Tauri2 で削除予定
            let app_dir = path_resolver.app_dir();
            println!("app_dir: {:?}", app_dir);

            // Tauri2 で削除予定
            let log_dir = path_resolver.log_dir();
            println!("log_dir: {:?}", log_dir);

            // `tauri::api::path::` での取得
            println!("audio_dir: {:?}", audio_dir());
            println!("cache_dir: {:?}", cache_dir());
            println!("config_dir: {:?}", config_dir());
            println!("data_dir: {:?}", data_dir());
            println!("desktop_dir: {:?}", desktop_dir());
            println!("document_dir: {:?}", document_dir());
            println!("download_dir: {:?}", download_dir());
            println!("executable_dir: {:?}", executable_dir());
            println!("font_dir: {:?}", font_dir());
            println!("home_dir: {:?}", home_dir());
            println!("local_data_dir: {:?}", local_data_dir());
            println!("picture_dir: {:?}", picture_dir());
            println!("public_dir: {:?}", public_dir());
            println!("runtime_dir: {:?}", runtime_dir());
            println!("template_dir: {:?}", template_dir());
            println!("video_dir: {:?}", video_dir());

            println!("==== setup 関数内で取得して表示終了 ====");

            {
                // イベントは、クロージャで path_resolve を受け渡せば取得できる
                let path_resolver = app.path_resolver().clone();
                let _ = app.listen_global("event", move |_| {
                    println!("==== イベント内で取得して表示開始 ====");

                    let resource_dir = path_resolver.resource_dir();
                    println!("resource_dir: {:?}", resource_dir);

                    let resolve_resource = path_resolver.resolve_resource("../assets/logo.svg");
                    println!("resolve_resource: {:?}", resolve_resource);

                    let app_config_dir = path_resolver.app_config_dir();
                    println!("app_config_dir: {:?}", app_config_dir);

                    let app_data_dir = path_resolver.app_data_dir();
                    println!("app_data_dir: {:?}", app_data_dir);

                    let app_local_data_dir = path_resolver.app_local_data_dir();
                    println!("app_local_data_dir: {:?}", app_local_data_dir);

                    let app_cache_dir = path_resolver.app_cache_dir();
                    println!("app_cache_dir: {:?}", app_cache_dir);

                    let app_log_dir = path_resolver.app_log_dir();
                    println!("app_log_dir: {:?}", app_log_dir);

                    // Tauri2 で削除予定
                    let app_dir = path_resolver.app_dir();
                    println!("app_dir: {:?}", app_dir);

                    // Tauri2 で削除予定
                    let log_dir = path_resolver.log_dir();
                    println!("log_dir: {:?}", log_dir);

                    println!("==== イベント内で取得して表示終了 ====");
                });
            }

            Ok(())
        })
        .invoke_handler(tauri::generate_handler![print_xdg_base_directories])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

■ `src/App.tsx`:

単純にコマンド呼び出しとイベント送信をするだけ。

```tsx
import { invoke } from "@tauri-apps/api";
import "./App.css";
import { emit } from "@tauri-apps/api/event";

function App() {

  return (
    <div className="container">
      <h1>Welcome to Tauri!</h1>

      <button onClick={() => {
        invoke('print_xdg_base_directories', {});
      }}>コマンド実行</button>
      <button onClick={() => {
        emit('event', {});
      }}>イベント送信</button>

    </div>
  );
}

export default App;
```

# 動作確認

起動時・コマンド実行時・イベント送信時、それぞれでディレクトリが取得できていることを確認できる。

```
...(snip)
==== コマンド内で取得して表示開始 ====
resource_dir: Some("/workspaces/TIL/tauri/1.0.0/XdgBaseDirectory/tauri-delete-default-event/src-tauri/target/debug")
resolve_resource: Some("/workspaces/TIL/tauri/1.0.0/XdgBaseDirectory/tauri-delete-default-event/src-tauri/target/debug/_up_/assets/logo.svg")
app_config_dir: Some("/home/node/.config/dev.mikoto2000.study.tauri.delete-default-event")
app_data_dir: Some("/home/node/.local/share/dev.mikoto2000.study.tauri.delete-default-event")
app_local_data_dir: Some("/home/node/.local/share/dev.mikoto2000.study.tauri.delete-default-event")
app_cache_dir: Some("/home/node/.cache/dev.mikoto2000.study.tauri.delete-default-event")
app_log_dir: Some("/home/node/.config/dev.mikoto2000.study.tauri.delete-default-event/logs")
==== コマンド内で取得して表示終了 ====
==== イベント内で取得して表示開始 ====
resource_dir: Some("/workspaces/TIL/tauri/1.0.0/XdgBaseDirectory/tauri-delete-default-event/src-tauri/target/debug")
resolve_resource: Some("/workspaces/TIL/tauri/1.0.0/XdgBaseDirectory/tauri-delete-default-event/src-tauri/target/debug/_up_/assets/logo.svg")
app_config_dir: Some("/home/node/.config/dev.mikoto2000.study.tauri.delete-default-event")
app_data_dir: Some("/home/node/.local/share/dev.mikoto2000.study.tauri.delete-default-event")
app_local_data_dir: Some("/home/node/.local/share/dev.mikoto2000.study.tauri.delete-default-event")
app_cache_dir: Some("/home/node/.cache/dev.mikoto2000.study.tauri.delete-default-event")
app_log_dir: Some("/home/node/.config/dev.mikoto2000.study.tauri.delete-default-event/logs")
app_dir: Some("/home/node/.config/dev.mikoto2000.study.tauri.delete-default-event")
log_dir: Some("/home/node/.config/dev.mikoto2000.study.tauri.delete-default-event/logs")
==== イベント内で取得して表示終了 ====
```

良さそう。

以上。


# 参考資料

- [Tauri で XDG Base Directory を取得する at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/XdgBaseDirectory)
- [XDG Base Directory - ArchWiki](https://wiki.archlinux.jp/index.php/XDG_Base_Directory?utm_source=pocket_shared)
- [PathResolver in tauri - Rust](https://docs.rs/tauri/latest/tauri/struct.PathResolver.html)
- [tauri::api::path - Rust](https://docs.rs/tauri/latest/tauri/api/path/index.html)

以上。

