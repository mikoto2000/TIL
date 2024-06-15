---
title: Tauri で各プラットフォーム向けのアイコンを生成する
author: mikoto2000
date: 2024/6/11
---

掲題の通り。


# 前提

- 開発環境構築は済んでいるものとする
    - See: [mikoto2000/tauri](https://github.com/mikoto2000/docker-images/blob/master/tauri/Dockerfile)
- Tauri:
    - tauri-cli: 1.5.14
    - rust: 1.78.0
    - node: v22.2.0
- [この回](https://mikoto2000.blogspot.com/2024/06/tauri.html) で作成した [delete-default-event](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/delete-default-event) をベースに作業をしたので、そのディレクトリとの差分を取ってください


# ベースのアイコンを生成

[TIL/svg/icon/myicon.svg at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/blob/master/svg/icon/myicon.svg) に SVG 形式の自分のアイコンがあるので、これを 1024x1024 の背景透過 png へ変換する。

※この作業は Windows の ImageMagick を使って実行した

```sh
magick.exe convert -background transparent .\myicon.svg -resize 1024x1024 .\myicon.png
```

これで生成した png を、プロジェクトルートの一つ上のディレクトリへ配置した。


# png を基に各プラットフォーム向けのアイコンを生成

プロジェクトルートの一つ上に配置したアイコンを、 `npm run tauri icon` コマンドに渡すと、各プラットフォーム向けのアイコンを生成してくれる。

```sh
$ npm run tauri icon ../myicon.png

> tauri-delete-default-event@0.0.0 tauri
> tauri icon ../myicon.png

        Appx Creating StoreLogo.png
        Appx Creating Square30x30Logo.png
        Appx Creating Square44x44Logo.png
        Appx Creating Square71x71Logo.png
        Appx Creating Square89x89Logo.png
        Appx Creating Square107x107Logo.png
        Appx Creating Square142x142Logo.png
        Appx Creating Square150x150Logo.png
        Appx Creating Square284x284Logo.png
        Appx Creating Square310x310Logo.png
        ICNS Creating icon.icns
         ICO Creating icon.ico
         PNG Creating 128x128@2x.png
         PNG Creating icon.png
         PNG Creating 32x32.png
         PNG Creating 128x128.png
```

`src-tauri/icons` に生成されたファイルが格納される。

![生成後のアイコンたち](https://github.com/mikoto2000/TIL/assets/345832/3d44cfb7-9736-454b-90bd-38967a184353)

アプリを起動すると、タスクバーのアイコンが今回せいせいした物に代わっているのがわかる。

以上。


# 参考資料

- [Icons | Tauri Apps](https://tauri.app/v1/guides/features/icons/)
- [Xユーザーの大雪 命さん: 「備忘 memo. ImageMagick で背景透明の svg を背景透明の png へ変換するコマンド。 magick.exe convert -background transparent .\src.svg .\dest.png See: https://t.co/7PXfTJXAI1」 / X](https://x.com/mikoto2000/status/1567491226307526663)
- [Tauri で各プラットフォーム向けのアイコンを生成する at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/tree/master/tauri/1.0.0/Icons)

