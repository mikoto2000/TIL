---
title: Tauri が生成したプログラムから最小構成まで削除する
author: mikoto2000
date: 2024/6/11
---

いったん、 Tauri の機能紹介で紹介されているものを消していく。

# 前提

- 開発環境構築は済んでいるものとする
    - See: [mikoto2000/tauri](https://github.com/mikoto2000/docker-images/blob/master/tauri/Dockerfile)
- Tauri:
    - tauri-cli: 1.5.14
    - rust: 1.78.0
    - node: v22.2.0


# create-tauri-app を使用したプロジェクト作成

以下内容でプロジェクトを作成する。

- プロジェクト名: tauri-delete-default-event
- パッケージ管理: npm
- UI ライブラリ: React
- フロントエンド言語: TypeScript

```sh
$ # インストール
$ cargo install create-tauri-app --locked

$ # 実行
$ cargo create-tauri-app
✔ Project name · tauri-v1-firststep
✔ Choose which language to use for your frontend · TypeScript / JavaScript - (pnpm, yarn, npm, bun)
✔ Choose your package manager · npm
✔ Choose your UI template · React - (https://react.dev/)
✔ Choose your UI flavor · TypeScript

Template created! To get started run:
  cd tauri-v1-firststep
  npm install
  npm run tauri dev
```

# 生成されたプログラムの Command を削除する

生成されたプログラムは、フロントエンドからバックエンドの Command を呼び出す実装がされている。

ひとまずまっさらな状態から追加できるようにしたいので、この Command を削除する。


`src/App.tsx` の差分:

`@tauri-apps/api/tauri` の `invoke` メソッドで、バックエンドの Command を呼び出しているので、それを削除。

```tsx
diff --git a/tauri/1.0.0/delete-default-event/tauri-delete-default-event/src/App.tsx b/tauri/1.0.0/delete-default-event/tauri-delete-default-event/src/App.tsx
index a449915..19dbdc6 100644
--- a/tauri/1.0.0/delete-default-event/tauri-delete-default-event/src/App.tsx
+++ b/tauri/1.0.0/delete-default-event/tauri-delete-default-event/src/App.tsx
@@ -1,17 +1,11 @@
 import { useState } from "react";
 import reactLogo from "./assets/react.svg";
-import { invoke } from "@tauri-apps/api/tauri";
 import "./App.css";
 
 function App() {
   const [greetMsg, setGreetMsg] = useState("");
   const [name, setName] = useState("");
 
-  async function greet() {
-    // Learn more about Tauri commands at https://tauri.app/v1/guides/features/command
-    setGreetMsg(await invoke("greet", { name }));
-  }
-
   return (
     <div className="container">
       <h1>Welcome to Tauri!</h1>
@@ -34,7 +28,7 @@ function App() {
         className="row"
         onSubmit={(e) => {
           e.preventDefault();
-          greet();
+          setGreetMsg(`Hello, ${name}! You've been greeted from Rust!`);
         }}
       >
         <input
```

`src-tauri/src/main.rs` の差分:

- `#[tauri::command]` を定義した関数を削除
- `tauri::Builder` の `invoke_handler` で前述の関数を登録しているので、その呼び出しを削除

```rs
diff --git a/tauri/1.0.0/delete-default-event/tauri-delete-default-event/src-tauri/src/main.rs b/tauri/1.0.0/delete-default-event/tauri-delete-default-event/src-tauri/src/main.rs
index 523550d..e6ad770 100644
--- a/tauri/1.0.0/delete-default-event/tauri-delete-default-event/src-tauri/src/main.rs
+++ b/tauri/1.0.0/delete-default-event/tauri-delete-default-event/src-tauri/src/main.rs
@@ -1,15 +1,8 @@
 // Prevents additional console window on Windows in release, DO NOT REMOVE!!
 #![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]
 
-// Learn more about Tauri commands at https://tauri.app/v1/guides/features/command
-#[tauri::command]
-fn greet(name: &str) -> String {
-    format!("Hello, {}! You've been greeted from Rust!", name)
-}
-
 fn main() {
     tauri::Builder::default()
-        .invoke_handler(tauri::generate_handler![greet])
         .run(tauri::generate_context!())
         .expect("error while running tauri application");
 }
```

この修正で、「バックエンドはフレームワークを起動するだけ」「フロントエンドは静的な画面を表示するだけ」の状態になったので、これをベースに各機能を試していきたい。

以上。

