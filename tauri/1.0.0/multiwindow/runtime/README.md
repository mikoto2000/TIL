---
title: Tauri でマルチウィンドウ(動的生成編)
author: mikoto2000
date: 2024/6/11
---

今回は、マルチウィンドウ定義を動的に作るのをやっていく。


# 前提

- 開発環境構築は済んでいるものとする
    - See: [mikoto2000/tauri](https://github.com/mikoto2000/docker-images/blob/master/tauri/Dockerfile)
- Tauri:
    - tauri-cli: 1.5.14
    - rust: 1.78.0
    - node: v22.2.0

[前回](https://mikoto2000.blogspot.com/2024/06/tauri_26.html)のものに追加する形でやっていくので、差分を見てください。


# 実装

今回は、以下の順に実装していく。

1. 初期ウィンドウ表示を main だけにする
2. フロントエンド実装
    1. `src-tauri/tauri.conf.json` にウィンドウ操作の許可設定を追加
    2. 実装
3. バックエンドエンド実装

フロントエンド実装・バックエンド実装はそれぞれ独立しているので必要などちらか片方だけでも OK。

## 初期ウィンドウ表示を main だけにする

起動時に、メインウィンドウとのみが表示されるように、前回追加したサブの定義を削除。

```json
...(snip)
  "tauri": {
    ...(snip)
    "windows": [
      {
        "label": "main",
        "title": "Main Window",
        "url": "index.html",
        "height": 1000
      },
    ],
...(snip)
```


## `src-tauri/tauri.conf.json` にウィンドウ操作の許可設定を追加

フロントエンドからウィンドウ操作をするには、 `src-tauri/tauri.conf.json` の `allowList` に許可設定を追加する必要がある。

今回は、 `tauri/allowlist/window/create` を `true` にする。

```json
...(snip)
  "tauri": {
    "allowlist": {
      ...(snip)
      "window": {
        "create": true
      }
      ...(snip)
    },
...(snip)
```

これで、フロントエンドからウィンドウの生成を実行できるようになる。


## フロントエンド実装

`src/App.tsx`:

フロントエンドから直接ウィンドウを生成するものと、
コマンド経由でバックエンドに生成を依頼するものの、 2 種類計 4 個のボタンを追加。

`new` してから操作するまでや、エラー取得の流れが独特なので気を付けること。

```tsx
...(snip)
      <button onClick={() => {
        const mainWindow = new WebviewWindow('main', {
          title: 'Main Window',
          url: 'index.html',
        });
        mainWindow.once('tauri://created', () => {
          // ウィンドウ生成後、何かしたい場合はここに記述する
        });

        mainWindow.once('tauri://error', function(e) {
          console.log(e);
        });
      }}>フロントエンドからメインウィンドウを開く</button>
      <button onClick={() => {
        const subWindow = new WebviewWindow('sub', {
          title: 'Sub Window',
          url: 'index.html',
        });
        subWindow.once('tauri://created', () => {
          // ウィンドウ生成後、何かしたい場合はここに記述する
        });

        subWindow.once('tauri://error', function(e) {
          console.log(e);
        });
      }}>フロントエンドからサブウィンドウを開く</button>

      <button onClick={() => {
        invoke('open_main_window', {});
      }}>バックエンドからメインウィンドウを開く</button>
      <button onClick={() => {
        invoke('open_sub_window', {});
      }}>バックエンドからサブウィンドウを開く</button>
...(snip)
```


## バックエンド実装

`src-tauri/src/main.rs`:

フロントエンドから `open_main_window`, `open_sub_window` のコマンドを呼び出すようにしたので、
それらコマンドを実装し、それぞれ対象のウィンドウにイベントを発行するように実装。

```rs
...(snip)
#[tauri::command]
fn open_main_window(app_handle: AppHandle) {
    let result = tauri::WindowBuilder::new(
        &app_handle,
        "main",
        tauri::WindowUrl::App("index.html".into()),
    )
    .build();

    // 既にウィンドウが開いている場合は Err が返ってくるので、match で判定して処理
    match result {
        Ok(main_window) => main_window.set_title("Main Window").unwrap(),
        Err(error) => println!("{:?}", error),
    }
}

#[tauri::command]
fn open_sub_window(app_handle: AppHandle) {
    let result = tauri::WindowBuilder::new(
        &app_handle,
        "sub",
        tauri::WindowUrl::App("index.html".into()),
    )
    .build();

    // 既にウィンドウが開いている場合は Err が返ってくるので、match で判定して処理
    match result {
        Ok(sub_window) => sub_window.set_title("Sub Window").unwrap(),
        Err(error) => println!("{:?}", error),
    }
}
...(snip)
fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![
            open_main_window,
            open_sub_window,
            to_all,
            to_main,
            to_sub
        ])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

# 動作確認

×でどちらかのウィンドウを消したあと、ウィンドウ作成ボタを押下すると、ウィンドウが表示される。
良さそう。

以上。


# 参考資料

- [Tauri でマルチウィンドウ(静的定義編) at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/multiwindow/runtime)
- [Multiwindow | Tauri Apps](https://tauri.app/v1/guides/features/multiwindow)
- [WindowConfig - Configuration | Tauri Apps](https://tauri.app/v1/api/config/#windowconfig)
- [Events | Tauri Apps](https://tauri.app/v1/guides/features/events/)
- [WindowBuilder in tauri::window - Rust](https://docs.rs/tauri/1.6.8/tauri/window/struct.WindowBuilder.html)

