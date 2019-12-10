% mruby で exe ファイルを作る
% mikoto2000
% 2019/12/6

# mruby のプロジェクトひな型作成

```sh
mruby-cli.exe -s hello
```

# プログラム実装

```sh
vim hello/mrblib/hello.rb
```

# コンパイル

## クロスコンパイル設定

`build_config.rb` を編集。

今回は Windows 用バイナリを生成するのが目的なので、 `MRuby::CrossBuild.new('x86_64-w64-mingw32')` 以外の `MRuby::CrossBuild` ブロックを削除。

```sh
vim build_config.rb
```


## コンパイル

```sh
cd hello
docker-compose run compile
```

`.\mruby\build\x86_64-w64-mingw32\bin\hello.exe` に実行ファイルが生成される。

## 実行

```sh
> .\mruby\build\x86_64-w64-mingw32\bin\hello.exe
Hello World
```


以上。

