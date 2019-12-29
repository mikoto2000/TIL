# first_step

## setup

`bundle init`, `bundle install`.

```sh
bundle init
echo 'gem "roo"' >> .\Gemfile
bundle install --path=./vendor/bundle
```

# run

`bundle exec`.

```sh
> bundle exec ruby .\app.rb
====== データ型一覧
データ:
データ型一覧表, , , ,
, , , ,
No., 名前, 型, サイズ, 備考
1, uint8, unsigned char, 8, びこう１
2, uint16, unsigned short, 16, びこう２
3, uint32, unsigned long, 32, びこう３
4, sint8, char, 8, びこう４
5, sint16, short, 16, びこう５
6, sint32, long, 32, びこう６

====== ユーザー一覧
データ:
■ ユーザー一覧, , ,
, , ,
No., id, 名前, ユーザー種別
1, mikoto2000, 大雪 命, 管理者
```

