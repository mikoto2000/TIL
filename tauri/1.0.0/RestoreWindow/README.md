---
title: Tauri で Window 配置の復元をする
author: mikoto2000
date: 2024/6/23
---

今回は、前回プログラム終了時のウィンドウ配置を復元するやつを試す。

[plugins-workspace/plugins/window-state at v1 · tauri-apps/plugins-workspace](https://github.com/tauri-apps/plugins-workspace/tree/v1/plugins/window-state) を導入するだけ。


# 前提

- 開発環境構築は済んでいるものとする
    - See: [mikoto2000/tauri](https://github.com/mikoto2000/docker-images/blob/master/tauri/Dockerfile)
- Tauri:
    - tauri-cli: 1.5.14
    - rust: 1.78.0
    - node: v22.2.0
- [delete-default-event](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/delete-default-event) をベースに作業をしたので、そのディレクトリとの差分を取ってください


# 実装

実装手順は以下の通り。

1. バックエンドにプラグインへの依存を追加
2. Tauri Builder にプラグインを登録

これだけ。

## バックエンドにプラグインへの依存を追加

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

- [Tauri で Window 配置の復元をする at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/RestoreWindow)
- [plugins-workspace/plugins/window-state at v1 · tauri-apps/plugins-workspace](https://github.com/tauri-apps/plugins-workspace/tree/v1/plugins/window-state)
- [Tauri でウィンドウ状態を保存・再現する #Rust - Qiita](https://qiita.com/takavfx/items/531bc89ac402f9cdf6a7)

