---
title: Tauri 2.0 のウィンドウにメニューを追加する
author: mikoto2000
date: 2024/07/22
---

# やること

Tauri 2.0 での Menu の追加方法がわかったのでやってみる。


# 前提

Windows 上の WSL2 上の Ubuntu 上のコンテナで実行。

- OS: Windows 11 Pro 23H2 ビルド 22631.4037
- WSL2: Ubuntu 24.04
- Docker: docker-ce 27.1.2
- tauri2 と  Android ビルドの環境が構築済みであること。
    - See: [docker-images/tauri2/Dockerfile at master · mikoto2000/docker-images](https://github.com/mikoto2000/docker-images/blob/master/tauri2/Dockerfile)
    - rustup: 1.27.1
    - cargo: 1.79.0
    - rustc: 1.79.0

# プロジェクトのひな形を作成

```sh
$ npm create tauri-app@latest -- --rc
Need to install the following packages:
create-tauri-app@4.3.0
Ok to proceed? (y) y


> npx
> create-tauri-app --rc

✔ Project name · menu-firststep
✔ Identifier · com.menu-firststep.app
✔ Choose which language to use for your frontend · TypeScript / JavaScript - (pnpm, yarn, npm, bun)
✔ Choose your package manager · npm
✔ Choose your UI template · React - (https://react.dev/)
✔ Choose your UI flavor · TypeScript

Template created! To get started run:
  cd menu-firststep
  npm install
  npm run tauri android init

For Desktop development, run:
  npm run tauri dev

For Android development, run:
  npm run tauri android dev
```

# ひな形の動作確認

```sh
cd menu-firststep
npm i
npm run tauri dev
```


# 実装

書きたいことは全部コメントに書いたので、コードを貼るだけ。

```rust
use tauri::menu::{MenuBuilder, MenuId, MenuItemKind};
use tauri_plugin_shell::ShellExt;

// Learn more about Tauri commands at https://tauri.app/v1/guides/features/command
#[tauri::command]
fn greet(name: &str) -> String {
    format!("Hello, {}! You've been greeted from Rust!", name)
}

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
        .setup(|app| {
            // メニュー組み立て開始

            // ビルダーを作って
            let menu = MenuBuilder::new(app)
                // テキストメニューアイテムを追加
                // 第一引数は ID, 第二引数がラベル
                .text("open-url", "Open URL")
                // チェックボックスメニューアイテムを追加
                // 第一引数は ID, 第二引数がラベル
                .check("toggle", "Toggle")
                // アイコンメニューアイテム追加
                // 第一引数は ID, 第二引数がラベル, 第三引数がアイコン
                .icon(
                    "show-app",
                    "Show App",
                    app.default_window_icon().cloned().unwrap(),
                )
                // ビルドしてメニュー完成
                .build()?;

            // app にメニューをセット
            app.set_menu(menu.clone())?;

            // 各メニューがクリックされたとき、
            // on_menu_event で定義した処理が走る。
            app.on_menu_event(move |app, event| {
                // イベントからメニューアイテム ID を取得して
                let MenuId(menu_id) = event.id();

                // メニューアイテム ID に応じた処理を実行する
                if menu_id == "open-url" {
                    println!("Received open-url event.");

                    // デフォルトブラウザで URL を開く
                    app.shell()
                        .open("https://github.com/mikoto2000/TIL", None)
                        .unwrap();
                } else if menu_id == "toggle" {
                    println!("Received toggle event.");

                    // トグルはこんな感じで CheckMenuItem が取得できるので、
                    // それを使って ON/OFF を判別できる
                    let option_menu_item_kind = menu.get("toggle");
                    if let Some(MenuItemKind::Check(check)) = option_menu_item_kind {
                        println!("Check is {:?}.", check.is_checked());
                    } else {
                        panic!("???");
                    }
                } else if menu_id == "show-app" {
                    println!("Received show-app event.");
                } else {
                    println!("Unknown event.");
                }
                println!("{:?}", event);
            });

            Ok(())
        })
        .plugin(tauri_plugin_shell::init())
        .invoke_handler(tauri::generate_handler![greet])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```


# 動作確認

```sh
npm run tauri dev
```

- 各メニューを押下すると標準出力にどれが押されたかが表示される
- `Open URL` を押下すると、デフォルトブラウザで `mikoto2000/TIL` のページが表示される(コンテナ上だと失敗するので、これだけ Windows で試した)
- `Toggle` を押下すると、切り替え後の ON/OFF が true/false で表示される

OK, よさそう。

以上。

# 参考資料

- [What is Tauri? | Tauri](https://v2.tauri.app/start/)
- [Migrate to Menu Module - Upgrade from Tauri 1.0 | Tauri](https://v2.tauri.app/start/migrate/from-tauri-1/#migrate-to-menu-module)
- [Menu Events - Upgrade from Tauri 1.0 | Tauri](https://v2.tauri.app/start/migrate/from-tauri-1/#menu-events)
- [MenuItemKind in tauri::menu - Rust](https://docs.rs/tauri/2.0.0-rc/tauri/menu/enum.MenuItemKind.html#)
- [Tauri 2.0 のウィンドウにメニューを追加する](https://github.com/mikoto2000/TIL/tree/master/tauri/2.0.0-beta/Menu/firststep)

