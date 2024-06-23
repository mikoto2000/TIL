---
title: Tauri での Store を使ってデータの永続化を行う
author: mikoto2000
date: 2024/6/23
---

今回は、設定やキャッシュなどに使える、データの永続化を行う。

[plugins-workspace/plugins/store at v1 · tauri-apps/plugins-workspace](https://github.com/tauri-apps/plugins-workspace/tree/v1/plugins/store) を利用すると簡単に実現できる。


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
2. フロントエンドにプラグインへの依存を追加
3. Tauri Builder にプラグインを登録
4. Store を使って永続化


## バックエンドにプラグインへの依存を追加

`src-tauri/Cargo.toml` の `dependencies` セクションに、以下を追加。

```toml
tauri-plugin-store = { git = "https://github.com/tauri-apps/plugins-workspace", branch = "v1" }
```


## フロントエンドにプラグインへの依存を追加

プロジェクトルートで以下を実行。今回は `npm` を利用。

```sh
npm add https://github.com/tauri-apps/tauri-plugin-store#v1
```


## Tauri Builder にプラグインを登録

`src-tauri/src/main.rs` の `tauri::Builder::default()` に、 Store プラグインを登録する定義を追加。

```rs
fn main() {
    tauri::Builder::default()
        .plugin(tauri_plugin_store::Builder::default().build()) # この行を追加
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

Store を使うための事前準備はこれだけ。

後は、使うための実装をしていく。


## Store を使って永続化

テキストフィールドの値をストアに保存し、次回起動時に復元するようにしてみる。

また、バックエンドは前回起動日時をストアに記録し、起動時に標準出力へ表示する。

### フロントエンド

```tsx
import { useEffect, useState } from "react";
import reactLogo from "./assets/react.svg";
import "./App.css";

import { Store } from "tauri-plugin-store-api";

function App() {
  const [greetMsg, setGreetMsg] = useState("");
  const [name, setName] = useState("");

  useEffect(() => {
    (async () => {
      // ストアから情報を抽出して存在するなら表示
      const store = new Store("greet.json");
      const name = await store.get("name");
      setName(name?.value);
    })();

  }, []);

  return (
    <div className="container">
      <h1>Welcome to Tauri!</h1>

      <div className="row">
        <a href="https://vitejs.dev" target="_blank">
          <img src="/vite.svg" className="logo vite" alt="Vite logo" />
        </a>
        <a href="https://tauri.app" target="_blank">
          <img src="/tauri.svg" className="logo tauri" alt="Tauri logo" />
        </a>
        <a href="https://reactjs.org" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>

      <p>Click on the Tauri, Vite, and React logos to learn more.</p>

      <form
        className="row"
        onSubmit={(e) => {
          e.preventDefault();
          setGreetMsg(`Hello, ${name}! You've been greeted from Rust!`);
        }}
      >
        <input
          id="greet-input"
          onChange={ async (e) => {
            setName(e.currentTarget.value)

            // ストアに保存
            const store = new Store("greet.json");
            await store.set("name", { value: e.currentTarget.value });
          }}
          placeholder="Enter a name..."
          value={name}
        />
        <button type="submit">Greet</button>
      </form>

      <p>{greetMsg}</p>
    </div>
  );
}

export default App;
```

- `new Store("greet.json")` で、アプリローカルディレクトリ(~/.local/share/<アプリのidentify>) に `greet.json` という名前のストアを作成
- `store.set(<キー>, <バリュー>)` で、キーに対してバリューを保存
- `store.save()` でファイルへ書き出し
- `store.get(<キー>)` で保存したバリューを取得


### バックエンド

```rs
// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use std::time::SystemTime;

use serde_json::json;
use tauri_plugin_store::StoreBuilder;

fn main() {
    tauri::Builder::default()
        .plugin(tauri_plugin_store::Builder::default().build())
        .setup(|app| {
            // ストア情報をロード
            let mut store = StoreBuilder::new(app.handle(), "greet_backend.json".parse()?).build();
            let _ = store.load();

            // store のキー `launch` から前回起動時刻を取得し、表示
            let launch = store.get("launch");
            println!("previous launch: {:?}", launch);

            // 現在時刻をストアに保存
            let now = SystemTime::now();
            let _ = store.insert("launch".to_string(), json!(now));
            let _ = store.save();

            Ok(())
        })
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

詳細は `setup` 内のコメントを参照してください。

初回起動時は空・None が表示され、閉じて開きなおすと前回入力した値や、前回の起動時刻が表示される。

うん、できていそう。以上。


# 参考資料

- [Tauri での Store を使ってデータの永続化を行う at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/Store)
- [Tauri を使ってみる](https://zenn.dev/wtshm/scraps/8d1f26e22cdf3a)
- [plugins-workspace/plugins/store at v1 · tauri-apps/plugins-workspace](https://github.com/tauri-apps/plugins-workspace/tree/v1/plugins/store)
- [plugins-workspace/plugins/store/examples/AppSettingsManager/src-tauri/src/main.rs at v1 · tauri-apps/plugins-workspace](https://github.com/tauri-apps/plugins-workspace/blob/v1/plugins/store/examples/AppSettingsManager/src-tauri/src/main.rs)
- [plugins-workspace/plugins/store/examples/AppSettingsManager/src-tauri/src/app/settings.rs at v1 · tauri-apps/plugins-workspace](https://github.com/tauri-apps/plugins-workspace/blob/v1/plugins/store/examples/AppSettingsManager/src-tauri/src/app/settings.rs)

