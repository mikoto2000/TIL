---
title: Drizzle firststep
author: mikoto2000
date: 2025/1/1
---

# 必要パッケージのインストール

```sh
npm i drizzle-orm pg dotenv
npm i -D drizzle-kit tsx @types/pg typescript
```

- dependencies
    - `drizzle-orm`: ORM 本体
    - `pg`: PostgreSQL へ接続するための Driver
    - `dotenv`: 接続先設定を env に記述するために利用
- devDependencies
    - `drizzle-kit`: スキーマファイル生成や、マイグレーション等を行うのに利用
    - `tsx`: 謎。チュートリアルに入っていたから入れた。
    - `@types/pg`: `pg` の型情報
    - `typescript`: TypeScript トランスパイルに利用


# DB 接続設定の作成

`.env` ファイルに接続先設定を記述する。

```env
DATABASE_URL=postgres://postgres@postgres/postgres
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


# Drizzle の設定

## コネクション初期化処理の作成

以下の通りコネクション初期化処理を作成。

```sh
mkdir -p ./src/db
cat << EOF > ./src/db/index.ts
import 'dotenv/config';
import { drizzle } from 'drizzle-orm/node-postgres';

const db = drizzle(process.env.DATABASE_URL!);
EOF
```

## Drizzle の設定ファイル作成

以下の通り設定ファイルを作成。

```sh
cat << EOF > ./drizzle.config.ts
import 'dotenv/config';
import { defineConfig } from 'drizzle-kit';

export default defineConfig({
  out: './drizzle',
  schema: './src/db/schema.ts',
  dialect: 'postgresql',
  dbCredentials: {
    url: process.env.DATABASE_URL!,
  },
});
EOF
```

# DB スキーマの作成

TypeScript でスキーマを書いていく。

以下コードを見れば、どんなスキーマを作りたいのかわかるはず...

```sh
cat << EOF > ./src/db/schema.ts
import { integer, pgTable, varchar } from "drizzle-orm/pg-core";

export const usersTable = pgTable("users", {
  id: integer().primaryKey().generatedAlwaysAsIdentity(),
  name: varchar({ length: 255 }).notNull(),
  age: integer().notNull(),
  email: varchar({ length: 255 }).notNull().unique(),
});
EOF
```


# マイグレーションファイルの生成と適用

## マイグレーションファイルの生成

```sh
npx drizzle-kit generate
```

設定ファイルの `out` に指定したとおり、 `./drizzle` にマイグレーションファイルが出力される。

## マイグレーションファイルの適用

`migration` サブコマンドで生成したマイグレーションファイルを DB に反映させる。

```sh
npx drizzle-kit migrate
```

A5:SQL-Mk2 で確認してみると、確かにテーブルが作成されている ... ok!


# メインファイルの作成

作成した Drizzle の設定で、DB にアクセスし、 CRD するアプリケーションを作成する。

```sh
cat <<"EOF" > ./src/index.ts
import 'dotenv/config';
import { drizzle } from 'drizzle-orm/node-postgres';
import { eq } from 'drizzle-orm';
import { usersTable } from './db/schema';
  
const db = drizzle(process.env.DATABASE_URL!);

async function main() {
  const user: typeof usersTable.$inferInsert = {
    name: 'John',
    age: 30,
    email: 'john@example.com',
  };

  await db.insert(usersTable).values(user);
  console.log('New user created!')

  const users = await db.select().from(usersTable);
  console.log('Getting all users from the database: ', users)
  /*
  const users: {
    id: number;
    name: string;
    age: number;
    email: string;
  }[]
  */

  await db
    .update(usersTable)
    .set({
      age: 31,
    })
    .where(eq(usersTable.email, user.email));
  console.log('User info updated!')

  await db.delete(usersTable).where(eq(usersTable.email, user.email));
  console.log('User deleted!')
}

main();
EOF
```

# 動作確認

## ビルド

```sh
npx tsc
node ./dist/src/index.js
```

## 実行

```sh
node ➜ /workspaces $ node ./dist/src/index.js
New user created!
Getting all users from the database:  [ { id: 1, name: 'John', age: 30, email: 'john@example.com' } ]
User info updated!
User deleted!
```

ok! 以上。

# 参考資料

- [Drizzle ORM - PostgreSQL](https://orm.drizzle.team/docs/get-started/postgresql-new)
- [tsconfig.jsonを設定する | TypeScript入門『サバイバルTypeScript』](https://typescriptbook.jp/reference/tsconfig/tsconfig.json-settings)

