% Module の確認
% mikoto2000
% 2019/12/31

# mruby のプロジェクトひな型作成

[mikoto2000/mruby-cli](https://github.com/mikoto2000/mruby-cli) を使用。

```sh
mruby-cli.exe -s module_test --mruby-version 2.1.0
```

# プログラム実装

```sh
vim module_test/mrblib/module_test.rb
```

# コンパイル

```sh
cd module_test
docker-compose run compile
```

`.\mruby\build\x86_64-w64-mingw32\bin\module_test.exe` に実行ファイルが生成される。

## 実行

```sh
> .\mruby\build\x86_64-w64-mingw32\bin\module_test.exe
MyModule class method hello!
MyModule instance method hello!
`MyClass.class_hello` raise NoMethodError.
[2, 4, 6]
```

以上。


# 参考資料

- [Enumerable モジュールを include して、eachによる繰り返し処理を用いたメソッドを利用する - Qiita](https://qiita.com/00inoue/items/4d3b392bcdde4d4498b6)
- [Enumerable を include する代わりに Enumerator オブジェクトを活用する - Qiita](https://qiita.com/QUANON/items/d84fcf417c285721837d)


