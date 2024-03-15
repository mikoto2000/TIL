# clipboard firststep

引数で受け取った文字列をクリップボードに書き込む。


## 開発環境起動

```sh
docker run -it --rm -v "$(pwd):/work" --workdir /work golang:1.22.1-bookworm
```


## プロジェクト初期化

```sh
go mod init github.com/mikoto2000/TIL/golang/clipboard/firststep
```


## パッケージの追加

`go get` コマンドで追加する。

```sh
go get golang.design/x/clipboard
```

## 参考資料

- [clipboard package - golang.design/x/clipboard - Go Packages](https://pkg.go.dev/golang.design/x/clipboard@v0.7.0)
- [golang-design/clipboard: 📋 cross-platform clipboard package that supports accessing text and image in Go (macOS/Linux/Windows/Android/iOS)](https://github.com/golang-design/clipboard)

