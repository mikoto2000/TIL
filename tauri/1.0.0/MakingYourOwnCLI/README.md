---
title: Tauri の cli モジュールでコマンドラインオプションをパースする
author: mikoto2000
date: 2024/6/12
---

[Making Your Own CLI | Tauri Apps](https://tauri.app/v1/guides/features/cli) の内容。

# 前提

- 開発環境構築は済んでいるものとする
    - See: [mikoto2000/tauri](https://github.com/mikoto2000/docker-images/blob/master/tauri/Dockerfile)
- Tauri:
    - tauri-cli: 1.5.14
    - rust: 1.78.0
    - node: v22.2.0
- [前回](https://mikoto2000.blogspot.com/2024/06/tauri.html) 作成した [delete-default-event](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/delete-default-event) をベースに作業をしたので、そのディレクトリとの差分を取ってください


# 実装

## オプション(Arguments)定義

Tauri では、 Arguments と表現されているが、これ以降は、 `-o` 等をオプション、それ以外を args と表記する。

`tauri.conf.json` の `tauri/cli/args` に Arguments の定義を追加することでパーサー定義を行う。

`src-tauri/tauri.conf.json`:

```json
{
  ...(snip)
  "tauri": {
    "cli": {
      "description": "コマンドライン引数パース検証プログラム",
      "longDescription": "各型の引数と、サブコマンドを試していきます",
      "beforeHelp": "👺ヘルプの前に表示されるテキスト👺",
      "afterHelp": "👺ヘルプの後に表示されるテキスト👺",
      "args": [
        {
          "name": "option1",
          "short": "o",
          "takesValue": true
        },
        {
          "name": "option2",
          "short": "p",
          "takesValue": true,
          "multiple": true
        },
        {
          "name": "option3",
          "short": "q",
          "takesValue": true,
          "possibleValues": ["are", "kore", "sore"]
        },
        {
          "name": "flagOption",
          "short": "f"
        },
        {
          "name": "flagOptionWithOccurrence",
          "short": "g",
          "multipleOccurrences": true
        },
        {
          "name": "firstArg",
          "index": 1,
          "takesValue": true
        },
        {
          "name": "secondArg",
          "index": 2,
          "takesValue": true
        },
        {
          "name": "lastArgs",
          "index": 3,
          "takesValue": true,
          "multiple": true
        }
      ],
      "subcommands": {
        "subcommand": {
          "description": "コマンドライン引数パース検証プログラムサブコマンド",
          "longDescription": "サブコマンドを試してます",
          "beforeHelp": "👺ヘルプの前に表示されるテキスト👺",
          "afterHelp": "👺ヘルプの後に表示されるテキスト👺",
          "args": [
            {
              "name": "option1",
              "short": "o",
              "takesValue": true
            },
            {
              "name": "option2",
              "short": "p",
              "takesValue": true,
              "multiple": true
            },
            {
              "name": "option3",
              "short": "q",
              "takesValue": true,
              "possibleValues": ["are", "kore", "sore"]
            },
            {
              "name": "flagOption",
              "short": "f"
            },
            {
              "name": "flagOptionWithOccurrence",
              "short": "g",
              "multipleOccurrences": true
            },
            {
              "name": "firstArg",
              "index": 1,
              "takesValue": true
            },
            {
              "name": "secondArg",
              "index": 2,
              "takesValue": true
            },
            {
              "name": "lastArgs",
              "index": 3,
              "takesValue": true,
              "multiple": true
            }
          ]
        }
      }
    },
    ...(snip)
  }
}
```

以下のような意味の設定をしている。

- `option1`: 単純な値付きオプション
- `option2`: 値を複数設定可能なオプション
- `option3`: 設定可能値が決まっているオプション
- `flagOption`: 単純なフラグオプション
- `flagOptionWithOccurrence`: 複数回指定可能なフラグオプション
    - 「`-vvv` で verbose レベル 3 にする」というようなときに使える
- `firstArg`: オプションを除いたひとつ目の引数
- `secondArg`: オプションを除いたふたつ目の引数
- `lastArgs`: オプションを除いたみっつ目以降の引数が格納された配列


## バックエンドの実装

`src-tauri/src/main.rs`:

```diff
--- ../../delete-default-event/tauri-delete-default-event/src-tauri/src/main.rs 2024-06-11 16:38:00.146956138 +0000
+++ ./src-tauri/src/main.rs     2024-06-14 12:59:46.130211061 +0000
@@ -1,8 +1,80 @@
 // Prevents additional console window on Windows in release, DO NOT REMOVE!!
 #![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]
 
+use std::{collections::HashMap, process};
+
+use serde_json::value;
+use tauri::api::cli::ArgData;
+
 fn main() {
     tauri::Builder::default()
+        .setup(|app| {
+            match app.get_cli_matches() {
+                Ok(matches) => {
+
+                    // ヘルプの表示
+                    if let Some(x) = matches.args.get("help").clone() {
+                        println!("{}", x.value.as_str().unwrap());
+                        process::exit(0);
+                    }
+
+                    // バージョンの表示
+                    if let Some(_) = matches.args.get("version").clone() {
+                        let version = app.config().package.version.clone();
+                        println!("{}", version.unwrap());
+                        process::exit(0);
+                    }
+
+                    // ひとまず matches 確認
+                    println!("{:?}", matches);
+
+                    // args と subcommand を取得
+                    let args = matches.args;
+                    let subcommand = matches.subcommand;
+
+                    // サブコマンドがマッチしたらそちらを表示し、
+                    // そうでなければサブコマンド無しの方を表示する
+                    if subcommand.is_none() {
+                        // サブコマンドなし
+                        println!("option1: {}", get_value(&args, "option1").as_str().unwrap());
+                        println!("option2: {:?}", get_value(&args, "option2").as_array().unwrap());
+                        println!("option3: {}", get_value(&args, "option3").as_str().unwrap());
+                        println!("flagOption: {}", get_value(&args, "flagOption").as_bool().unwrap());
+                        println!("flagOptionWithOccurrence: {}", get_value(&args, "flagOptionWithOccurrence").as_bool().unwrap());
+                        println!("firstArg: {}", get_value(&args, "firstArg").as_str().unwrap());
+                        println!("secondArg: {}", get_value(&args, "secondArg").as_str().unwrap());
+                        println!("lastArgs: {:?}", get_value(&args, "lastArgs").as_array().unwrap());
+                    } else {
+                        // サブコマンドアリ
+                        let sb_args = subcommand.unwrap().matches.args;
+                        println!("option1: {}", get_value(&sb_args, "option1").as_str().unwrap());
+                        println!("option2: {:?}", get_value(&sb_args, "option2").as_array().unwrap());
+                        println!("option3: {}", get_value(&sb_args, "option3").as_str().unwrap());
+                        println!("flagOption: {}", get_value(&sb_args, "flagOption").as_bool().unwrap());
+                        println!("flagOptionWithOccurrence: {}", get_value(&sb_args, "flagOptionWithOccurrence").as_bool().unwrap());
+                        println!("firstArg: {}", get_value(&sb_args, "firstArg").as_str().unwrap());
+                        println!("secondArg: {}", get_value(&sb_args, "secondArg").as_str().unwrap());
+                        println!("lastArgs: {:?}", get_value(&sb_args, "lastArgs").as_array().unwrap());
+                    }
+                }
+                Err(err) => {
+                    // エラー時はエラーを表示した終了
+                    println!("{:?}", err);
+                    return Err(Box::new(err));
+                }
+            }
+            Ok(())
+        })
         .run(tauri::generate_context!())
         .expect("error while running tauri application");
 }
+
+// args から value を取得するための関数
+fn get_value(args: &HashMap<String, ArgData>, key: &str) -> value::Value {
+    let option_arg_data = args.get(key);
+    let option_data_wraped = option_arg_data.unwrap();
+    let option_value = &option_data_wraped.value;
+
+    return option_value.clone();
+}
+
```

## フロントエンドの実装

```diff
--- ../../delete-default-event/tauri-delete-default-event/src/App.tsx   2024-06-11 16:41:13.265368722 +0000
+++ ./src/App.tsx       2024-06-13 23:21:46.386133193 +0000
@@ -2,10 +2,24 @@
 import reactLogo from "./assets/react.svg";
 import "./App.css";
 
+import { CliMatches, getMatches } from '@tauri-apps/api/cli'
+
 function App() {
   const [greetMsg, setGreetMsg] = useState("");
   const [name, setName] = useState("");
 
+  const [matches, setMatches] = useState<CliMatches|null>(null);
+
+  getMatches().then((matches) => {
+    setMatches(matches);
+  })
+
+  const args = matches?.args;
+  const lastArgsArray = args?.lastArgs?.value as string[]
+  const subcommand = matches?.subcommand;
+  const subcommandArgs = subcommand?.matches?.args;
+  const subcommandLastArgsArray = subcommandArgs?.lastArgs?.value as string[]
+
   return (
     <div className="container">
       <h1>Welcome to Tauri!</h1>
@@ -22,6 +36,55 @@
         </a>
       </div>
 
+      {!subcommand
+        ?
+        <div>
+          <h1>Option parse result:</h1>
+          <ul>
+            <li>option1: {args?.option1?.value}</li>
+            <li>option2: {JSON.stringify(args?.option2?.value)}</li>
+            <li>option3: {args?.option3?.value}</li>
+            <li>flagOption: {args?.flagOption?.value}
+              and
+              occurences: {args?.flagOption?.occurrences}</li>
+            <li>flagOptionWithOccurrence: {args?.flagOptionWithOccurrence?.value}
+              and
+              occurences: {args?.flagOptionWithOccurrence?.occurrences}</li>
+            <li>firstArg: {args?.firstArg?.value}</li>
+            <li>secondArg: {args?.secondArg?.value}</li>
+            <li>lastArgs: {lastArgsArray ?
+              JSON.stringify(lastArgsArray)
+              :
+              <></>
+            }
+              </li>
+          </ul>
+        </div>
+        :
+        <div>
+          <h1>Subcommand parse result:</h1>
+          <ul>
+            <li>option1: {subcommandArgs?.option1?.value}</li>
+            <li>option2: {JSON.stringify(subcommandArgs?.option2?.value)}</li>
+            <li>option3: {subcommandArgs?.option3?.value}</li>
+            <li>flagOption: {subcommandArgs?.flagOption?.value}
+              and
+              occurences: {subcommandArgs?.flagOption?.occurrences}</li>
+            <li>flagOptionWithOccurrence: {subcommandArgs?.flagOptionWithOccurrence?.value}
+              and
+              occurences: {subcommandArgs?.flagOptionWithOccurrence?.occurrences}</li>
+            <li>firstArg: {subcommandArgs?.firstArg?.value}</li>
+            <li>secondArg: {subcommandArgs?.secondArg?.value}</li>
+            <li>lastArgs: {subcommandLastArgsArray ?
+              JSON.stringify(subcommandLastArgsArray)
+              :
+              <></>
+            }
+              </li>
+          </ul>
+        </div>
+      }
+
       <p>Click on the Tauri, Vite, and React logos to learn more.</p>
 
       <form
```

# ビルド

`npm run tauri dev` にコマンドライン引数を渡すやり方がわからないので、
ビルドして実行する。

```sh
$ npm run tauri build
```

# 動作確認

## 正常系

以下コマンドを実行。

```sh
./src-tauri/target/release/tauri-delete-default-event --option1 aaa -p bbb -q are -f -gg abc def ghi jkl mno
```

フロントエンド:

![正常系フロントエンド画像](https://github.com/mikoto2000/TIL/assets/345832/80cf4028-c303-407f-aa58-9eaad0a5c085)

バックエンド:

```sh
$ ./src-tauri/target/release/tauri-delete-default-event --option1 aaa -p bbb -q are -f -gg abc def ghi jkl mno Matches { args: {"firstArg": ArgData { value: String("abc"), occurrences: 1 }, "flagOptionWithOccurrence": ArgData { value: Bool(true), occurrences: 2 }, "option2": ArgData { value: Array [String("bbb")], occurrences: 1 }, "secondArg": ArgData { value: String("def"), occurrences: 1 }, "option1": ArgData { value: String("aaa"), occurrences: 1 }, "flagOption": ArgData { value: Bool(true), occurrences: 1 }, "option3": ArgData { value: String("are"), occurrences: 1 }, "lastArgs": ArgData { value: Array [String("ghi"), String("jkl"), String("mno")], occurrences: 3 }}, subcommand: None }
option1: aaa
option2: [String("bbb")]
option3: are
flagOption: true
flagOptionWithOccurrence: true
firstArg: abc
secondArg: def
lastArgs: [String("ghi"), String("jkl"), String("mno")]
```

OK そう。


## 異常系

以下コマンドを実行。

```sh
./src-tauri/target/release/tauri-delete-default-event --option1 aaa -p bbb -q invalid -f -gg abc def ghi jkl mno
```

フロントエンド:

エラー時に `process::exit` するので表示されない(一瞬だけ表示されるのを防ぐ方法がわからない...)

バックエンド:

```sh
$ ./src-tauri/target/release/tauri-delete-default-event --option1 aaa -p bbb -q invalid -f -gg abc def ghi jkl mno
FailedToExecuteApi(ParseCliArguments("error: \"invalid\" isn't a valid value for '--option3 <option3>'\n\t[possible values: are, kore, sore]\n\nFor more information try --help\n"))
thread 'main' panicked at src/main.rs:62:6:
error while running tauri application: Setup(SetupError(FailedToExecuteApi(ParseCliArguments("error: \"invalid\" isn't a valid value for '--option3 <option3>'\n\t[possible values: are, kore, sore]\n\nFor more information try --help\n"))))
note: run with `RUST_BACKTRACE=1` environment variable to display a backtrace
```

こちらも OK そう。


## サブコマンド正常系

以下コマンドを実行。

```sh
./src-tauri/target/release/tauri-delete-default-event subcommand --option1 aaa -p bbb -q are -f -gg abc def ghi jkl mno
```

フロントエンド:

![サブコマンド正常系フロントエンド画像](https://github.com/mikoto2000/TIL/assets/345832/c574b913-e8d0-482a-9a5b-34a024bdd50c)

バックエンド:

```sh
node@afd272c7eff1:/workspaces/TIL/tauri/1.0.0/MakingYourOwnCLI/making-your-own-cli$ ./src-tauri/target/release/tauri-delete-default-event subcommand --option1 aaa -p bbb -q are -f -gg abc def ghi jkl mno
Matches { args: {"secondArg": ArgData { value: Bool(false), occurrences: 0 }, "option1": ArgData { value: Bool(false), occurrences: 0 }, "flagOption": ArgData { value: Bool(false), occurrences: 0 }, "option2": ArgData { value: Bool(false), occurrences: 0 }, "firstArg": ArgData { value: Bool(false), occurrences: 0 }, "flagOptionWithOccurrence": ArgData { value: Bool(false), occurrences: 0 }, "option3": ArgData { value: Bool(false), occurrences: 0 }, "lastArgs": ArgData { value: Bool(false), occurrences: 0 }}, subcommand: Some(SubcommandMatches { name: "subcommand", matches: Matches { args: {"option1": ArgData { value: String("aaa"), occurrences: 1 }, "option2": ArgData { value: Array [String("bbb")], occurrences: 1 }, "secondArg": ArgData { value: String("def"), occurrences: 1 }, "lastArgs": ArgData { value: Array [String("ghi"), String("jkl"), String("mno")], occurrences: 3 }, "flagOptionWithOccurrence": ArgData { value: Bool(true), occurrences: 2 }, "option3": ArgData { value: String("are"), occurrences: 1 }, "flagOption": ArgData { value: Bool(true), occurrences: 1 }, "firstArg": ArgData { value: String("abc"), occurrences: 1 }}, subcommand: None } }) }
option1: aaa
option2: [String("bbb")]
option3: are
flagOption: true
flagOptionWithOccurrence: true
firstArg: abc
secondArg: def
lastArgs: [String("ghi"), String("jkl"), String("mno")]
```

これも OK そう。

## サブコマンド異常系

フロントエンド:

エラー時に `process::exit` するので表示されない(一瞬だけ表示されるのを防ぐ方法がわからない...)

バックエンド:

```sh
node@afd272c7eff1:/workspaces/TIL/tauri/1.0.0/MakingYourOwnCLI/making-your-own-cli$ ./src-tauri/target/release/tauri-delete-default-event subcommand --option1 aaa -p bbb -q invalid -f -gg abc def ghi jkl mno
FailedToExecuteApi(ParseCliArguments("error: \"invalid\" isn't a valid value for '--option3 <option3>'\n\t[possible values: are, kore, sore]\n\nFor more information try --help\n"))
thread 'main' panicked at src/main.rs:62:6:
error while running tauri application: Setup(SetupError(FailedToExecuteApi(ParseCliArguments("error: \"invalid\" isn't a valid value for '--option3 <option3>'\n\t[possible values: are, kore, sore]\n\nFor more information try --help\n"))))
note: run with `RUST_BACKTRACE=1` environment variable to display a backtrace
```

最後のこれも OK そう。

以上。


# 参考資料

- [Making Your Own CLI | Tauri Apps](https://tauri.app/v1/guides/features/cli/)
- [CliConfig - Configuration | Tauri Apps](https://tauri.app/v1/api/config/#cliconfig)
- [cli | Tauri Apps](https://tauri.app/v1/api/js/cli)
- フロントエンド
    - [cli | Tauri Apps](https://tauri.app/v1/api/js/cli)
- バックエンド
    - [Matches in tauri::api::cli - Rust](https://docs.rs/tauri/1.6.8/tauri/api/cli/struct.Matches.html)
    - [ArgData in tauri::api::cli - Rust](https://docs.rs/tauri/1.6.8/tauri/api/cli/struct.ArgData.html)
    - [Value in serde_json::value - Rust](https://docs.rs/serde_json/1.0.117/serde_json/value/enum.Value.html)
    - [SubcommandMatches in tauri::api::cli - Rust](https://docs.rs/tauri/1.6.8/tauri/api/cli/struct.SubcommandMatches.html)
- [Tauri アプリの CLI 機能実装 #Windows - Qiita](https://qiita.com/takavfx/items/4743ceaf9fccc87eac52)

