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

今回は、以下の順に実装していく。

1. `src-tauri/tauri.conf.json` にウィンドウ定義を追加
2. フロントエンド実装
3. バックエンドエンド実装

フロントエンド実装・バックエンド実装はそれぞれ独立しているので必要などちらか片方だけでも OK。


## `src-tauri/tauri.conf.json` にウィンドウ定義を追加

起動時に、メインウィンドウとサブウィンドウのふたつが表示されるように設定。

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
      {
        "label": "sub",
        "title": "Sub Window",
        "url": "index.html",
        "height": 1000
      }
    ],
...(snip)
```

ここで設定した `label` で、各ウィンドウの判別が行われる。

どちらも `url` を `index.html` としているので、同じ画面を表示する。


## フロントエンド実装

`src/App.tsx`:

初回起動時にイベントを listen.

各種ボタンでそれぞれの対象に対してイベントを発行する。

```tsx
import { useEffect, useState } from "react";
import "./App.css";
import { WebviewWindow, appWindow, getCurrent } from "@tauri-apps/api/window";
import { emit, listen } from "@tauri-apps/api/event";
import { invoke } from "@tauri-apps/api";

function App() {
  const currentWindow = getCurrent();
  const [receivedEvent, setReceivedEvent] = useState<Array<any>>([]);

  let initialized = false;

  {/* useEffect を使って `event` という名前のイベントを購読する */}
  useEffect(() => {
    if (!initialized) {
      let unlisten: any = undefined;
      (async () => {
        unlisten = await listen('event', (e: any) => {
          console.log(e);
          setReceivedEvent((prev) => [...prev, e])
        });
      })();

      initialized = true;

      return () => {
        if (unlisten) {
          unlisten();
        }
      };
    }
  }, []);

  return (
    <div className="container">
      <h1>Welcome to Tauri!</h1>

      {/* getCurrent で、現在の WebviewWindow オブジェクトが取得できる */}
      <p>This window is: `{currentWindow.label}`</p>

      <h2>フロントエンドからのイベント送信</h2>

      <button onClick={() => {
        emit('event', { message: `${currentWindow.label}からのイベント` })
      }}>表示中の全ウィンドウへイベント送信</button>
      <button onClick={() => {
        appWindow.emit('event', { message: `${currentWindow.label}からのイベント` })
      }}>自分自身へイベント送信</button>
      <button onClick={() => {
        const mainWindow = new WebviewWindow('main');
        mainWindow.emit('event', { message: `${currentWindow.label}からのイベント` })
      }}>mainへのイベント送信</button>
      <button onClick={() => {
        const subWindow = new WebviewWindow('sub');
        subWindow.emit('event', { message: `${currentWindow.label}からのイベント` })
      }}>subへのイベント送信</button>

      <h2>バックエンドからのイベント送信</h2>

      <button onClick={() => {
        invoke('to_all', {});
      }}>表示中の全ウィンドウへイベント送信</button>
      <button onClick={() => {
        invoke('to_main', {});
      }}>mainへのイベント送信</button>
      <button onClick={() => {
        invoke('to_sub', {});
      }}>subへのイベント送信</button>

      <ul>
        {receivedEvent.map((e) => <li key={e.id}>{JSON.stringify(e)}</li>)}
      </ul>
    </div>
  );
}

export default App;
```

## バックエンド実装

`src-tauri/src/main.rs`:

フロントエンドから `to_all`, `to_main`, `to_sub` のコマンドを呼び出すようにしたので、
それらコマンドを実装し、それぞれ対象のウィンドウにイベントを発行するように実装。

```rs
// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use serde::Serialize;
use tauri::{AppHandle, Manager};

#[derive(Clone, Serialize)]
struct Payload {
    message:  String,
}

#[tauri::command]
fn to_all(app_handle: AppHandle) {
    app_handle.emit_all("event", Payload { message: "バックエンドからのイベント".to_string() }).unwrap();
}

#[tauri::command]
fn to_main(app_handle: AppHandle) {
    app_handle.emit_to("main", "event", Payload { message: "バックエンドからのイベント".to_string() }).unwrap();
}

#[tauri::command]
fn to_sub(app_handle: AppHandle) {
    app_handle.emit_to("sub", "event", Payload { message: "バックエンドからのイベント".to_string() }).unwrap();
}

fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![
            to_all,
            to_main,
            to_sub])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

# 動作確認

各ボタンをポチポチすると、ウィンドウ下部の受信イベント表示欄に受信したイベントが表示されていく。
良さそう。

![実行した画面](https://github.com/mikoto2000/TIL/assets/345832/f0023660-38cf-472c-8d46-91a5882c4b65)

以上。


# 参考資料

- [Tauri でマルチウィンドウ(静的定義編) at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/multiwindow/static)
- [Multiwindow | Tauri Apps](https://tauri.app/v1/guides/features/multiwindow)
- [WindowConfig - Configuration | Tauri Apps](https://tauri.app/v1/api/config/#windowconfig)
- [Events | Tauri Apps](https://tauri.app/v1/guides/features/events/)

