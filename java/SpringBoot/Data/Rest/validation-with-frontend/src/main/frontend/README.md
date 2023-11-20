# Spring Data REST ManyToMany Exapmle frontend

## OpenAPI Generator CLI インストール

```sh
npm i -D @openapitools/openapi-generator-cli
```

## OpenAPI 定義から API クライアントを生成

バックエンドを起動したうえで、以下コマンドを実行。

```sh
# OpenAPI 定義ダウンロード
curl -L http://localhost:8080/v3/api-docs -o ./openapi/openapi.json

# コード生成生成
npx openapi-generator-cli generate -g typescript-axios -i ./openapi/openapi.json -o ./src/api --additional-properties=useSingleRequestParameter=true
```

## デプロイ

```sh
# フロントエンドビルド
npm run build

# ビルド成果物を Spring Boot の静的ファイルとして配置
cp -r dist/* ../resources/static/
```

※ どのパスからでも `index.html` を開くように設定が必要だが、今回はそこまでやっていない


## 参照資料

- https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/typescript-axios.md
- https://openapi-generator.tech/docs/configuration/

