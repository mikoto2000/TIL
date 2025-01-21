---
title: Prisma で many to many のリレーションシップを定義する
author: mikoto2000
date: 2025/1/21
---

Prisma での many to many のリレーションシップ定義・操作の勉強を行う。


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

```sh
model Book {
  id      Int      @id @default(autoincrement())
  title   String
  authors BooksOnAuthors[]
}

model Author {
  id    Int    @id @default(autoincrement())
  name  String
  books BooksOnAuthors[]
}

model BooksOnAuthors {
  id         Int    @id @default(autoincrement())
  // 本を削除する際には、著者へのリレーションシップは消えて欲しいのでカスケード設定
  book       Book   @relation(fields: [bookId], references: [id], onDelete: Cascade, onUpdate: Cascade)
  bookId     Int // リレーションスカラーフィールド。 上記の `@relation` の `fields` で指定した名前にする。
  // 著者を消そうとしたときに、その著者の本がまだある場合はエラーにして火しいのでカスケードなし
  author     Author @relation(fields: [authorId], references: [id])
  authorId Int // リレーションスカラーフィールド
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
  // Author を生成
  const createdAuthor = await prisma.author.create({
    data: {
      name: 'mikoto2000',
    },
  })
  console.dir(`createdAuthor: ${JSON.stringify(createdAuthor)}`);

  // Book と、 Author へのリレーションシップを生成
  const createdBook = await prisma.book.create({
    data: {
      title: 'mikoto2000\'s history',
      authors: {
        create: [
          {
            authorId: createdAuthor.id
          }
        ]
      }
    },
  })
  console.dir(`createdBook: ${JSON.stringify(createdBook)}`);

  // 作成した Book の検索(中間テーブル・Author を JOIN して、戻り値に含める)
  const foundBook = await prisma.book.findMany({
    include: {
      authors: {
        include: {
          author: {}
        }
      }
    }
  });
  console.dir(`foundBook: ${JSON.stringify(foundBook)}`);
  console.dir(`プロパティ取得の練習: { title: ${foundBook[0].title}, Author: ${foundBook[0].authors.map((e) => e.author.name)}}`);

  // Book を削除(中間テーブルの要素と Book 自体の削除。中間テーブルはカスケード設定をしているので自動で削除される)
  const deletedBook = await prisma.book.delete({
    where: {
      id: createdBook.id
    },
  });
  console.dir(`deletedBook: ${JSON.stringify(deletedBook)}`);

  // Author を削除
  const deletedAuthor = await prisma.author.delete({
    where: {
      id: createdAuthor.id
    }
  });
  console.dir(`deletedAuthor: ${JSON.stringify(deletedAuthor)}`);
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
node ➜ ~/tmp $ node ./dist/index.js 
'createdAuthor: {"id":1,"name":"mikoto2000"}'
`createdBook: {"id":1,"title":"mikoto2000's history"}`
`foundBook: [{"id":1,"title":"mikoto2000's history","authors":[{"id":1,"bookId":1,"au
thorId":1,"author":{"id":1,"name":"mikoto2000"}}]}]`
"プロパティ取得の練習: { title: mikoto2000's history, Author: mikoto2000}"
`deletedBook: {"id":1,"title":"mikoto2000's history"}`
'deletedAuthor: {"id":1,"name":"mikoto2000"}'
```

ok! 以上。


# 参考資料

- [Quickstart with TypeScript & SQLite | Prisma Documentation](https://www.prisma.io/docs/getting-started/quickstart-sqlite)
- [CRUD (Reference) | Prisma Documentation](https://www.prisma.io/docs/orm/prisma-client/queries/crud)

