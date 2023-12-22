# Zod union

firststep をベースにしています。

firststep からの diff を確認してください。

## 開発環境起動

```sh
docker run -it --rm -v "$(pwd):/work" --workdir /work node:18 bash
```

## 実装

`index.ts` を参照。

## 動作確認

```sh
root@f05dee4f0fb3:/work# npx tsc
root@f05dee4f0fb3:/work# node ./index.js
== 妥当な Box を Shape としてパース ==
パース成功: {"type":"box","height":11,"width":10}
{"success":true,"data":{"type":"box","height":11,"width":10}}

== 妥当な Box を Box としてパース ==
パース成功: {"type":"box","height":11,"width":10}
{"success":true,"data":{"type":"box","height":11,"width":10}}

== 妥当な Circle を Shape としてパース ==
パース成功: {"type":"circle","radius":12}
{"success":true,"data":{"type":"circle","radius":12}}

== 妥当な Circle を Circle としてパース ==
パース成功: {"type":"circle","radius":12}
{"success":true,"data":{"type":"circle","radius":12}}

== 不当な Shape を Shape としてパース ==
パース失敗: [
  {
    "code": "invalid_union",
    "unionErrors": [
      {
        "issues": [
          {
            "code": "invalid_literal",
            "expected": "box",
            "path": [
              "type"
            ],
            "message": "Invalid literal value, expected \"box\""
          },
          {
            "code": "invalid_type",
            "expected": "number",
            "received": "undefined",
            "path": [
              "height"
            ],
            "message": "Required"
          },
          {
            "code": "invalid_type",
            "expected": "number",
            "received": "undefined",
            "path": [
              "width"
            ],
            "message": "Required"
          }
        ],
        "name": "ZodError"
      },
      {
        "issues": [
          {
            "code": "invalid_literal",
            "expected": "circle",
            "path": [
              "type"
            ],
            "message": "Invalid literal value, expected \"circle\""
          },
          {
            "code": "invalid_type",
            "expected": "number",
            "received": "undefined",
            "path": [
              "radius"
            ],
            "message": "Required"
          }
        ],
        "name": "ZodError"
      }
    ],
    "path": [],
    "message": "Invalid input"
  }
]
{"success":false,"error":{"issues":[{"code":"invalid_union","unionErrors":[{"issues":[{"code":"invalid_literal","expected":"box","path":["type"],"message":"Invalid literal value, expected \"box\""},{"code":"invalid_type","expected":"number","received":"undefined","path":["height"],"message":"Required"},{"code":"invalid_type","expected":"number","received":"undefined","path":["width"],"message":"Required"}],"name":"ZodError"},{"issues":[{"code":"invalid_literal","expected":"circle","path":["type"],"message":"Invalid literal value, expected \"circle\""},{"code":"invalid_type","expected":"number","received":"undefined","path":["radius"],"message":"Required"}],"name":"ZodError"}],"path":[],"message":"Invalid input"}],"name":"ZodError"},"_error":{"issues":[{"code":"invalid_union","unionErrors":[{"issues":[{"code":"invalid_literal","expected":"box","path":["type"],"message":"Invalid literal value, expected \"box\""},{"code":"invalid_type","expected":"number","received":"undefined","path":["height"],"message":"Required"},{"code":"invalid_type","expected":"number","received":"undefined","path":["width"],"message":"Required"}],"name":"ZodError"},{"issues":[{"code":"invalid_literal","expected":"circle","path":["type"],"message":"Invalid literal value, expected \"circle\""},{"code":"invalid_type","expected":"number","received":"undefined","path":["radius"],"message":"Required"}],"name":"ZodError"}],"path":[],"message":"Invalid input"}],"name":"ZodError"}}

== 不当な Shape を Box としてパース ==
パース失敗: [
  {
    "code": "invalid_literal",
    "expected": "box",
    "path": [
      "type"
    ],
    "message": "Invalid literal value, expected \"box\""
  },
  {
    "code": "invalid_type",
    "expected": "number",
    "received": "undefined",
    "path": [
      "height"
    ],
    "message": "Required"
  },
  {
    "code": "invalid_type",
    "expected": "number",
    "received": "undefined",
    "path": [
      "width"
    ],
    "message": "Required"
  }
]
{"success":false,"error":{"issues":[{"code":"invalid_literal","expected":"box","path":["type"],"message":"Invalid literal value, expected \"box\""},{"code":"invalid_type","expected":"number","received":"undefined","path":["height"],"message":"Required"},{"code":"invalid_type","expected":"number","received":"undefined","path":["width"],"message":"Required"}],"name":"ZodError"},"_error":{"issues":[{"code":"invalid_literal","expected":"box","path":["type"],"message":"Invalid literal value, expected \"box\""},{"code":"invalid_type","expected":"number","received":"undefined","path":["height"],"message":"Required"},{"code":"invalid_type","expected":"number","received":"undefined","path":["width"],"message":"Required"}],"name":"ZodError"}}
== 不当な Shape を Circle としてパース ==
パース失敗: [
  {
    "code": "invalid_literal",
    "expected": "circle",
    "path": [
      "type"
    ],
    "message": "Invalid literal value, expected \"circle\""
  },
  {
    "code": "invalid_type",
    "expected": "number",
    "received": "undefined",
    "path": [
      "radius"
    ],
    "message": "Required"
  }
]
{"success":false,"error":{"issues":[{"code":"invalid_literal","expected":"circle","path":["type"],"message":"Invalid literal value, expected \"circle\""},{"code":"invalid_type","expected":"number","received":"undefined","path":["radius"],"message":"Required"}],"name":"ZodError"},"_error":{"issues":[{"code":"invalid_literal","expected":"circle","path":["type"],"message":"Invalid literal value, expected \"circle\""},{"code":"invalid_type","expected":"number","received":"undefined","path":["radius"],"message":"Required"}],"name":"ZodError"}}
```

## Reference

- [Zod | Documentation](https://zod.dev/)

