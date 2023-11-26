# JSON Forms firststep

## 開発環境

開発環境起動。

```sh
docker run -it --rm -v "$(pwd):/work" --workdir /work -p 5173:5173  node:21 bash
```

## プロジェクト作成

### 雛形作成

```sh
root@fbc5fede0e25:/work# npm create vite@latest
Need to install the following packages:
create-vite@5.0.0
Ok to proceed? (y)
✔ Project name: … firststep
✔ Select a framework: › React
✔ Select a variant: › TypeScript

Scaffolding project in /work/firststep...

Done. Now run:

  cd firststep
  npm install
  npm run dev

npm notice
npm notice New patch version of npm available! 10.2.0 -> 10.2.4
npm notice Changelog: https://github.com/npm/cli/releases/tag/v10.2.4
npm notice Run npm install -g npm@10.2.4 to update!
```

### 依存追加

```sh
npm i @jsonforms/core @jsonforms/react @jsonforms/material-renderers ajv
```

## 実装

### フォームの型定義

以下のクラスに対応するフォームを作るイメージ。

```ts
export type User = {
  /**
   * 必須。`USER-` で始まり、以降数値の文字列。
   */
  userCode: `USER-${number}`;

  /**
   * 必須。 1 文字以上の文字列。
   */
  userName: string;

  /**
   * 必須。ひとつ以上のメールアドレス。
   */
  mail: string[];

  /**
   * オプション。誕生日。
   * ※ 日時操作したい場合にはその都度変換する
   */
  birthday?: string;

  /**
   * オプション。謎日時。
   * ※ 日時操作したい場合にはその都度変換する
   */
  datetime?: string;

  /**
   * オプション。ゼロ個以上の URL。
   */
  homepage?: URL[];
};
```

このクラスを表す JSON Schema を定義する。


■ User.schema.json

```json
{
  "type": "object",
  "properties": {
    "userCode": {
      "description": "ユーザーコード",
      "type": "string",
      "pattern": "^USER-[0-9]+$"
    },
    "userName": {
      "description": "ユーザー名",
      "type": "string",
      "minLength": 1
    },
    "birthday": {
      "description": "誕生日",
      "type": "string",
      "format": "date"
    },
    "datetime": {
      "description": "謎日時",
      "type": "string",
      "format": "date-time"
    },
    "mail": {
      "type": "array",
      "items": {
        "description": "メールアドレス",
        "type": "string",
        "format": "email"
      },
      "minItems": 1
    },
    "homepage": {
      "type": "array",
      "items": {
        "description": "URL",
        "type": "string",
        "format": "url"
      }
    }
  },
  "required": ["userName", "mail"]
}
```

firststep では、レイアウト定義は行わない。

### アプリへ Form を組み込む

`cells`, `renderers` をインポート。今回は `@jsonforms/material-renderers` のものを使う。

```tsx
import {
  materialCells,
  materialRenderers,
} from '@jsonforms/material-renderers';
```

初期値, State, 変更イベントハンドラを作成。

```ts
  const initialUser: User = {
    userName: ""
  };

  const [data, setData] = useState<User>(initialUser);
  const [errors, setErrors] = useState<ErrorObject[]>([]);

  const handleFormChange = (event: {data: User, errors: ErrorObject[]}) => {
    setData(event.data);
    setErrors(event.errors);
  }
```

作成した情報を `JsonForms` コンポーネントへ渡す。

```ts
      <JsonForms
        schema={userSchema}
        data={data}
        onChange={handleFormChange}
        cells={materialCells}
        renderers={materialRenderers}
      />
```


<details>
    <summary>App.ts 全体</summary>

```ts
import { useState } from 'react'

import { JsonForms } from '@jsonforms/react';
import {
  materialCells,
  materialRenderers,
} from '@jsonforms/material-renderers';
import { ErrorObject } from 'ajv';
import userSchema from './User.schema.json';

import './App.css'
import { User } from './User.ts';

function App() {

  const initialUser: User = {
    userName: ""
  };

  const [data, setData] = useState<User>(initialUser);
  const [errors, setErrors] = useState<ErrorObject[]>([]);

  const handleFormChange = (event: {data: User, errors: ErrorObject[]}) => {
    setData(event.data);
    setErrors(event.errors);
  }

  return (
    <>
      <JsonForms
        schema={userSchema}
        data={data}
        onChange={handleFormChange}
        cells={materialCells}
        renderers={materialRenderers}
      />

      <h1>data:</h1>
      <p>
        {JSON.stringify(data)}
      </p>

      <h1>errors:</h1>
      <p>
        {JSON.stringify(errors)}
      </p>
    </>
  )
}

export default App

```
</details>

## 動作確認

`npm run dev -- --host 0.0.0.0` でアプリを起動し、 Web ブラウザで `http://localhost:5173` へアクセスする。

デフォルト UI のフォームが表示され、スキーマに則ったバリデーションも行ってくれる。

## 参照資料

- [More forms. Less code. - JSON Forms](https://jsonforms.io/)
- [apiextensions-apiserver/pkg/apis/apiextensions/v1/types_jsonschema.go at master · kubernetes/apiextensions-apiserver](https://github.com/kubernetes/apiextensions-apiserver/blob/master/pkg/apis/apiextensions/v1/types_jsonschema.go)
- [TypeScriptの型定義で正規表現っぽいかんじでstring型を詳しく定義する](https://zenn.dev/s1r_j/articles/f2db9413fe6b03fc1089)
- [How to define the min size of array in the json schema - Stack Overflow](https://stackoverflow.com/questions/16583485/how-to-define-the-min-size-of-array-in-the-json-schema)
- [draft-fge-json-schema-validation-00](https://datatracker.ietf.org/doc/html/draft-fge-json-schema-validation-00#section-5.3.3)
- [JSON Schema](https://json-schema.org/)

