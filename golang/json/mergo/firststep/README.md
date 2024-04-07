# mergo firststep

mergo を使って 2 つの JSON ファイルをマージする。


## 開発環境起動

```sh
devcontainer.vim-linux-amd64 run -v "$(pwd):/work" --workdir /work -v "$HOME/.vim:/root/.vim" golang:1.22.1-bookworm
```


## 環境構築

```sh
go mod init github.com/mikoto2000/golang/json/mergo/firststep
go get dario.cat/mergo
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

### JSON のスキーマファイル定義

`devcontainer/schema.go`:

```go
package devcontainer

import (
	"encoding/json"
)

type Devcontainer struct {
	Name       string
	Image      string
	Mounts     Mounts
	Features   interface{}
	RemoteUser string
}

type Mounts []Mount

type Mount struct {
	Type   string
	Source string
	Target string
}

func UnmarshalDevcontainer(data []byte) (Devcontainer, error) {
	var d Devcontainer
	err := json.Unmarshal(data, &d)
	return d, err
}
```

### 主処理実装

`main.go`:

```go
package main

import (
	"fmt"
	"os"

	"dario.cat/mergo"

	"github.com/mikoto2000/golang/json/mergo/firststep/devcontainer"
)

const baseJsonPath = "json/base.json"
const additionalJsonPath = "json/additional.json"

func main() {
	baseJson, err := parseJsonFile("json/base.json")
	if err != nil {
		panic(err)
	}
	fmt.Printf("=== %s ===\n", baseJsonPath)
	fmt.Printf("%+v\n", baseJson)
	fmt.Println()

	additionalJson, err := parseJsonFile("json/additional.json")
	if err != nil {
		panic(err)
	}
	fmt.Printf("=== %s ===\n", additionalJsonPath)
	fmt.Printf("%+v\n", additionalJson)
	fmt.Println()

	// マージオプションは以下を参照
	// https://github.com/darccio/mergo/blob/cde9f0ea26cccb1168ee3900cf8ca457bb928c3c/merge.go#L329-L372
	mergo.Merge(&baseJson, additionalJson, mergo.WithAppendSlice)
	fmt.Printf("=== Merged JSON %s and %s ===\n", baseJsonPath, additionalJsonPath)
	fmt.Printf("%+v\n", baseJson)
	fmt.Println()

}

func parseJsonFile(jsonFilePath string) (devcontainer.Devcontainer, error) {
	jsonContent, err := os.ReadFile(jsonFilePath)
	if err != nil {
		return devcontainer.Devcontainer{}, err
	}

	d, err := devcontainer.UnmarshalDevcontainer(jsonContent)
	if err != nil {
		return devcontainer.Devcontainer{}, err
	}

	return d, nil
}
```


## 実行

```sh
root@a67c959ef4b8:/work# go run main.go
=== json/base.json ===
{Name:Go Image:mcr.microsoft.com/devcontainers/go:1-1.22-bookworm Mounts:[{Type:bind Source:${localEnv:HOME}/.gitconfig Target:/home/vscode/.gitconfig}] Features:map[] RemoteUser:vscode}

=== json/additional.json ===
{Name: Image: Mounts:[{Type:bind Source:${localEnv:HOME}/.vim Target:/home/vscode/.vim}] Features:<nil> RemoteUser:}

=== Merged JSON json/base.json and json/additional.json ===
{Name:Go Image:mcr.microsoft.com/devcontainers/go:1-1.22-bookworm Mounts:[{Type:bind Source:${localEnv:HOME}/.gitconfig Target:/home/vscode/.gitconfig} {Type:bind Source:${localEnv:HOME}/.vim Target:/home/vscode/.vim}] Features:map[] RemoteUser:vscode}
```


## 参考資料

- [darccio/mergo: Mergo: merging Go structs and maps since 2013](https://github.com/darccio/mergo)
- [mergo/merge.go at cde9f0ea26cccb1168ee3900cf8ca457bb928c3c · darccio/mergo](https://github.com/darccio/mergo/blob/cde9f0ea26cccb1168ee3900cf8ca457bb928c3c/merge.go#L329-L372)

