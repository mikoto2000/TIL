# Rails relation has_many

## 開発環境

docker compose で開発環境を立ち上げます。

```sh
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
./bin/rails generate scaffold Group name:string
./bin/rails generate scaffold Account name:string group:references
```

### Model を編集して has_many 関係を定義

今回は、グループに複数のアカウントが所属しているという関連を定義。

```rb
# app/models/group.rb
class Group < ApplicationRecord
  has_many :accounts
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

### グループの作成

```ruby
group1 = Group.new
group1.name = "group1"
group1.save
```

### アカウントの作成

```ruby
account1 = Account.new
account1.name = "account1"
account1.group = group1
account1.save

account2 = Account.new
account2.name = "account2"
account2.group = group1
account2.save
```

Account の group 属性に、所属するグループを設定する。


### グループに所属しているアカウント一覧取得

```ruby
irb(main):013:0> group1.accounts
  Account Load (0.4ms)  SELECT "accounts".* FROM "accounts" WHERE "accounts"."group_id" = $1  [["group_id", 1]]
=>
[#<Account:0x00007fcdd0977fc0 id: 1, name: "account1", group_id: 1, created_at: Wed, 03 May 2023 13:30:20.289838000 UTC +00:00, updated_at: Wed, 03 May 2023 13:30:20.289838000 UTC +00:00>,
 #<Account:0x00007fcdd0977250 id: 2, name: "account2", group_id: 1, created_at: Wed, 03 May 2023 13:30:20.431719000 UTC +00:00, updated_at: Wed, 03 May 2023 13:30:20.431719000 UTC +00:00>]
```

こんな感じで、 外部キーで表現された 1 対多の関係を簡単に扱えるようになる。

GUI を作る際に、このあたりの便利メソッドが活用できるはず。


# 参考資料

- [Active Record の関連付け - Railsガイド](https://railsguides.jp/association_basics.html)


