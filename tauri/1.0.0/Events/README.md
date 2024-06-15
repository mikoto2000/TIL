---
title: Tauri でフロントエンド<->バックエンドでイベントを送信しあう
author: mikoto2000
date: 2024/6/15
---

今回は、フロントエンドとバックエンドでイベントの送受信を行う。
[Events | Tauri Apps](https://tauri.app/v1/guides/features/events) の内容。

![実行画面](https://github.com/mikoto2000/TIL/assets/345832/acc5ef81-50c5-4ef9-b81c-e7a6fb0b8a66)


# 前提

- 開発環境構築は済んでいるものとする
    - See: [mikoto2000/tauri](https://github.com/mikoto2000/docker-images/blob/master/tauri/Dockerfile)
- Tauri:
    - tauri-cli: 1.5.14
    - rust: 1.78.0
    - node: v22.2.0
- [前々々回](https://mikoto2000.blogspot.com/2024/06/tauri.html) 作成した [delete-default-event](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/delete-default-event) をベースに作業をしたので、そのディレクトリとの差分を取ってください
- ウィンドウについてはまだ知らないので、グローバルイベントのみ作ってみる


# 実装

以下の実装を行う。

1. フロントエンドのボタン押下時に `greet` イベントを送信
2. バックエンドは、 `greet` イベントをリッスン
3. バックエンドは、 `greet` イベントが発火した 2 秒後に、 `greet_ended` イベントを発火する
4. フロントエンドは、 `greet_ended` イベントをリッスン
5. フロントエンドは、 `greet_ended` イベントが発火したら、受信したメッセージを画面に表示する


## フロントエンド

`listen` でイベントを待ち受け、 `emit` でイベントを送信する。

```tsx
...(snip)
import { emit, listen } from '@tauri-apps/api/event'
...(snip)
  const [greetEndedMsg, setGreetEndedMsg] = useState("");

  {/* `greet_ended` イベントをリッスン */}
  listen('greet_ended', (event) => {
    setGreetEndedMsg(JSON.stringify(event));
  })
...(snip)
      <form
        className="row"
        onSubmit={(e) => {
          e.preventDefault();
          setGreetMsg(`Hello, ${name}! You've been greeted from Rust!`);

          {/*ボタン押下で `greet` イベントを emit */}
          emit('greet', { greet_message: name })
        }}
      >
        <input
          id="greet-input"
          onChange={(e) => setName(e.currentTarget.value)}
          placeholder="Enter a name..."
        />
        <button type="submit">Greet</button>
      </form>

      <p>{greetMsg}</p>

      {/* 受信した `greet_ended` イベントのメッセージを表示 */}
      <p>{greetEndedMsg}</p>
...(snip)
```

## バックエンド

フロントエンドから受け取るイベントのペイロードは、「JSON 文字列」。

フロントエンドへ送るイベントのペイロードは、「`serde::Serialize` を付与した構造体」。

こちらもフロントエンドと似たような形で、 `App#listen_global` でグローバルイベントを待ち受け、 `AppHandle#emit_all` でイベントをグローバルに送信する。

```rs
...(snip)
use std::{thread, time::Duration};

use tauri::Manager;

// フロントエンドから受信したメッセージをデシリアライズするための構造体
#[derive(Clone, Debug, serde::Deserialize)]
struct GreetPayload {
  greet_message: String,
}

// フロントエンドへ送信するメッセージをシリアライズするための構造体
#[derive(Clone, Debug, serde::Serialize)]
struct GreetEndedPayload {
  greet_ended_message: String,
}

fn main() {
    tauri::Builder::default()
        .setup(|app| {
            let app_handle = app.handle();

            // イベント `greet` の listen 開始
            // listen を止めたいことがある場合、
            // 戻り値の id を使って `app.unlisten(id)` とする
            let _id = app.listen_global("greet", move |event| {
                let emit_payload_json = event.payload().unwrap();
                println!("EmitPayloadJson: {:?}", emit_payload_json);

                // JSON 文字列で受け取るので、構造体へデシリアライズ
                let emit_payload = serde_json::from_str::<GreetPayload>(&emit_payload_json);
                println!("GreetPayload: {:?}", emit_payload);

                // 構造体からメッセージを抜き出す
                let greet_message = emit_payload.unwrap().greet_message;
                println!("GreetPayload.greet_message: {:?}", greet_message);

                // 2 秒後にイベント `greet_ended` を発火
                thread::sleep(Duration::from_millis(2000));
                let greet_message = format!("greet end by message: {}", greet_message);
                let app_handle = app_handle.clone();
                app_handle.emit_all("greet_ended", GreetEndedPayload {
                    greet_ended_message: greet_message
                }).unwrap();
            });
            Ok(())
        })
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

以上。

# 参考資料

- [Events | Tauri Apps](https://tauri.app/v1/guides/features/events)
- [Tauri でフロントエンド<->バックエンドでイベントを送信しあう at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/Events)

