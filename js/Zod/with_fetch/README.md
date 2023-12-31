# Zod with fetch

## Create project

### npm プロジェクトのひな形作成

```sh
root@86f533f1ce6a:/work# npm init
This utility will walk you through creating a package.json file.
It only covers the most common items, and tries to guess sensible defaults.

See `npm help init` for definitive documentation on these fields
and exactly what they do.

Use `npm install <pkg>` afterwards to install a package and
save it as a dependency in the package.json file.

Press ^C at any time to quit.
package name: (work) with_fetch
version: (1.0.0)
description: Zod with fetch
entry point: (index.js)
test command: vitest
git repository:
keywords:
author: mikoto2000
license: (ISC) MIT
About to write to /work/package.json:

{
  "name": "with_fetch",
  "version": "1.0.0",
  "description": "Zod with fetch",
  "main": "index.js",
  "scripts": {
    "test": "vitest"
  },
  "author": "mikoto2000",
  "license": "MIT"
}


Is this OK? (yes) yes
```

### 必要なパッケージのインストール

```sh
npm i -D typescript@5.2.2 vitest msw
npx tsc --init
npm i zod
```

### gitignore の追加

```sh
curl -L https://raw.githubusercontent.com/github/gitignore/main/Node.gitignore -o .gitignore
```


## Create types

```ts
import { z } from "zod";

// ランタイム型の定義
const UserSchema = z.object({
  firstName: z.string(),
  lastName: z.string(),
  age: z.number(),
});

// 静的型定義
type User = z.infer<typeof UserSchema>;

// User として妥当な値
const validUser = {
  firstName: "firstName",
  lastName: "lastName",
  age: 123,
};

// User として不当な値
const invalidUser = {
  name: "firstName lastName",
  age: 123,
};

// パース
console.log("== エラー時に例外を送出するパース ==");

try {
  const user1: User = UserSchema.parse(validUser);
  console.log(JSON.stringify(user1));
} catch (error) {
  console.log(error);
}

try {
  const user2: User = UserSchema.parse(invalidUser);
  console.log(JSON.stringify(user2));
} catch (error) {
  console.log(JSON.stringify(error));
}

console.log("\n== パース結果を戻り値として返却するパース ==");

const user3Result = UserSchema.safeParse(validUser);
console.log(JSON.stringify(user3Result));

const user4Result = UserSchema.safeParse(invalidUser);
console.log(JSON.stringify(user4Result));
```

## Compile and run

```sh
npx tsc && node index.js
```

## Result

```sh
== エラー時に例外を送出するパース ==
{"firstName":"firstName","lastName":"lastName","age":123}
{"issues":[{"code":"invalid_type","expected":"string","received":"undefined","path":["firstName"],"message":"Required"},{"code":"invalid_type","expected":"string","received":
"undefined","path":["lastName"],"message":"Required"}],"name":"ZodError"}

== パース結果を戻り値として返却するパース ==
{"success":true,"data":{"firstName":"firstName","lastName":"lastName","age":123}}
{"success":false,"error":{"issues":[{"code":"invalid_type","expected":"string","received":"undefined","path":["firstName"],"message":"Required"},{"code":"invalid_type","expec
ted":"string","received":"undefined","path":["lastName"],"message":"Required"}],"name":"ZodError"}}
```

## Reference

- [Zod | Documentation](https://zod.dev/)


