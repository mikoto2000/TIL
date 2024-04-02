# Go FFI c language firststep

## 開発環境

```sh
docker run -it --rm -v "$(pwd):/work" --workdir /work -v "$HOME/.vim:/root/.vim" golang:1.22.1-bookworm
```


## 必要パッケージのインストール

```sh
apt install build-essential
```


## c 言語のプログラムのビルド

`lib` ターゲットで `make` を行うと、 `c_project/lib/libhello.a` が生成される。

```sh
cd c_project
make lib
```

## Go 言語のプログラムのビルド

`main.go` 内に、ビルドやリンクに必要な情報が記載されているので、
いつも通りに `go build` するだけ。

今回は `go_project/firststep` に実行バイナリが生成される。

```sh
cd go_project
go build
```

## 参考資料

- [C言語で静的・動的ライブラリを作成・使用する方法 #Linux - Qiita](https://qiita.com/Pirlo/items/8261361f2c153e104054)
- [cgoでGoのコードからCの関数を利用する - EagleLand](https://1000ch.net/posts/2014/c-in-golang-with-cgo.html)

