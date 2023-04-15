# Rails firststep

```sh
docker compose up
```

## 開発環境

docker compose で開発環境を立ち上げます。

構築手順については、 [./.devcontainer/dockerfile/app/Dockerfile](./.devcontainer/dockerfile/app/Dockerfile) を参照してください。

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
./bin/rails generate scaffold Role name:string
./bin/rails generate scaffold Account name:string role:belongs_to expiration_date:date
```

### 必要に応じて Model を編集(今回は不要)

ひとつのテーブルに対して、複数カラムで foreign key を設定している場合、モデルの `belongs_to` を適切に設定してください。

例: `app/models/present_history.rb` を編集

```rb
class PresentHistory < ApplicationRecord
  def self.ransackable_attributes(auth_object = nil)
    ["id", "name", "from_account_id", "to_account_id"]
  end

  belongs_to :from_account, class_name: 'Account' # この行の、 `, class_name: 'Account' を追加
  belongs_to :to_account, class_name: 'Account' # この行の、 `, class_name: 'Account' を追加
end
```

### DB マイグレーション

#### マイグレーションファイルの修正

制約があれば、 `db/migrate/*_create_*` のファイルを修正してください。

今回は、以下のように制約を設定した。

```ruby
# app/db/migrate/*_create_role.rb
class CreateRoles < ActiveRecord::Migration[7.0]
  def change
    create_table :roles do |t|
      # name に NOT NULL 制約を設定
      t.string :name, :null => false

      t.timestamps
    end

    # name にユニーク制約を設定
    add_index :roles, [:name], unique: true
  end
end
```

```ruby
# app/db/migrate/*_create_account.rb
class CreateAccounts < ActiveRecord::Migration[7.0]
  def change
    create_table :accounts do |t|
      # name に NOT NULL 制約を設定
      t.string :name, :null => false

      # role に NOT NULL 制約と外部キーを設定
      t.belongs_to :role, null: false, foreign_key: true

      t.date :expiration_date

      t.timestamps
    end
  end
end
```

#### マイグレーション実行

以下コマンドで、マイグレーションを実行してください。

```sh
rake db:migrate
```

### モデルの修正

デフォルト値の設定やバリデーション設定は、モデルファイルの修正を行うことで実現する。
今回は、以下のように設定した。

```ruby
# app/models/role.rb
class Role < ApplicationRecord
  # name に一意制約とブランク禁止のバリデーションを追加
  validates :name, uniqueness: true, length: { minimum: 1 }, allow_blank: false
end
```

```ruby
# app/models/account.rb
class Account < ApplicationRecord
  belongs_to :role
  # インスタンス初期化後に呼び出すメソッドを指定し、
  # その中でデフォルト値を設定する
  after_initialize :set_default_values

  # name に一意制約とブランク禁止のバリデーションを追加
  validates :name, uniqueness: true, length: { minimum: 1 }, allow_blank: false

  private
  def set_default_values
    # 有効期限を 1 ヶ月に設定
    self.expiration_date ||= Time.current.since(1.month)
  end
end
```

## 動作確認

### dev server の起動

以下コマンドで、開発用にサーバーを起動します。

```sh
BINDING=0.0.0.0 bin/dev
```

### ブラウザでの確認

Scaffold の例の通り、role と account を作成したとして話を進めます。

`http://localhost:3000/roles`, `http://localhost:3000/accounts` にアクセスし、
挙動を確認してください。

気に入らない挙動があれば、生成されたコードを修正してください。


## 参考資料

- [【Ruby On Rails】DBに一意性を与えるユニーク制約（unique）の正しい記述場所 - Qiita](https://qiita.com/NedzumiNeko/items/bf8ad819fdbf087da947)
- [Active Record マイグレーション - Railsガイド](https://railsguides.jp/active_record_migrations.html)
- [Active Record バリデーション - Railsガイド](https://railsguides.jp/active_record_validations.html#allow-blank)
- [ActiveRecord でのデフォルト値設定 - Qiita](https://qiita.com/tearoom6/items/15d29b5ce8b3d0c0048c)
- [Active Record コールバック - Railsガイド](https://railsguides.jp/active_record_callbacks.html)
- [Railsでの日付操作でよく使うものまとめ - Qiita](https://qiita.com/mmmm/items/efda48f1ac0267c95c29)

