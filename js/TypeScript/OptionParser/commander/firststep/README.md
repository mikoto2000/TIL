# firststep

## プロジェクト作成

```sh
npm init
```

## 必要パッケージのインストール

```sh
npm install --save-dev typescript
npm install --save-dev @types/node
npm install commander
```

## tsconfig.json 生成

```sh
./node_modules/.bin/tsc --init
```

## ビルドスクリプト定義追加

`package.json` の `script` へ、以下定義を追加。

```json
"build": "tsc"
```

## スクリプト作成

`index.ts` を作成。


## ビルド

```sh
npm run build
```

## 実行

```sh
# node index.js -a aaa -a true -a ccc -b -i 123 -f 12.0 -s abcdefg
string : abcdefg
integer: 123
float  : 12
array  : aaa,true,ccc
boolean: true
```

## 参考資料

- [tj/commander.js: node.js command-line interfaces made easy](https://github.com/tj/commander.js/#custom-option-processing)

