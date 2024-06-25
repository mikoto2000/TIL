---
title: Tauri でマルチウィンドウ(静的定義編)
author: mikoto2000
date: 2024/6/11
---

今回は、マルチウィンドウ定義を静的に設定するのをやっていく。

そして、前回やらなかった個別ウィンドウへ向けたイベント送信をやってみる。

# 前提

- 開発環境構築は済んでいるものとする
    - See: [mikoto2000/tauri](https://github.com/mikoto2000/docker-images/blob/master/tauri/Dockerfile)
- Tauri:
    - tauri-cli: 1.5.14
    - rust: 1.78.0
    - node: v22.2.0


# 実装

実装手順は以下の通り。

1. `src-tauri/tauri.conf.json` にウィンドウ定義を追加
2. Tauri Builder にプラグインを登録

これだけ。

## バックエンドにプラグインへの依存を追加

```json
...(snip)
  "tauri": {
    "windows": [
      {
        "label": "main",
        "title": "Main Window",
        "url": "index.html"
      },
      {
        "label": "sub",
        "title": "Sub Window",
        "url": "index.html",
        "resizeable": false
      }
    ],
...(snip)
```


`src-tauri/Cargo.toml` の `dependencies` セクションに、以下を追加。

```toml
tauri-plugin-window-state = { git = "https://github.com/tauri-apps/plugins-workspace", branch = "v1" }
```


## Tauri Builder にプラグインを登録

`src-tauri/src/main.rs` の `tauri::Builder::default()` に、 Store プラグインを登録する定義を追加。

```rs
fn main() {
    tauri::Builder::default()
        .plugin(tauri_plugin_window_state::Builder::default().build()) # この行を追加
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

これで、前回終了時のウィンドウ配置を復元してくれるようになる。

マニュアルで記録・レストアする機能もあるみたいだが、それは必要になった時に調べることとする。

以上。


# 参考資料

- [Tauri でマルチウィンドウ(静的定義編) at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/multiwindow/static)
- [Multiwindow | Tauri Apps](https://tauri.app/v1/guides/features/multiwindow)
- [WindowConfig - Configuration | Tauri Apps](https://tauri.app/v1/api/config/#windowconfig)

