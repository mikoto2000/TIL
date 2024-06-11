---
title: Tauri v1 に入門する(プロジェクト作成から Linux 向けビルド・Windows 向けのクロスビルドまで)
author: mikoto2000
date: 2024/6/11
---

# 前提

- 開発環境構築は済んでいるものとする
    - See: [mikoto2000/tauri](https://github.com/mikoto2000/docker-images/blob/master/tauri/Dockerfile)


# create-tauri-app を使用したプロジェクト作成

以下内容でプロジェクトを作成する。

- プロジェクト名: tauri-v1-firststep
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


# 開発

`npm tauri dev` コマンドを実行すると、開発用にアプリが立ち上がる。

```sh
npm run tauri dev
```

この状態でソースコードを変更すると、アプリに変更が反映される。


# デプロイ

## Linux 用にネイティブコンパイル

`src-tauri/tauri.conf.json` 内の `tauri/bundle/identifier` を書き換えたうえで、 `npm run tauri build` を実行する。

```sh
node@2a5899df88f3:/workspaces/TIL/tauri/1.0.0/firststep/tauri-v1-firststep$ npm run tauri build

> tauri-v1-firststep@0.0.0 tauri
> tauri build

     Running beforeBuildCommand `npm run build`

> tauri-v1-firststep@0.0.0 build
> tsc && vite build

vite v5.2.13 building for production...
✓ 33 modules transformed.
dist/index.html                   0.47 kB │ gzip:  0.30 kB
dist/assets/react-CHdo91hT.svg    4.13 kB │ gzip:  2.05 kB
dist/assets/index-Dfkbh-rD.css    1.37 kB │ gzip:  0.65 kB
dist/assets/index-Cq7Jx5WI.js   144.12 kB │ gzip: 46.41 kB
✓ built in 360ms
   Compiling proc-macro2 v1.0.85
   Compiling unicode-ident v1.0.12
   ...(snip)
   Compiling gdk v0.15.4
   Compiling webkit2gtk v0.18.2
    Finished `release` profile [optimized] target(s) in 45.62s
    Bundling tauri-v1-firststep_0.0.0_amd64.deb (/workspaces/TIL/tauri/1.0.0/firststep/tauri-v1-firststep/src-tauri/target/release/bundle/deb/tauri-v1-firststep_0.0.0_amd64.deb)
    Bundling tauri-v1-firststep_0.0.0_amd64.AppImage (/workspaces/TIL/tauri/1.0.0/firststep/tauri-v1-firststep/src-tauri/target/release/bundle/appimage/tauri-v1-firststep_0.0.0_amd64.AppImage)
    Finished 2 bundles at:
        /workspaces/TIL/tauri/1.0.0/firststep/tauri-v1-firststep/src-tauri/target/release/bundle/deb/tauri-v1-firststep_0.0.0_amd64.deb
        /workspaces/TIL/tauri/1.0.0/firststep/tauri-v1-firststep/src-tauri/target/release/bundle/appimage/tauri-v1-firststep_0.0.0_amd64.AppImage
```

Linux では、 `.deb` と `.AppImage` が生成される。

それぞれの出力先は以下。

| 種類      | パス                                      |
|-----------|-------------------------------------------|
| deb       | src-tauri/target/release/bundle/deb       |
| AppImage  | src-tauri/target/release/bundle/appimage  |


## Windows 用にクロスコンパイル

`.msi` の出力には失敗したが、`.exe` の出力には成功したので手順を記載。


### クロスコンパイルに必要なパッケージの取得

クロスコンパイルに MinGW を使用するので、パッケージをインストールする。

```sh
sudo apt install mingw-w64
```

### クロスコンパイルのターゲットをインストール

`rustup` で、 `x86_64-pc-windows-gnu` のターゲットをインストールする。

```sh
cd src-tauri
rustup target add x86_64-pc-windows-gnu
```

### クロスコンパイル

プロジェクトルートに戻り、 `npm` コマンドでターゲットを指定しながらビルドする。

```sh
cd ${PROJECT_ROOT}
npm run tauri build -- --target x86_64-pc-windows-gnu
```

`.exe` が `src-tauri/target/x86_64-pc-windows-gnu/release` に出力される。

以上。


# 参考資料

- [Prerequisites | Tauri Apps](https://tauri.app/v1/guides/getting-started/prerequisites)
- [Quick Start | Tauri Apps](https://tauri.app/v1/guides/getting-started/setup/)
- [Linux Bundle | Tauri Apps](https://tauri.app/v1/guides/building/linux)
- [Rust メモ Windows向けにクロスコンパイル - エンジニアですよ！](https://totem3.hatenablog.jp/entry/2016/11/21/080949)
- [Rustでクロスコンパイル - ryochack.blog](https://ryochack.hatenablog.com/entry/2017/10/22/014735)

