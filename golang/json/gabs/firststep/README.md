# gabs firststep

gabs を使って 2 つの JSON ファイルをマージする。

## 開発環境起動

```sh
devcontainer.vim-linux-amd64 run -v "$(pwd):/work" --workdir /work -v "$HOME/.vim:/root/.vim" golang:1.22.1-bookworm
```


## 環境構築

```sh
go mod init github.com/mikoto2000/golang/json/gabs/firststep
go get github.com/Jeffail/gabs/v2
```

## JSON ファイル作成

`json/base.json`:

```json
{
  "name":"Go",
  "image":"mcr.microsoft.com/devcontainers/go:1-1.22-bookworm",
  "mounts": [
    {
      "type": "bind",
      "source": "${localEnv:HOME}/.gitconfig",
      "target": "/home/vscode/.gitconfig"
    }
  ],
  "features":{},
  "remoteUser":"vscode"
}
```

`json/additional.json`:

```json
{
  "mounts": [
    {
      "type": "bind",
      "source": "${localEnv:HOME}/.vim",
      "target": "/home/vscode/.vim"
    }
  ]
}
```

## プログラム実装

```go
package main

import (
	"fmt"

	"github.com/Jeffail/gabs/v2"
)

const baseJsonFilePath = "json/base.json"
const additionalJsonFilePath = "json/additional.json"

func main() {
	parsedBaseJson, err := gabs.ParseJSONFile(baseJsonFilePath)
	if err != nil {
		panic(err)
	}
	fmt.Printf("=== %s ===\n", baseJsonFilePath)
	fmt.Println(parsedBaseJson.StringIndent("", "  "))
	fmt.Println()

	parsedAdditionalJson, err := gabs.ParseJSONFile(additionalJsonFilePath)
	if err != nil {
		panic(err)
	}
	fmt.Printf("=== %s ===\n", additionalJsonFilePath)
	fmt.Println(parsedAdditionalJson.StringIndent("", "  "))
	fmt.Println()

	parsedBaseJson.Merge(parsedAdditionalJson)
	fmt.Printf("=== Merged JSON %s and %s ===\n", baseJsonFilePath, additionalJsonFilePath)
	fmt.Println(parsedBaseJson.StringIndent("", "  "))
	fmt.Println()
}
```


## 実行

```sh
root@696abd662a3a:/work# go run main.go
=== json/base.json ===
{
  "features": {},
  "image": "mcr.microsoft.com/devcontainers/go:1-1.22-bookworm",
  "mounts": [
    {
      "source": "${localEnv:HOME}/.gitconfig",
      "target": "/home/vscode/.gitconfig",
      "type": "bind"
    }
  ],
  "name": "Go",
  "remoteUser": "vscode"
}

=== json/additional.json ===
{
  "mounts": [
    {
      "source": "${localEnv:HOME}/.vim",
      "target": "/home/vscode/.vim",
      "type": "bind"
    }
  ]
}

=== Merged JSON json/base.json and json/additional.json ===
{
  "features": {},
  "image": "mcr.microsoft.com/devcontainers/go:1-1.22-bookworm",
  "mounts": [
    {
      "source": "${localEnv:HOME}/.gitconfig",
      "target": "/home/vscode/.gitconfig",
      "type": "bind"
    },
    {
      "source": "${localEnv:HOME}/.vim",
      "target": "/home/vscode/.vim",
      "type": "bind"
    }
  ],
  "name": "Go",
  "remoteUser": "vscode"
}
```

## 参考資料

- [Jeffail/gabs: For parsing, creating and editing unknown or dynamic JSON in Go](https://github.com/Jeffail/gabs)

