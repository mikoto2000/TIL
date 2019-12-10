% File.open の確認
% mikoto2000
% 2019/12/10

# mruby のプロジェクトひな型作成

```sh
mruby-cli.exe -s test_file_open
```

# プログラム実装

```sh
vim file/mrblib/test_file_open.rb
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
cd test_file_open
docker-compose run compile
```

`.\mruby\build\x86_64-w64-mingw32\bin\test_file_open.exe` に実行ファイルが生成される。

## 実行

```sh
> bash.exe -c "echo 'Hello, World!' > test.txt"
> .\mruby\build\x86_64-w64-mingw32\bin\test_file_open.exe
Hello, World!
```


以上。


