# hello_world

`Hello, World!` というテキストが表示される。


## プロジェクト作成

```sh
mkdir hello_world
cd hello_world
elm init
```

## ソースコード作成

[./src/hello_world.elm](./src/hello_world.elm) を作成し、フォーマッターにかける。

```sh
elm-format --yes ./src/hello_world.elm
```

## コンパイル

```sh
elm make ./src/hello_world.elm
```

## 動作確認

生成された `./index.html` を開くと、`Hello, World!` と表示されたページが開く。

