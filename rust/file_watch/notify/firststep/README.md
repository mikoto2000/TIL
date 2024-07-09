---
title: notify クレートを使って Rust でファイル監視する
author: mikoto2000
date: 2024/7/9
---

# やること

ファイルの内容変更を監視し、ファイル変更があったら何かしらを行うヤツを作る。


# 前提

- OS: Windows 11 Pro 23H2 ビルド 22631.3737
- Docker Desktop: Version 4.31.1 (153621)
- rust の環境が構築済みであること。
    - See: [docker-images/rust/Dockerfile at master · mikoto2000/docker-images](https://github.com/mikoto2000/docker-images/blob/master/rust/Dockerfile)
    - rustup: 1.27.0
    - cargo: 1.78.0
    - rustc: 1.78.0


# プロジェクト作成

```sh
cargo init
cargo add notify
```

# 実装

いくつか u8 配列を用意して、エンディアン指定で抜き出してみた。

```rs
// 対象のファイルを監視し、ファイルに変更が行われたらイベントを標準出力へ出力します。
// 監視方法の recommend は、Linux なら大抵 inotify, Windows は監視用の Win32 API を利用するらしい。
//
// 第一引数: 監視対象ファイル
// 第二引数: 監視方法(recommend or polling)
use std::{env, path::Path, sync::mpsc, time::Duration};

use notify::{Config, PollWatcher, RecursiveMode, Watcher};

fn main() {
    // 引数パース
    let args: Vec<String> = env::args().collect();
    let is_polling = if args[2] == "recommend" {
        false
    } else if args[2] == "polling" {
        true
    } else {
        panic!("第二引数に未知の監視方法が入力されました。 recommend か polling を入力してください。");
    };
    let path = Path::new(&args[1]).as_ref();
    println!("Start watch: {:?}", path);

    // 監視イベントを通知するためのチャンネルを作る。
    // Watcher がファイルの更新を検知したら、 tx へ書き込むので、
    // 通知を受け取りたい側は rx を読む。
    let (tx, rx) = mpsc::channel();

    // watcher の作成
    let _watcher: Box<dyn Watcher> = if is_polling {
        // ポーリングでファイル監視する
        // ポーリングタイミングは `with_poll_interval` で指定した期間だが、
        // アプリへのイベント発火は「変更が行われたとき」となる。
        println!("ポーリングで 1 秒間隔に監視を行います。");
        let config = Config::default().with_poll_interval(Duration::from_secs(1));
        let mut poll_watcher = PollWatcher::new(tx, config).unwrap();
        poll_watcher
            .watch(path, RecursiveMode::NonRecursive)
            .unwrap();
        Box::new(poll_watcher)
    } else {
        println!("inotify による監視を行います。");
        let mut recommended_watcher = notify::recommended_watcher(move |event| {
            let _ = tx.send(event);
        })
        .unwrap();
        recommended_watcher
            .watch(path, RecursiveMode::NonRecursive)
            .unwrap();
        Box::new(recommended_watcher)
    };

    // ファイルに変更が行われると、rx チャンネルにイベントが来るので、
    // それを読み込んで表示する。大抵は別スレッドでやることになるだろう。
    while let Ok(res) = rx.recv() {
        match res {
            Ok(event) => println!("Event: {:?}", event),
            Err(error) => println!("Error: {:?}", error),
        }
    }
}
```


# 動作確認

```sh
$ cargo run -- /workspaces/TIL/rust/file_wtach/notify/firststep/README.md recommend
    Finished `dev` profile [unoptimized + debuginfo] target(s) in 0.00s
     Running `target/debug/firststep /workspaces/TIL/rust/file_wtach/notify/firststep/README.md recommend`
Start watch: "/workspaces/TIL/rust/file_wtach/notify/firststep/README.md"
recommend による監視を行います。
Event: Event { kind: Modify(Data(Any)), paths: ["/workspaces/TIL/rust/file_wtach/notify/firststep/README.md"], attr:tracker: None, attr:flag: None, attr:info: None, attr:source: None }
Event: Event { kind: Modify(Data(Any)), paths: ["/workspaces/TIL/rust/file_wtach/notify/firststep/README.md"], attr:tracker: None, attr:flag: None, attr:info: None, attr:source: None }
Event: Event { kind: Modify(Metadata(Any)), paths: ["/workspaces/TIL/rust/file_wtach/notify/firststep/README.md"], attr:tracker: None, attr:flag: None, attr:info: None, attr:source: None }
Event: Event { kind: Access(Close(Write)), paths: ["/workspaces/TIL/rust/file_wtach/notify/firststep/README.md"], attr:tracker: None, attr:flag: None, attr:info: None, attr:source: None }
```

色々と情報を持った Event が渡されれてくるので、それを基に処理が書ける。

以上。


# 参考資料

- [notify クレートを使って Rust でファイル監視する](https://github.com/mikoto2000/TIL/tree/master/rust/file_watch/notify/firststep)

