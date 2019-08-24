# form

画面下部のテキストフィールドに入力した文字が、画面上部のテキストに反映される。

- `編集中テキスト`: テキストフィールドに入力中の文字列が表示される
- `送信済みテキスト`: `submit` ボタンを最後に押下したときの文字列が表示される


## プロジェクト作成

```sh
mkdir form
cd form
elm init
```

## ソースコード作成

[./src/form.elm](./src/form.elm) を作成し、フォーマッターにかける。

```sh
elm-format --yes ./src/form.elm
```

## コンパイル

```sh
elm make ./src/form.elm
```

## 動作確認

生成された `./index.html` を開き、テキストフィールドに文字を入力したり `submit` ボタンを押したりすると、表示が更新される。


