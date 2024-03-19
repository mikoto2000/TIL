# Go Quicktype firststep

## 開発環境起動

```sh
docker run -it --rm -v "$(pwd):/work" --workdir /work --name devcontainer.vim golang:1.22.1-bookworm
```

## プロジェクト初期化

```sh
go mod init github.com/mikoto2000/TIL/golang/json/quicktype/firststep
```

## 実装

### スキーマ作成

今回は、以下例のような、苗字と名前を持つリストのスキーマを定義する。

`testdata/Persons.json`

```json
[
  {
    "first_name": "Mikoto",
    "last_name": "Ohyuki"
  },
  {
    "first_name": "Mikoto2",
    "last_name": "Ohyuki2"
  }
]
```

`schema/Persons.schema.json`

```json
{
  "$schema": "http://json-schema.org/draft-06/schema#",
  "type": "array",
  "items": {
    "$ref": "#/definitions/Person"
  },
  "definitions": {
    "Person": {
      "type": "object",
      "additionalProperties": false,
      "properties": {
        "first_name": {
          "type": "string"
        },
        "last_name": {
          "type": "string"
        }
      },
      "required": [
        "first_name",
        "last_name"
      ],
      "title": "Person"
    }
  }
}
```

### JSON エンコード・デコード処理生成

開発コンテナ内で docker が使えないため、 Docker ホスト側で実行する。

#### コード生成用コンテナ起動

```sh
docker run -it --rm -v "$(pwd):/work" --workdir /work node:21 bash
```

#### コード生成

```sh
npm i -g quicktype
quicktype -s schema ./schema/Persons.schema.json -o ./persons.go
```

これで、 `./persons.go` に以下のようなコードが生成される。

```go
// This file was generated from JSON Schema using quicktype, do not modify it directly.
// To parse and unparse this JSON data, add this code to your project and do:
//
//    persons, err := UnmarshalPersons(bytes)
//    bytes, err = persons.Marshal()

package main

import "encoding/json"

type Persons []Person

func UnmarshalPersons(data []byte) (Persons, error) {
	var r Persons
	err := json.Unmarshal(data, &r)
	return r, err
}

func (r *Persons) Marshal() ([]byte, error) {
	return json.Marshal(r)
}

type Person struct {
	FirstName string `json:"first_name"`
	LastName  string `json:"last_name"`
}
```

### メインコード実装

`main.go`

```go
package main

import (
	_ "embed"
	"fmt"
)

// testdata ディレクトリに JSON ファイルがあるので、そこから読み込む
//
//go:embed testdata/Persons.json
var jsonString string

func main() {
	// JSON 文字列 ⇒ Go 構造体
	persons, err := UnmarshalPersons([]byte(jsonString))
	if err != nil {
		panic(err)
	}
	fmt.Println(persons)

	// Go 構造体 ⇒ JSON 文字列
	newPersons := Persons{
		Person{
			"Makoto",
			"Ohyuki",
		},
		Person{
			"Mokoto",
			"Ohyuki",
		},
	}
	newPersonsJsonString, err := newPersons.Marshal()
	if err != nil {
		panic(err)
	}
	fmt.Println(string(newPersonsJsonString))
}
```

## 動作確認

`main.go` も `persons.go` も main パッケージとして作ったので、 `go run` にふたつのファイルを渡して実行する。

```sh
# go run main.go persons.go
[{Mikoto Ohyuki} {Mikoto2 Ohyuki2}]
[{"first_name":"Makoto","last_name":"Ohyuki"},{"first_name":"Mokoto","last_name":"Ohyuki"}]
```

OK.
