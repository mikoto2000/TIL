# pattern_match

パターンマッチの挙動確認。


## プロジェクト作成

```sh
mkdir form
cd form
elm init
```

## ソースコード作成

[./src/pattern_match.elm](./src/pattern_match.elm) を作成し、フォーマッターにかける。

```sh
elm-format --yes ./src/pattern_match.elm
```

## 動作確認

MSYS2 or WSL の bash にスクリプトを流し込んで動作確認。

```sh
bash -c "elm repl < ./src/pattern_match.elm"
```

