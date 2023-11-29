# JSON Forms i18n

firststep をベースにコードの追加を行っています。

firststep と diff をとって確認してください。

## 開発環境

開発環境起動。

```sh
docker run -it --rm -v "$(pwd):/work" --workdir /work -p 5173:5173  node:21 bash
```

## 動作確認

`npm run dev -- --host 0.0.0.0` でアプリを起動し、 Web ブラウザで `http://localhost:5173` へアクセスする。

デフォルト UI のフォームが表示され、スキーマに則ったバリデーションも行ってくれる。

## 参照資料

- [More forms. Less code. - JSON Forms](https://jsonforms.io/)
- [i18n - JSON Forms](https://jsonforms.io/docs/i18n)
- [ajv-validator/ajv: The fastest JSON schema Validator. Supports JSON Schema draft-04/06/07/2019-09/2020-12 and JSON Type Definition (RFC8927)](https://github.com/ajv-validator/ajv)
- [ajv-validator/ajv-i18n: Internationalised error messages for Ajv JSON schema validator](https://github.com/ajv-validator/ajv-i18n)

