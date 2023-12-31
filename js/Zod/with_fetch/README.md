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
test command: vitest run
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
npm i -D typescript@5.2.2 vitest msw @types/node
npx tsc --init
npm i zod
```

### gitignore の追加

```sh
curl -L https://raw.githubusercontent.com/github/gitignore/main/Node.gitignore -o .gitignore
```

### `package.json` の修正

vitest を使用する都合上、 ES Modules として作成したいので、そのように設定する。

```diff
diff --git a/js/Zod/with_fetch/package.json b/js/Zod/with_fetch/package.json
index 0245a6f..bc8c448 100644
--- a/js/Zod/with_fetch/package.json
+++ b/js/Zod/with_fetch/package.json
@@ -8,6 +8,7 @@
   },
   "author": "mikoto2000",
   "license": "MIT",
+  "type": "module",
   "devDependencies": {
     "@types/node": "^20.10.6",
     "msw": "^2.0.11",
```

### tsconfig の修正

「[2020年版スクラッチから作るなら - tsconfig.jsonを設定する | TypeScript入門『サバイバルTypeScript』](https://typescriptbook.jp/reference/tsconfig/tsconfig.json-settings#2020%E5%B9%B4%E7%89%88%E3%82%B9%E3%82%AF%E3%83%A9%E3%83%83%E3%83%81%E3%81%8B%E3%82%89%E4%BD%9C%E3%82%8B%E3%81%AA%E3%82%89)」を参考に、モダンな(？)設定に変更。

```diff
diff --git a/js/Zod/with_fetch/tsconfig.json b/js/Zod/with_fetch/tsconfig.json
index e075f97..529f93b 100644
--- a/js/Zod/with_fetch/tsconfig.json
+++ b/js/Zod/with_fetch/tsconfig.json
@@ -11,11 +11,11 @@
     // "disableReferencedProjectLoad": true,             /* Reduce the number of projects loaded automatically by TypeScript. */

     /* Language and Environment */
-    "target": "es2016",                                  /* Set the JavaScript language version for emitted JavaScript and include compatible library declarations. */
-    // "lib": [],                                        /* Specify a set of bundled library declaration files that describe the target runtime environment. */
+    "target": "es2022",                                  /* Set the JavaScript language version for emitted JavaScript and include compatible library declarations. */
+    "lib": ["es2022"],                                        /* Specify a set of bundled library declaration files that describe the target runtime environment. */
     // "jsx": "preserve",                                /* Specify what JSX code is generated. */
-    // "experimentalDecorators": true,                   /* Enable experimental support for legacy experimental decorators. */
-    // "emitDecoratorMetadata": true,                    /* Emit design-type metadata for decorated declarations in source files. */
+    "experimentalDecorators": true,                   /* Enable experimental support for legacy experimental decorators. */
+    "emitDecoratorMetadata": true,                    /* Emit design-type metadata for decorated declarations in source files. */
     // "jsxFactory": "",                                 /* Specify the JSX factory function used when targeting React JSX emit, e.g. 'React.createElement' or 'h'. */
     // "jsxFragmentFactory": "",                         /* Specify the JSX Fragment reference used for fragments when targeting React JSX emit e.g. 'React.Fragment' or 'Fra
gment'. */
     // "jsxImportSource": "",                            /* Specify module specifier used to import the JSX factory functions when using 'jsx: react-jsx*'. */
@@ -25,10 +25,10 @@
     // "moduleDetection": "auto",                        /* Control what method is used to detect module-format JS files. */

     /* Modules */
-    "module": "commonjs",                                /* Specify what module code is generated. */
-    // "rootDir": "./",                                  /* Specify the root folder within your source files. */
-    // "moduleResolution": "node10",                     /* Specify how TypeScript looks up a file from a given module specifier. */
-    // "baseUrl": "./",                                  /* Specify the base directory to resolve non-relative module names. */
+    "module": "es2022",                                /* Specify what module code is generated. */
+    "rootDir": "./src",                                  /* Specify the root folder within your source files. */
+    "moduleResolution": "node",                     /* Specify how TypeScript looks up a file from a given module specifier. */
+    "baseUrl": "./src",                                  /* Specify the base directory to resolve non-relative module names. */
     // "paths": {},                                      /* Specify a set of entries that re-map imports to additional lookup locations. */
     // "rootDirs": [],                                   /* Allow multiple folders to be treated as one when resolving modules. */
     // "typeRoots": [],                                  /* Specify multiple folders that act like './node_modules/@types'. */
@@ -52,10 +52,10 @@
     // "declaration": true,                              /* Generate .d.ts files from TypeScript and JavaScript files in your project. */
     // "declarationMap": true,                           /* Create sourcemaps for d.ts files. */
     // "emitDeclarationOnly": true,                      /* Only output d.ts files and not JavaScript files. */
-    // "sourceMap": true,                                /* Create source map files for emitted JavaScript files. */
+    "sourceMap": true,                                /* Create source map files for emitted JavaScript files. */
     // "inlineSourceMap": true,                          /* Include sourcemap files inside the emitted JavaScript. */
     // "outFile": "./",                                  /* Specify a file that bundles all outputs into one JavaScript file. If 'declaration' is true, also designates a fil
e that bundles all .d.ts output. */
-    // "outDir": "./",                                   /* Specify an output folder for all emitted files. */
+    "outDir": "./dist",                                   /* Specify an output folder for all emitted files. */
     // "removeComments": true,                           /* Disable emitting comments. */
     // "noEmit": true,                                   /* Disable emitting files from a compilation. */
     // "importHelpers": true,                            /* Allow importing helper functions from tslib once per project, instead of including them per-file. */
@@ -105,5 +105,7 @@
     /* Completeness */
     // "skipDefaultLibCheck": true,                      /* Skip type checking .d.ts files that are included with TypeScript. */
     "skipLibCheck": true                                 /* Skip type checking all .d.ts files. */
-  }
+  },
+  "include": ["./src/**/*"],
+  "exclude": ["./dist", "./node_modules", "./src/mocks", "./src/**/*.test.*"]
 }

