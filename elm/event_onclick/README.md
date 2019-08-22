# event_onclick

クリックするとテキストが更新される。


## プロジェクト作成

```sh
mkdir event_onclick
cd event_onclick
elm init
```

## ソースコード作成

[./src/event_onclick.elm](./src/event_onclick.elm) を作成し、フォーマッターにかける。

```sh
elm-format --yes ./src/event_onclick.elm
```

## コンパイル

```sh
elm make ./src/event_onclick.elm
```

## 動作確認

生成された `./index.html` を開き、表示された `Initial Message.` のテキストをクリックすると、テキストが `Message Updated!!!` に更新される。

