---
title: Firestore firststep
author: mikoto2000
date: 2025/7/13
---

# Firebase の設定

## プロジェクト作成

1. Firebase のコンソールからプロジェクトを追加
    - `プロジェクトを使ってみる` 押下
2. プロジェクト名入力
    - firestore-firststep
3. Firebase プロジェクトの AI アシスタンス
    - 無効
4.  Google アナリティクス （Firebase プロジェクト向け）
    - 無効
5. `続行` 押下


## プロジェクトの設定

### Firestore の構築

1. 左のリストから `構築` を選択
2. `Firestore Database` を選択
3. `データベースの作成` を選択
4. `ロケーション` を選択
5. `テストモードで開始する` を選択
    - Web ではこれ一択らしい


### アプリの追加

1. `アプリに Firebase を追加して利用を開始しましょう` から `Web` を選択
2. `アプリのニックネーム` 入力
3. `アプリを登録` を押下

Firebase 初期化用コードが出力されるのでメモしておく。

```js
// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyCtgjntK9RJ266HtARxgqynO3UFZl3SwZE",
  authDomain: "firestore-firststep.firebaseapp.com",
  projectId: "firestore-firststep",
  storageBucket: "firestore-firststep.firebasestorage.app",
  messagingSenderId: "422794914716",
  appId: "1:422794914716:web:02cc22a4b84c9ce9ff7659"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
```

# Vite プロジェクト設定

## Vite プロジェクト初期化

Vanilla で TypeScript なプロジェクトを作る。

```sh
npm create vite@latest firestore-firststep
cd firestore-firststep
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

## Firestore 操作

### Firestore に collection を作成し、ドキュメントを追加。


#### 実装

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
    <div>
      <div>
        <label>タイトル: <input type="text" id="document-title"></input></label>
      </div>
      <div>
        <label>詳細<textarea id="document-detail"></textarea></label>
      </div>
      <div>
        <button id="document-create-button">Document 作成</button>
      </div>
    </div>
    <script type="module" src="/src/main.ts"></script>
  </body>
</html>
```

`src/main.ts`:

```ts
import { initializeApp } from "firebase/app";
import { addDoc, collection, getFirestore } from "firebase/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyCtgjntK9RJ266HtARxgqynO3UFZl3SwZE",
  authDomain: "firestore-firststep.firebaseapp.com",
  projectId: "firestore-firststep",
  storageBucket: "firestore-firststep.firebasestorage.app",
  messagingSenderId: "422794914716",
  appId: "1:422794914716:web:02cc22a4b84c9ce9ff7659"
};

const app = initializeApp(firebaseConfig);

// DB 取得
const db = getFirestore(app)

// コレクション定義(この時点でコレクションが存在していなくてもよい)
const testCollection = collection(db, 'test');

async function addDocument() {
  const title = (document.getElementById('document-title') as HTMLInputElement | null)?.value;
  const detail = (document.getElementById('document-detail') as HTMLTextAreaElement | null)?.value;
  await addDoc(testCollection, {
    title, detail
  });
}

const createButton = document.getElementById('document-create-button');
if (createButton) {
  createButton.addEventListener('click', addDocument);
} else {
  console.error('`#document-create-button` not found.');
}
```

タイトル,詳細を入力して `Document 作成` ボタンを押すと、Firestore の `test` コレクションにドキュメントが追加される。

Firebase コンソールの `Firestore Database` から `test` コレクションを選択し、ドキュメントが追加されていることが確認できる。


## 作成したドキュメントを取得して表示

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
    <div>
      <h2>ドキュメント追加</h2>
      <div>
        <label>タイトル: <input type="text" id="document-title"></input></label>
      </div>
      <div>
        <label>詳細<textarea id="document-detail"></textarea></label>
      </div>
      <div>
        <button id="document-create-button">Document 作成</button>
      </div>
    </div>
    <div id="documents">
      <h2>fetch したドキュメント</h2>
    </div>
    <template id="doc-template">
      <div>
        <div>
          <label>id: <span class="document-id"></span></label>
        </div>
        <div>
          <label>タイトル: <span class="document-title"></span></label>
        </div>
        <div>
          <label>詳細 <span class="document-detail"></span></label>
        </div>
      </div>
    </template>
    <script type="module" src="/src/main.ts"></script>
  </body>
</html>
```

`src/main.ts`:

```ts
import { initializeApp } from "firebase/app";
import { addDoc, collection, getDocs, getFirestore } from "firebase/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyCtgjntK9RJ266HtARxgqynO3UFZl3SwZE",
  authDomain: "firestore-firststep.firebaseapp.com",
  projectId: "firestore-firststep",
  storageBucket: "firestore-firststep.firebasestorage.app",
  messagingSenderId: "422794914716",
  appId: "1:422794914716:web:02cc22a4b84c9ce9ff7659"
};

const app = initializeApp(firebaseConfig);

// DB 取得
const db = getFirestore(app)

// コレクション定義(この時点でコレクションが存在していなくてもよい)
const testCollection = collection(db, 'test');

async function addDocument() {
  const title = (document.getElementById('document-title') as HTMLInputElement | null)?.value;
  const detail = (document.getElementById('document-detail') as HTMLTextAreaElement | null)?.value;
  await addDoc(testCollection, {
    title, detail
  });
}

async function fetchDocument() {
  const docs = await getDocs(testCollection);
  const documentsElm = document.getElementById('documents');
  if (documentsElm) {
    docs.forEach((doc) => {

      // テンプレ取得
      const template = document.getElementById('doc-template') as HTMLTemplateElement | null;
      if (!template) {
        console.error('#doc-template not found.');
        return
      }

      // 追加するために複製
      const appendNode = template.content.cloneNode(true) as DocumentFragment;

      // 複製したノードに値を注入
      const data: any = doc.data();
      const documentIdElm = appendNode.querySelector('.document-id');
      if (documentIdElm) {
        documentIdElm.textContent = doc.id;
      }
      const documentTitleElm = appendNode.querySelector('.document-title');
      if (documentTitleElm) {
        documentTitleElm.textContent = data.title;
      }
      const documentDetailElm = appendNode.querySelector('.document-detail');
      if (documentDetailElm) {
        documentDetailElm.textContent = data.detail;
      }

      documentsElm.append(appendNode);
    });
  }
}

// ドキュメント作成ボタン定義
const createButton = document.getElementById('document-create-button');
if (createButton) {
  createButton.addEventListener('click', addDocument);
} else {
  console.error('`#document-create-button` not found.');
}

fetchDocument();
```

以上。


# 参考資料

- 

