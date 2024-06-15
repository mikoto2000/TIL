---
title: Tauri のフロントエンド(TypeScript)からバックエンド(Rust)を呼び出す
author: mikoto2000
date: 2024/6/15
---

今回は、フロントエンド(TypeScript)からバックエンド(Rust)を呼び出すための仕組みである、 Command の使い方を勉強する。

![実行画面](https://github.com/mikoto2000/TIL/assets/345832/9a58dcde-6849-4e7d-91ff-523b07ab02f8)

# 前提

- 開発環境構築は済んでいるものとする
    - See: [mikoto2000/tauri](https://github.com/mikoto2000/docker-images/blob/master/tauri/Dockerfile)
- Tauri:
    - tauri-cli: 1.5.14
    - rust: 1.78.0
    - node: v22.2.0
- [前々回](https://mikoto2000.blogspot.com/2024/06/tauri.html) 作成した [delete-default-event](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/delete-default-event) をベースに作業をしたので、そのディレクトリとの差分を取ってください


# 実装 その1 - 引数・戻り値の無いコマンド

## コマンドを実装(バックエンド)

### コマンドの実体である関数を定義(バックエンド)

`src-tauri/src/main.rs`:

```rs
...(snip)
#[tauri::command]
fn implemented_command_function() {
    println!("Called implementedCommandFunction!!!!!");
}
...(snip)
```

戻り値のないコマンドを定義した。やるべきことは以下 2 つ。

1. コマンド処理関数の実装
2. `#[tauri::command]` アノテーションの付与

これでコマンド定義の完了。


### コマンドをアプリに登録(バックエンド)

定義したコマンドを、アプリが使えるようにするため、登録処理を行う。

`src-tauri/src/main.rs`:

```rs
...(snip)
fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![implemented_command_function])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

`invoke_handler` の行を追加。

これで、アプリがコマンドを呼び出す準備ができた。


## コマンドを呼び出す(フロントエンド)

`src/App.tsx`:

```ts
...(snip)
import { invoke } from '@tauri-apps/api/tauri'
...(snip)
  function callImplementedCommandFunction() {
    invoke('implemented_command_function');
  }
...(snip)
      <div>
        <label>
          Call implemented_command_function command:
          <button
            onClick={() => {callImplementedCommandFunction()}}>
            call
          </button>
        </label>
      </div>
...(snip)
```

`invoke` でコマンドを呼び出せる。

ボタンを追加し、イベントハンドラを登録し、そこから `invoke` で `implemented_command_function` を呼び出すようにした。


## 動作確認 その1 - 引数・戻り値の無いコマンド

今回は、コマンドライン引数が絡まないので、dev コマンドで開発用サーバーを立ち上げ、動作確認を行う。

```sh
$ npm run tauri dev

> tauri-delete-default-event@0.0.0 tauri
> tauri dev

     Running BeforeDevCommand (`npm run dev`)

> tauri-delete-default-event@0.0.0 dev
> vite


  VITE v5.2.13  ready in 103 ms

  ➜  Local:   http://localhost:1420/
  ➜  Network: use --host to expose
        Info Watching /workspaces/TIL/tauri/1.0.0/CallingRustFromTheFrontend/tauri-delete-default-event/src-tauri for changes...
    Finished `dev` profile [unoptimized + debuginfo] target(s) in 0.11s
```

![実行画面](https://github.com/mikoto2000/TIL/assets/345832/65f5df0d-ba62-40e1-88ae-0271192a2926)

`call` ボタンを押下すると、以下メッセージがターミナルに表示される。

```sh
Called implemented_command_function!!!!!
```

OK っぽい。


# 実装 その2 - 引数・戻り値のあるコマンド

## コマンドを実装(バックエンド)

引数・戻り値がある以外は「その1」と同様。

`src-tauri/src/main.rs`:

```rs
...(snip)
#[tauri::command]
fn sum(x:i64, y: i64) -> i64 {
    return x + y;
}
...(snip)
fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![
            implemented_command_function,
            sum])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

これで、アプリがコマンドを呼び出す準備ができた。


## コマンドを呼び出す(フロントエンド)

以下 3 点を理解しつつ追加していく。

1. 引数は、バックエンドで定義した仮引数名をパラメーターとして持つ JSON オブジェクトとして定義し、 `invoke` へ渡す
2. フロントエンドで使う仮引数名は、「ローワーキャメルケース」で定義する(Tauri が変換処理を行う)
3. `invoke` の戻り値は、 `then` の引数として受け取る。

`src/App.tsx`:

```ts
  const [sumResult, setSumResult] = useState("");
...(snip)
  function callSum() {
    invoke('sum', {xForSum: 1, yForSum: 2})
      .then((result) => setSumResult(result));
  }
...(snip)
      <div>
        <label>
          Execute 1 + 2: {sumResult}
          <button
            onClick={() => {callSum()}}>
            call
          </button>
          <button
            onClick={() => setSumResult("")}>
            Clear
          </button>
        </label>
      </div>
...(snip)
```


## 動作確認 その2 - 引数・戻り値のあるコマンド

今回追加した `call` ボタンを押下すると、Rust で計算された結果が表示される。


# 実装 その3 - 非同期コマンド

## コマンドを実装(バックエンド)

関数に `async` 修飾子が付く以外は「その1」と同様。

今回は簡便にカウンタを用意するために `static mut` なグローバル変数と `unsafe` を使って実装。

`src-tauri/src/main.rs`:

```rs
...(snip)
static mut CALL_NUMBER : u64 = 0;
#[tauri::command]
async fn async_command() -> String {
    println!("Called asyncCommand!!!!!");
    let call_number = unsafe { CALL_NUMBER }
    ;
    unsafe { CALL_NUMBER += 1 }
    thread::sleep(Duration::from_millis(2000));
    return format!("Called asyncCommand {}.", call_number)
}
...(snip)
fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![
            implemented_command_function,
            sum,
            async_command])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

これで、アプリがコマンドを呼び出す準備ができた。


## コマンドを呼び出す(フロントエンド)

普通の戻り値ありのものと同じように呼び出せば OK.

`src/App.tsx`:

```ts
  const [asyncCommandResult, setAsyncCommandResult] = useState([]);
...(snip)
  function callAsyncCommand() {
    invoke('async_command')
      .then((result) => {
        setAsyncCommandResult((x) => [...x, result])
      })
  }
...(snip)
      <div>
        <label>
          Execute asyncCommand:
          <button
            onClick={() => {callAsyncCommand()}}>
            call
          </button>
          <button
            onClick={() => setAsyncCommandResult([])}>
            Clear
          </button>
        </label>
        <ol>
          {
            asyncCommandResult.map((e) => <li>{e}</li>)
          }
        </ol>
      </div>
...(snip)
```


## 動作確認 その3 - 非同期コマンド

今回追加した `call` ボタンを押下すると、2 秒後にリストに追加される。
`async` によりメインとは別スレッドで処理が行われるため、 UI がブロックされない事も確認できる。


# 実装 その4 - エラーの可能性のあるコマンド

## コマンドを実装(バックエンド)

引数が `Result` になる以外は「その1」と同様。

`src-tauri/src/main.rs`:

```rs
...(snip)
#[tauri::command]
fn success_or_failed(success: bool) -> Result<String, String> {
    println!("Called successOrFailed!!!!!");
    if success {
        Ok("Success!".into())
    } else {
        Err("Failed!".into())
    }
}
...(snip)
fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![
            implemented_command_function,
            sum,
            async_command,
            success_or_failed])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

これで、アプリがコマンドを呼び出す準備ができた。


## コマンドを呼び出す(フロントエンド)

よくある API と同じように、 `then` と `catch` で処理を分けて記述する。

`src/App.tsx`:

```ts
  const [successOrFailedMessage, setSuccessOrFailedMessage] = useState("");
...(snip)
  function callSuccessOrFailed(success) {
    invoke('success_or_failed', {success: success})
      .then((result) => {
        setSuccessOrFailedMessage(result)
      })
      .catch((error) => {
        setSuccessOrFailedMessage(error)
      })
  }
...(snip)
      <div>
        <label>
          Execute successOrFailed:
          <button
            onClick={() => {callSuccessOrFailed(true)}}>
            call success
          </button>
          <button
            onClick={() => {callSuccessOrFailed(false)}}>
            call failed
          </button>
          <button
            onClick={() => setSuccessOrFailedMessage("")}>
            Clear
          </button>
        </label>
        <p>{successOrFailedMessage}</p>
      </div>
...(snip)
```


## 動作確認 その4 - エラーの可能性のあるコマンド

バックエンド側で `Ok` が返却されると `then` の処理が、
`Err` が返却されると `catch` の処理が実行されることが確認できる。


他にも Window や AppHandle, State などがあるようだが、
そもそもそれらが何なのか今は分からないためここまでとする。

以上。


# 参考資料

- [Calling Rust from the frontend | Tauri Apps](https://tauri.app/v1/guides/features/command)
- [Tauri のフロントエンド(TypeScript)からバックエンド(Rust)を呼び出す at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/CallingRustFromTheFrontend)

