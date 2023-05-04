# Rails relation has_many

## 開発環境

docker compose で開発環境を立ち上げます。

```sH
docker compose up
```

### 開発コンテナへの接続

以下コマンドで、 ruby, nodejs, yarn の環境構築済みのコンテナに入ります。

```sh
docker compose exec app bash
```

## アプリケーション作成手順

### Rails プロジェクトの作成

`rails new` コマンドで、プロジェクトを生成します。

```sh
rails new app --css=bootstrap -d postgresql
cd app
```

- `--css=bootstrap`: Bootstrap を利用
- `-d postgresql` : 使用するデータベース。今回は PostgreSQL


### DB 設定更新

開発で使用するデータベースの設定は、 `config/database.yml` 内の `development` セクション内の項目に記載します。
必要に応じて、 `production` など、別環境の設定も行ってください。

```yaml
development:
  <<: *default
  host: postgres
  database: postgres
  username: admin
  password: password
```

### Scaffold 実行

```sh
./bin/rails generate scaffold Item name:string
./bin/rails generate scaffold Order
./bin/rails generate scaffold OrderItem order:references item:references
```

### Model を編集して has_many through 関係を定義

オーダーとアイテムが多対多になるように設定。

```rb
# app/models/order.rb
class Order < ApplicationRecord
  has_many :order_items
  has_many :items, through: :order_items
end
```

```rb
# app/models/item.rb
class Item < ApplicationRecord
  has_many :order_items
  has_many :orders, through: :order_items
end
```

```rb
# app/models/order_item.rb
class OrderItem < ApplicationRecord
  belongs_to :order
  belongs_to :item
end
```

### DB マイグレーション

以下コマンドで、マイグレーションを実行する。

```sh
rails db:migrate
```

## 動作確認

今回は GUI を作るのが面倒なので、 rails console で動作確認を行う。

### rails console の起動

以下コマンドで、開発用にサーバーを起動します。

```sh
rails console
```

### アイテムの作成

```ruby
item1 = Item.new
item1.name = "item1"
item1.save

item2 = Item.new
item2.name = "item2"
item2.save

item3 = Item.new
item3.name = "item2"
item3.save
```

### オーダーの作成

```ruby
order1 = Order.new
order1.save

order2 = Order.new
order2.save
```

### オーダーにアイテムを追加

```ruby
order1.items = [item1, item2]
order1.save

order2.items = [item1, item3]
order2.save
```


### グループに所属しているアカウント一覧取得

```ruby
irb(main):022:0> order1.items
=>
[#<Item:0x0000556fe07bd720
  id: 1,
  name: "item1",
  created_at: Thu, 04 May 2023 03:03:49.719400000 UTC +00:00,
  updated_at: Thu, 04 May 2023 03:03:49.719400000 UTC +00:00>,
 #<Item:0x0000556fe0554ce0
  id: 2,
  name: "item2",
  created_at: Thu, 04 May 2023 03:03:49.813609000 UTC +00:00,
  updated_at: Thu, 04 May 2023 03:03:49.813609000 UTC +00:00>]
```

こんな感じで、 交差テーブルを用いて表現された多対多の関係を簡単に扱えるようになる。

GUI を作る際に、このあたりの便利メソッドが活用できるはず。


# 参考資料

- [Active Record の関連付け - Railsガイド](https://railsguides.jp/association_basics.html)


