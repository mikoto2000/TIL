---
title: Tauri でドラッグアンドドロップでファイルを受け取る
author: mikoto2000
date: 2024/6/28
---

今回は、ドラッグアンドドロップでファイルを受け取るのをやっていく。

コンテナだと動作確認が無理なので、 Windows に環境を入れて実施した。


# 前提

- OS: Windows 11 Pro 23H2 ビルド 22631.3737
- Tauri:
    - rustup: 1.27.1
    - rust: 1.79.0
    - node: v20.11.1
    - tauri-cli: 10.2.4


# プロジェクト作成

```sh
> cargo install create-tauri-app --locked
> cargo create-tauri-app
✔ Project name · draganddrop
✔ Choose which language to use for your frontend · TypeScript / JavaScript - (pnpm, yarn, npm, bun)
✔ Choose your package manager · npm
✔ Choose your UI template · React - (https://react.dev/)
✔ Choose your UI flavor · TypeScript

Template created! To get started run:
  cd draganddrop
  npm install
  npm run tauri dev
```


# 実装

今回は、さくっとコードを載せるだけ。コメントを見れば大体わかるでしょう。

編集対象はフロントエンドのみ。

`src/App.tsx`:

```tsx
import { useEffect, useState } from "react";
import "./App.css";
import { appWindow } from "@tauri-apps/api/window";

function App() {

  const [dropFiles, setDropFiles] = useState<string[]>([]);

  useEffect(() => {
    let unlisten: any = null;
    if (!unlisten) {
      (async () => {
        // ファイルのドロップを購読
        unlisten = await appWindow.onFileDropEvent((event) => {
          if (event.payload.type === 'hover') {
            console.log('User hovering', event);
          } else if (event.payload.type === 'drop') {
            console.log('User dropped', event);
            setDropFiles(event.payload.paths);
          } else {
            console.log('File drop cancelled');
          }
        });
      })();
    }

    return () => {
      if (unlisten) {
        unlisten();
      }
    }
  })

  return (
    <div className="container">
      <h1>Welcome to Tauri!</h1>

      <h2>Drop files:</h2>
      <ul>
        { /* ドラッグアンドドロップで受け取ったファイルのファイルパスを表示 */
          dropFiles.map((filePath) => <li key={filePath}>{filePath}</li>)
        }
      </ul>

    </div>
  );
}

export default App;
```


# 動作確認

`npm run tauri dev` で実行し、ファイルをドロップすると、
画面下部にドラッグされたファイルのファイルパスが表示される。

良さそう。

以上。


# 参考資料

- [Tauri でドラッグアンドドロップでファイルを受け取る at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/DragAndDrop)
- [variable appWindow - window | Tauri Apps](https://tauri.app/v1/api/js/window#appwindow)
- [function onFileDropEvent of WebviewWindow - window | Tauri Apps](https://tauri.app/v1/api/js/window#onfiledropevent)
- [ElectronからTauriに移行しようとしてD&Dで詰んだ話 #Electron - Qiita](https://qiita.com/mrin/items/efe899943c3f69d53353)