```

## 実装

### 型定義追加

```ts
import { z } from "zod";

// 静的型定義
export type User = z.infer<typeof UserSchema>;
export type Users = z.infer<typeof UsersSchema>;

// ランタイム型の定義
export const UserSchema = z.object({
  firstName: z.string(),
  lastName: z.string(),
  age: z.number(),
});
export const UsersSchema = z.array(UserSchema);
```

### 動作確認のためのテストを実装

fetch して、結果を Zod でパースする処理を実装。

```ts
import { User, Users, UsersSchema } from "./types/User";

test("valid user", async () => {
  const httpResponse = await fetch("http://example.com/users");

  if (httpResponse.ok)  {
    const responseBody = await httpResponse.json();
    const parseResult = UsersSchema.safeParse(responseBody);

    if (parseResult.success) {
      expect(parseResult.data.length).toBe(1);
      expect(parseResult.data).toEqual([
        {
          firstName: "Mikoto",
          lastName: "Ohyuki",
          age: 500000000000
        }]);
    } else {
      throw Error(`パース失敗: ${parseResult.error}`);
    }
  } else {
    // とりあえず Zod の使い方の勉強なので異常系は省略...
    throw Error(`レスポンスが 200 台以外: ${JSON.stringify(httpResponse)}`);
  }
})

test("invalid user", async () => {
  const httpResponse = await fetch("http://example.com/invalid-users");

  if (httpResponse.ok)  {
    const responseBody = await httpResponse.json();
    const parseResult = UsersSchema.safeParse(responseBody);

    if (parseResult.success) {
      throw Error("パースに成功してはいけない");
    } else {
      parseResult.error.errors.forEach((e) => {

        // path はフィールドによって変わるので、存在確認のみ
        // 本来この辺りは Schema の単体テストでカバーするもの
        expect(e).toHaveProperty("code");
        expect(e.code).toEqual("invalid_type");
        expect(e).toHaveProperty("message");
        expect(e.message).toEqual("Required");
        expect(e).toHaveProperty("path");
      });
    }
  } else {
    // とりあえず Zod の使い方の勉強なので異常系は省略...
    throw Error(`レスポンスが 200 台以外: ${JSON.stringify(httpResponse)}`);
  }

})
```

### 動作確認のためのテスト用のモックを作成

[Getting started - Mock Service Worker](https://mswjs.io/docs/getting-started/) を参考にしながらモックを作成していく。

#### `handler.ts`

モックのハンドラ定義。

```ts
import { http, HttpResponse } from "msw";

import { User } from "../types/User";
 
export const handlers = [
  // パースを成功させるためのハンドラ
  http.get("http://example.com/users", () => {
    const user = {
      firstName: "Mikoto",
      lastName: "Ohyuki",
      age: 500000000000
    } as User;
    return HttpResponse.json([user]);
  }),
  // パースを失敗させるためのハンドラ
  http.get("http://example.com/invalid-users", () => {
    const user = {
      lastName: "Ohyuki",
    };
    return HttpResponse.json([user]);
  }),
]
```

#### `node.ts`

Node.js 上でモックサーバーを利用するための定義ファイル。

`setupServer` に上記で作ったハンドラを渡す。

```ts
import { setupServer } from 'msw/node'
import { handlers } from './handlers'

export const server = setupServer(...handlers)
```

#### `vitest.setup.ts`

各テストファイル実行前に共通で実行される処理。

各テストファイルが実行される前に、モックサーバーを開始し、テストのたびにハンドラをリセット。
ファイル内の全テストが終了したらクローズする。

```ts
import { beforeAll, afterEach, afterAll } from "vitest";

import { server } from './mocks/node'

beforeAll(() => server.listen());
afterEach(() => server.resetHandlers());
afterAll(() => server.close());
```

#### `vitest.config.ts`

`vitest.setup.ts` の場所を vitest に教える。

```ts
import { defineConfig } from 'vitest/config'

export default defineConfig({
  test: {
    setupFiles: ["./src/vitest.setup.ts"]
  },
})
```

## 動作確認

`npm test` すると、想定通りパースできたりパースエラーになったりすることが確認できる。


うーん、 msw や vitest の使い方がメインになってしまった.........。

## Reference

- [Zod | Documentation](https://zod.dev/)
- [TypeScript: TSConfig リファレンス - すべてのTSConfigのオプションのドキュメント](https://www.typescriptlang.org/ja/tsconfig)
- [2020年版スクラッチから作るなら - tsconfig.jsonを設定する | TypeScript入門『サバイバルTypeScript』](https://typescriptbook.jp/reference/tsconfig/tsconfig.json-settings#2020%E5%B9%B4%E7%89%88%E3%82%B9%E3%82%AF%E3%83%A9%E3%83%83%E3%83%81%E3%81%8B%E3%82%89%E4%BD%9C%E3%82%8B%E3%81%AA%E3%82%89)
- [expect | Vitest](https://vitest.dev/api/expect.html)
- [Getting started - Mock Service Worker](https://mswjs.io/docs/getting-started)
- [Node.js integration - Mock Service Worker](https://mswjs.io/docs/integrations/node)
- [setupFiles - Configuring Vitest | Vitest](https://vitest.dev/config/#setupfiles)
