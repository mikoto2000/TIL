% キーワード引数の確認
% mikoto2000
% 2019/12/11

# mruby のプロジェクトひな型作成

```sh
mruby-cli.exe -s test_keyword_arg
```

# プログラム実装

```sh
vim test_keyword_arg/mrblib/test_keyword_arg.rb
```

# コンパイル

## mruby-cli でダウンロードされた mruby を差し替える

`mrubu-cli` ではバージョン 1.2.0 の mruby がダウンロードされる。

mruby 2.0.0 からキーワード引数が使えるようになっているので差し替える。

```sh
cd test_keyword_arg
bash.exe -c "curl -L --fail --retry 3 --retry-delay 1 https://github.com/mruby/mruby/archive/2.1.0.tar.gz -s -o - | tar zxf -"
mv mruby-2.1.0 mruby
cd ..
```

## クロスコンパイル設定

`build_config.rb` を編集。

今回は Windows 用バイナリを生成するのが目的なので、 `MRuby::CrossBuild.new('x86_64-w64-mingw32')` 以外の `MRuby::CrossBuild` ブロックを削除。

```sh
vim test_keyword_arg/build_config.rb
```


## コンパイル

```sh
cd test_keyword_arg
docker-compose run compile
```

`.\mruby\build\x86_64-w64-mingw32\bin\test_keyword_arg.exe` に実行ファイルが生成される。

## 実行

```sh
> .\mruby\build\x86_64-w64-mingw32\bin\test_keyword_arg.exe
key1: key1, key2: key2
```


以上。



