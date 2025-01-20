---
title: Prisma に TypeScript と PostgreSQL で入門する
author: mikoto2000
date: 2025/1/1
---

Drizzle との違いを理解するため Prisma の Get Started をやってみる。

# 前提

- OS: Ubuntu 24.04 on WSL2 on Windows Pro 11
- Docker コンテナ: [mcr.microsoft.com/devcontainers/typescript-node:1-22](https://mcr.microsoft.com/en-us/artifact/mar/devcontainers/javascript-node/about)


# プロジェクトの初期化

```sh
npm init
```

# 必要パッケージのインストール

```sh
npm i dotenv
npm i -D typescript tsx @types/node prisma
```

- dependencies
    - `dotenv`: 接続先設定を env に記述するために利用
- devDependencies
    - `typescript`: TypeScript トランスパイルに利用
    - `tsx`: ts を実行できるコマンド。チュートリアルに入っていたから入れたが、使いどころは謎。
    - `@types/node`: `node` の型情報


# DB 接続設定の作成

`.env` ファイルに接続先設定を記述する。

```env
DATABASE_URL=postgres://postgres:postgres@postgres/postgres
```


# TypeScript 設定ファイル(tsconfig.json)の作成

## `tsconfig.json` の作成

`tsc` コマンドで、 `tsconfig.json` を作成する

```sh
npx tsc --init
```

## `tsconfig.json` のパラメーター編集

以下表のとおりパラメーターを設定。

| パラメーター    | 値
| --              | --
| `outDir`        | `./dist`


# ここまでの動作確認

ひとまず TypeScript をコンパイル・実行できるかを確認。

```sh
mkdir src
echo 'console.log("Hello, World!");' > ./src/index.ts
tsc
node ./dist/index.js
```

`Hello, World!` が表示される ... ok!


# Prisma の設定

## Prisma の初期化

```sh
npx prisma init --datasource-provider postgresql
```

`prisma/schema.prisma` にスキーマファイルが生成される。

NOTE: ここで、 `npx prisma db pull` とすると、既存 DB を基にスキーマファイルを更新してくれる


# DB スキーマの作成

Prisma の DSL でスキーマを書いていく。

以下コードを見れば、どんなスキーマを作りたいのかわかるはず...

```sh
model User {
  id    Int     @id @default(autoincrement())
  email String  @unique
  name  String?
  posts Post[]
}

model Post {
  id        Int     @id @default(autoincrement())
  title     String
  content   String?
  published Boolean @default(false)
  author    User    @relation(fields: [authorId], references: [id])
  authorId  Int
}
```


# prisma client のインストールとクライアントコード生成

この時点で、モデルに対応した API が生成されるようだ。

```sh
npx prisma generate
```



# マイグレーションの実行

```sh
npx prisma migrate dev --name init
```

`prisma/migrations` にマイグレーション用 SQL が生成され、それが実行される。

A5:SQL-Mk2 で確認してみると、確かにテーブルが作成されている ... ok!


# メインファイルの作成

作成した Prisma の設定で DB にアクセスし、 CRUD するアプリケーションを作成する。

`src/index.ts`:

```ts
import { PrismaClient } from '@prisma/client'

const prisma = new PrismaClient()

async function main() {
  // User を生成
  const createdUser = await prisma.user.create({
    data: {
      name: 'mikoto2000',
      email: 'mikoto2000@gmail.com',
    },
  })
  console.dir(`createdUser: ${JSON.stringify(createdUser)}`);

  // user を検索
  const foundUser = await prisma.user.findUnique({
    where: {
      email: 'mikoto2000@gmail.com',
    }
  });
  console.dir(`foundUser: ${JSON.stringify(foundUser)}`);

  // user を更新
  if (foundUser) {
    const updatedUser = await prisma.user.update({
      where: {
        email: foundUser.email,
      },
      data: {
        name: 'mikoto2000+prisma@gmail.com',
      },
    })
    console.dir(`updatedUser: ${JSON.stringify(updatedUser)}`);
  }

  // user を再び検索
  const foundUser2 = await prisma.user.findMany({
    where: {
      name: 'mikoto2000+prisma@gmail.com',
    }
  });
  console.dir(`foundUser2: ${JSON.stringify(foundUser2)}`);

  // user を削除
  const deletedUser = await prisma.user.delete({
    where: {
      id: createdUser.id
    }
  });
  console.dir(`deletedUser: ${JSON.stringify(deletedUser)}`);
}

main()
  .then(async () => {
    await prisma.$disconnect()
  })
  .catch(async (e) => {
    console.error(e)
    await prisma.$disconnect()
    process.exit(1)
  })
```

# 動作確認

## ビルド

```sh
npx tsc
```


## 実行

```sh
node ➜ /workspaces $ node ./dist/index.js
'createdUser: {"id":21,"email":"mikoto2000@gmail.com","name":"mikoto2000"}'
'foundUser: {"id":21,"email":"mikoto2000@gmail.com","name":"mikoto2000"}'
'updatedUser: {"id":21,"email":"mikoto2000@gmail.com","name":"mikoto2000+prisma@gmail.com"}'
'foundUser2: [{"id":21,"email":"mikoto2000@gmail.com","name":"mikoto2000+prisma@gmail.com"}]'
'deletedUser: {"id":21,"email":"mikoto2000@gmail.com","name":"mikoto2000+prisma@gmail.com"}'
```

ok! 以上。


# 参考資料

- [Quickstart with TypeScript & SQLite | Prisma Documentation](https://www.prisma.io/docs/getting-started/quickstart-sqlite)
- [CRUD (Reference) | Prisma Documentation](https://www.prisma.io/docs/orm/prisma-client/queries/crud)

