---
title: Firebase hello world application
author: mikoto2000
date: 2025/7/12
---

# プロジェクト設定

## npm プロジェクト初期化

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

プロジェクトにアプリを追加した直後に以下のコードが表示されるので、それをそのままコピペ。

`index.js`:

```js
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
```
