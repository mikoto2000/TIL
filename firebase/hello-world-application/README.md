---
title: Firebase hello world application
author: mikoto2000
date: 2025/7/12
---

# Firebase の設定

メモし忘れもうした....

# Vite プロジェクト設定

## Vite プロジェクト初期化

Vanilla で TypeScript なプロジェクトを作る。

```sh
npm create vite@latest hello-world-application
cd hello-world-application
```

## 必要パッケージインストール

```sh
npm i firebase
```

# 実装

## Firebase プロジェクト用アプリの初期化

### 実装

プロジェクトにアプリを追加した直後に以下のコードが表示されるので、それをそのままコピペ。

その後ろに `#app` に `app` を `JSON.stringify` したテキストを貼り付けるようにしてみる。

`src/main.ts`:

```ts
// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyBN246f4FotZ0iwlidNxFWPakVsaGFMgBM",
  authDomain: "hello-world-project-cd5b9.firebaseapp.com",
  projectId: "hello-world-project-cd5b9",
  storageBucket: "hello-world-project-cd5b9.firebasestorage.app",
  messagingSenderId: "1028786778",
  appId: "1:1028786778:web:5c52b662014062a9632663"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

const appElm = document.getElementById('app');
if (appElm) {
  appElm.innerHTML = JSON.stringify(app);
}
```

### 動作確認

```sh
npm run dev
```

`http://localhost:5173` にアクセスすると、以下のような JSON 文字列が表示される。よさそう。

```
{"_isDeleted":false,"_options":{"apiKey":"AIzaSyBN246f4FotZ0iwlidNxFWPakVsaGFMgBM","authDomain":"hello-world-project-cd5b9.firebaseapp.com","projectId":"hello-world-project-cd5b9","storageBucket":"hello-world-project-cd5b9.firebasestorage.app","messagingSenderId":"1028786778","appId":"1:1028786778:web:5c52b662014062a9632663"},"_config":{"name":"[DEFAULT]","automaticDataCollectionEnabled":true},"_name":"[DEFAULT]","_automaticDataCollectionEnabled":true,"_container":{"name":"[DEFAULT]","providers":{}}}
```

## Google ログイン

### 実装

`index.html`:

```html
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <link rel="icon" type="image/svg+xml" href="/vite.svg" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Vite + TS</title>
  </head>
  <body>
    <div id="uid"></div>
    <div id="app"></div>
    <script type="module" src="/src/main.ts"></script>
  </body>
</html>
```

`src/main.ts`:

```ts
import { initializeApp } from "firebase/app";
import {
  getAuth,
  signInWithPopup,
  GoogleAuthProvider,
  type User,
} from "firebase/auth";

const firebaseConfig = {
  apiKey: "AIzaSyBN246f4FotZ0iwlidNxFWPakVsaGFMgBM",
  authDomain: "hello-world-project-cd5b9.firebaseapp.com",
  projectId: "hello-world-project-cd5b9",
  storageBucket: "hello-world-project-cd5b9.firebasestorage.app",
  messagingSenderId: "1028786778",
  appId: "1:1028786778:web:5c52b662014062a9632663"
};

const app = initializeApp(firebaseConfig);

// app の情報を #app へ出力
const appElm = document.getElementById('app');
if (appElm) {
  appElm.innerHTML = JSON.stringify(app);
}

const auth = getAuth(app);

let currentUser: User | null = null;

async function login() {
  const provider = new GoogleAuthProvider();
  const result = await signInWithPopup(auth, provider);
  currentUser = result.user;
}

// ログイン処理実行
await login();

// uid の情報を #uid へ出力
const uidElm = document.getElementById('uid');
if (uidElm) {
  uidElm.innerHTML = JSON.stringify(currentUser);
}

```

リロードすると、 Google ログインのポップアップが表示され、ログイン後、画面にユーザー情報を `JSON.stringify` した文字列が表示される。良さそう。

ひとまず Hello, World! としてはここまで。やりすぎたかも。

以上。


# 参考資料

- 
