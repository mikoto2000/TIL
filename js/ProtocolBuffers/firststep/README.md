---
title: TypeScript で protobuf.js を使う
author: mikoto2000
date: 2023/7/4
---

# 開発用コンテナ起動

```sh
docker run -it --rm -v "$(pwd):/work" --workdir /work -p "0.0.0.0:5173:5173" node:18 bash
```


# プロジェクト作成

```sh
npm init vite@latest
# Vanilla, TypeScript を選択
```


# protobufjs のインストール

```sh
npm i protobufjs
npm i protobufjs-cli
```


# TS ファイル生成

```sh
npx pbjs -t static-module -w commonjs -o compiled.js proto/MemberInfo.proto
npx pbts -o compiled.d.ts compiled.js
```

