# Rails relation has_many

has_many_through をベースとしていますので、 has_many_through との diff で差分を確認してください。

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

## cocoon のインストール

多対多のフォームなので、アイテムを紐づけるために、動的にフォームの項目を増減させる必要がある。

動的にフォームを増減するための機能を提供する Gem である、 cocoon をインストールする。

動的なフォームを実現するためには、フロントエンドとバックエンドの両方で対応する必要があり、 cocoon もフロントエンド側の npm パッケージとバックエンド側の Gem を提供している。


### Gem インストール

Gemfile に `gem "cocoon"` を追加し、 `bundle install` を実行する。

```Gemfile
gem "cocoon"
```

## npm パッケージインストール

npm に `cocoon` も存在するが、それは jQuery が必須。

今回は jQuery 依存をなくしたいので、 jQuery 依存なしで cocoon 相当の機能を提供している `cocoon-js-vanilla` をインストールする。

```sh
yarn install cocoon-js-vanilla
```


## プログラムの修正

### `cocoon-js-vanilla` の有効化

`app/javascript/application.js` で、 `cocoon-js-vanilla` をインポートする。

```javascript
// Entry point for the build script in your package.json
import "@hotwired/turbo-rails"
import "./controllers"
import * as bootstrap from "bootstrap"
import "cocoon-js-vanilla"
```

### Controller の修正

```ruby
class OrdersController < ApplicationController
  before_action :set_order, only: %i[ show edit update destroy ]

  # GET /orders or /orders.json
  def index
    @orders = Order.preload(:items)
  end

  # GET /orders/1 or /orders/1.json
  def show
  end

  # GET /orders/new
  def new
    @order = Order.new
    @order.items.build
  end

...(snip)

    # Only allow a list of trusted parameters through.
    def order_params
      #params.fetch(:order, {})
      params.require(:order).permit(
        order_items_attributes: [:id, :order_id, :item_id, :_destroy]
      )
    end
end
```

### モデルの修正

cocoon が `accepts_nested_attributes_for` の仕組みを利用するので、
モデルにその定義を追加する。

今回は `Order -- OrderItem -- Item` の構成で多対多関係を作っているので、
モデル的には `Order -- OrderItem` 間の `accepts_nested_attributes_for` を設定すれば OK。

紐づける数が増減する、イコール、レコードの削除が発生する場合があるので、 `allow_destroy` を付ける必要があることに注意。

```ruby
class Order < ApplicationRecord
  has_many :order_items
  has_many :items, through: :order_items
  accepts_nested_attributes_for :order_items, allow_destroy: true
end
```

### ビューの修正

#### `index.html.erb`

ひとつひとつのオーダーの境目がわかりやすいように、もともとの繰り返し要素をボーダーで囲むようにした。

```erb
<p style="color: green"><%= notice %></p>

<h1>Orders</h1>

<div id="orders">
  <% @orders.each do |order| %>
    <div class="border m-1 p-1">
      <%= render order %>
      <p>
        <%= link_to "Show this order", order %>
      </p>
    </div>
  <% end %>
</div>

<%= link_to "New order", new_order_path %>
```

#### `_order.html.erb`

オーダー ID と、オーダーに紐づくアイテムを表示するように実装を追加。

```erb
<div id="<%= dom_id order %>">
  <label>order id: </label><%= order.id %>

  <div>
    <ul>
    <%- order.items.each { |i| %>
      <li>
        <%= link_to("item id: #{i.id}, item name: #{i.name}", item_url(i)) %>
      </li>
    <%- } %>
    </ul>
  </div>
</div>
```

#### `_form.html.erb`

オーダーに紐づくアイテムを複数設定できるように、フォーム定義を追加。

```ruby
<%= form_with(model: order) do |form| %>
  <% if order.errors.any? %>
    <div style="color: red">
      <h2><%= pluralize(order.errors.count, "error") %> prohibited this order from being saved:</h2>

      <ul>
        <% order.errors.each do |error| %>
          <li><%= error.full_message %></li>
        <% end %>
      </ul>
    </div>
  <% end %>

  <div id='items'> <!-- アイテム用フォームを表示する div 要素 -->
    <%= form.fields_for :order_items do |order_item| %> <!-- fields_for で、紐づけられているアイテムごとに繰り返す -->
      <%= render 'order_item_fields', :f => order_item %> <!-- アイテム用フォーム描画定義。別ファイルを render することで実現している -->
    <% end %>
    <%= link_to_add_association 'add item', form, :order_items %> <!-- アイテム用フォームの追加ボタン -->
  </div>

  <div>
    <%= form.submit %>
  </div>
<% end %>
```

#### `_order_item_fields.html.erb`

アイテムごとに表示するフォーム定義を記述。

```ruby
<div class="nested-fields">
  <div class="field">
    <%= f.label :id, style: "display: block" %>
    <%= f.hidden_field :order_id, :value => @order.id %>
    <%= f.collection_select :item_id, Item.all, :id, :name %>
  </div>
  <%= link_to_remove_association "remove item", f %>
</div>
```


## 動作確認

開発モードでサーバーを起動。

```sh
BINDING=0.0.0.0 ./bin/dev
```

1. `http://localhost:3000/items` を開き、アイテムを追加
2. `http://localhost:3000/orders` を開き、オーダーを追加
    - ここで、「1.」で追加したアイテムを選べるようになっている


以上。


# 参考資料

- [nathanvda/cocoon: Dynamic nested forms using jQuery made easy; works with formtastic, simple_form or default forms](https://github.com/nathanvda/cocoon)
- [oddcamp/cocoon-vanilla-js: A vanilla JS replacement for (Rails) Cocoon's jQuery script](https://github.com/oddcamp/cocoon-vanilla-js)
- [脱jQueryによるcocoonの使い方 - Qiita](https://qiita.com/yuya-yuya/items/3585ffbfbaf340052596)


