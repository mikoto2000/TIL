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
        println!("recommend による監視を行います。");
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
            // ファイルに変更があった場合、この処理が実行される
            Ok(event) => println!("Event: {:?}", event),
            // 何かしらのエラー時はこっち
            Err(error) => println!("Error: {:?}", error),
        }
    }
}
