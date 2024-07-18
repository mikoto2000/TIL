---
title: Tauri の setup 関数内で State の初期化を行う
author: mikoto2000
date: 2024/7/3
---

[前回](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/State) の書き方だと、setup 関数内で求めたもろもろを State に渡せないので、
setup 内で State の初期化をする方法をまとめる。

# 前提

- 開発環境構築は済んでいるものとする
    - See: [mikoto2000/tauri](https://github.com/mikoto2000/docker-images/blob/master/tauri/Dockerfile)
- Tauri:
    - tauri-cli: 1.5.14
    - rust: 1.78.0
    - node: v22.2.0
- [State](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/State) をベースに作業をしたので、そのディレクトリとの差分を取ってください


# 実装

App が `manage` 関数を持っているので、それに渡すだけ。

コード内にコメントでも書いてあるが、
例えば、 userDataDir 内に SQLite3 DB を置いてマイグレートして
コネクションをはって、そのコネクションをステートに入れるとかいう使い方ができる。

■ `src-tauri/src/main.rs`:


```rs
// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use std::sync::{
    atomic::{AtomicUsize, Ordering},
    Arc, Mutex,
};

use tauri::{Manager, State};

// State 用構造体
// AtomicUsize は、既にスレッドセーフなので
// Arc と Mutex で囲む必要はない。
struct Counter {
    count: AtomicUsize,
}

#[tauri::command]
fn inc_count(counter: State<'_, Counter>) -> usize {
    // 最大値のチェックは省略
    counter.count.fetch_add(1, Ordering::Relaxed) + 1
}

#[tauri::command]
fn dec_count(counter: State<'_, Counter>) -> usize {
    if counter.count.load(Ordering::Relaxed) > 0 {
        counter.count.fetch_sub(1, Ordering::Relaxed) - 1
    } else {
        0
    }
}

// けんぱスタック
// `けん` か `ぱ` を要素に持つ配列を記録する。
struct KenPaStack {
    ken_pa: Arc<Mutex<Vec<String>>>,
}

#[tauri::command]
fn put_ken(ken_pa_stack: State<'_, KenPaStack>) {
    ken_pa_stack
        .ken_pa
        .lock()
        .unwrap()
        .append(&mut vec!["けん".to_string()]);
}

#[tauri::command]
fn put_pa(ken_pa_stack: State<'_, KenPaStack>) {
    ken_pa_stack
        .ken_pa
        .lock()
        .unwrap()
        .append(&mut vec!["ぱ".to_string()]);
}

#[tauri::command]
fn get_ken_pa_stack(ken_pa_stack: State<'_, KenPaStack>) -> Vec<String> {
    ken_pa_stack.ken_pa.lock().unwrap().clone()
}

fn main() {
    tauri::Builder::default()
        .setup(|app| {
            // 例えば userDataDir 内に SQLite3 DB を置いてマイグレートして、
            // コネクションをステートに入れるとかいう使い方ができる。

            // State として管理してほしいインスタンスを登録する
            app.manage(Counter {
                count: AtomicUsize::new(0),
            });
            app.manage(KenPaStack {
                ken_pa: Arc::new(Mutex::new(Vec::new())),
            });

            Ok(())
        })
        .invoke_handler(tauri::generate_handler![
            inc_count,
            dec_count,
            put_ken,
            put_pa,
            get_ken_pa_stack
        ])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

■ `src/App.tsx`:

ここは前回と全く同じ。

```tsx
import { useState } from "react";
import "./App.css";
import { invoke } from "@tauri-apps/api";

function App() {

  const [count, setCount] = useState(0);

  const [kenPaStack, setKenPaStack] = useState<Array<string>>([]);

  async function incCount() {
    let count = await invoke<number>('inc_count', {});
    setCount(count);
  }

  async function decCount() {
    let count = await invoke<number>('dec_count', {});
    setCount(count);
  }

  async function putKen() {
    invoke<number>('put_ken', {});
  }

  async function putPa() {
    invoke<number>('put_pa', {});
  }

  async function getKenPaStack() {
    let kenPaStack = await invoke<Array<string>>('get_ken_pa_stack', {});
    setKenPaStack(kenPaStack);
  }

  return (
    <div className="container">
      <h1>Welcome to Tauri!</h1>

      <h2>Counter example:</h2>
      <div>
        <button onClick={decCount}>-</button>
        <input type="text" value={count} readOnly></input>
        <button onClick={incCount}>+</button>
      </div>

      <h2>KEN-PA example:</h2>
        <button onClick={putKen}>けん</button>
        <button onClick={putPa}>ぱ</button>
        <button onClick={getKenPaStack}>けんけんぱできた？</button>
        <div>
          <ul>
          {
            kenPaStack.map((e) => <li>{e}</li>)
          }
          </ul>
        </div>
    </div>
  );
}

export default App;
```

# 動作確認

初期化位置が変わっただけなので挙動に変更なし。OK.

以上。


# 参考資料

- [Tauri の setup 関数内で State の初期化を行う at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/StateInSetup)
- [tauri/examples/state/main.rs at dev · tauri-apps/tauri](https://github.com/tauri-apps/tauri/blob/dev/examples/state/main.rs)
- [AtomicUsize in std::sync::atomic - Rust](https://doc.rust-lang.org/std/sync/atomic/struct.AtomicUsize.html)
- [usize - Rust](https://doc.rust-lang.org/std/primitive.usize.html)
- [Vec in std::vec - Rust](https://doc.rust-lang.org/std/vec/struct.Vec.html)



