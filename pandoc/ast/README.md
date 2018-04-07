下記コマンドで pandoc の AST(の JSON 表現) が出力される。

```sh
pandoc -f markdown -t json test.md -o test.json
```

json 形式から各種フォーマットへ変換できる。

```sh
pandoc -f json -t html5 test.json -o test.html
```

