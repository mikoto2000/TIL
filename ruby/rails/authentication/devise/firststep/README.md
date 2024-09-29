---
title: devise で認証を実現する
author: mikoto2000
date: 2024/9/29
---

# プロジェクト作成

```sh
rails new app --javascript importmap --css tailwind --asset-pipeline sprockets -d postgresql --no-api
```

# データベース設定

■ `config/database.yml`

```yml
# PostgreSQL. Versions 9.3 and up are supported.
#
# Install the pg driver:
#   gem install pg
# On macOS with Homebrew:
#   gem install pg -- --with-pg-config=/usr/local/bin/pg_config
# On Windows:
#   gem install pg
#       Choose the win32 build.
#       Install PostgreSQL and put its /bin directory on your path.
#
# Configure Using Gemfile
# gem "pg"
#
default: &default
  adapter: postgresql
  encoding: unicode
  # For details on connection pooling, see Rails configuration guide
  # https://guides.rubyonrails.org/configuring.html#database-pooling
  pool: <%= ENV.fetch("RAILS_MAX_THREADS") { 5 } %>
  database: public
  host: postgres
  username: admin
  password: password

development:
  <<: *default

  # The specified database role being used to connect to PostgreSQL.
  # To create additional roles in PostgreSQL see `$ createuser --help`.
  # When left blank, PostgreSQL will use the default role. This is
  # the same name as the operating system user running Rails.
  #username: app

  # The password associated with the PostgreSQL role (username).
  #password:

  # Connect on a TCP socket. Omitted by default since the client uses a
  # domain socket that doesn't need configuration. Windows does not have
  # domain sockets, so uncomment these lines.
  #host: localhost

  # The TCP port the server listens on. Defaults to 5432.
  # If your server runs on a different port number, change accordingly.
  #port: 5432

  # Schema search path. The server defaults to $user,public
  #schema_search_path: myapp,sharedapp,public

  # Minimum log levels, in increasing order:
  #   debug5, debug4, debug3, debug2, debug1,
  #   log, notice, warning, error, fatal, and panic
  # Defaults to warning.
  #min_messages: notice

# Warning: The database defined as "test" will be erased and
# re-generated from your development database when you run "rake".
# Do not set this db to the same as development or production.
test:
  <<: *default
  database: test

# As with config/credentials.yml, you never want to store sensitive information,
# like your database password, in your source code. If your source code is
# ever seen by anyone, they now have access to your database.
#
# Instead, provide the password or a full connection URL as an environment
# variable when you boot the app. For example:
#
#   DATABASE_URL="postgres://myuser:mypass@localhost/somedatabase"
#
# If the connection URL is provided in the special DATABASE_URL environment
# variable, Rails will automatically merge its configuration values on top of
# the values provided in this file. Alternatively, you can specify a connection
# URL environment variable explicitly:
#
#   production:
#     url: <%= ENV["MY_APP_DATABASE_URL"] %>
#
# Read https://guides.rubyonrails.org/configuring.html#configuring-a-database
# for a full overview on how database connection configuration can be specified.
#
production:
  <<: *default
  database: production
```

# トップページの作成

## controller 作成

```sh
rails generate controller top
```

<!--
■ `app/controllers/top_controller.rb`

```rb
class TopController < ApplicationController
  def index
  end
end
```
-->

## view 作成

### view ファイル作成

■ `app/view/top/index.html.erb`

```html
<h1>Welcome top page</h1>
```

## ルーティング変更

■ `config/routes.rb`

```rb
...(snip)
  # Defines the root path route ("/")
  # root "posts#index"
  root to: "top#index"
...(snip)
```

## 動作確認

```sh
BINDING=0.0.0.0 ./bin/dev
```

ホスト PC のブラウザで、 `http://localhost:3000` にアクセスすると、 `Welcome top paga` とだけ記載されたページが表示される。

OK.


# devise で認証をする

## devise gem のインストール

Gemfile に `gem "devise"` を追加し、 `bundle install` する。

## プロジェクトに devise をインストール

実行後、ガイドが表示されるので、基本的にガイドに従えば OK。

```sh
$ rails generate devise:install
      create  config/initializers/devise.rb
      create  config/locales/devise.en.yml
===============================================================================

Depending on your application's configuration some manual setup may be required:

  1. Ensure you have defined default url options in your environments files. Here
     is an example of default_url_options appropriate for a development environment
     in config/environments/development.rb:

       config.action_mailer.default_url_options = { host: 'localhost', port: 3000 }

     In production, :host should be set to the actual host of your application.

     * Required for all applications. *

  2. Ensure you have defined root_url to *something* in your config/routes.rb.
     For example:

       root to: "home#index"
     
     * Not required for API-only Applications *

  3. Ensure you have flash messages in app/views/layouts/application.html.erb.
     For example:

       <p class="notice"><%= notice %></p>
       <p class="alert"><%= alert %></p>

     * Not required for API-only Applications *

  4. You can copy Devise views (for customization) to your app by running:

       rails g devise:views
       
     * Not required *

===============================================================================
```

「1.」は定義済み。「2.」も定義済み。

## 「3.」の実施

■ `app/views/layouts/application.html.erb`

```html
<!DOCTYPE html>
<html>
  <head>
    <title><%= content_for(:title) || "App" %></title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <%= csrf_meta_tags %>
    <%= csp_meta_tag %>

    <%= yield :head %>

    <link rel="manifest" href="/manifest.json">
    <link rel="icon" href="/icon.png" type="image/png">
    <link rel="icon" href="/icon.svg" type="image/svg+xml">
    <link rel="apple-touch-icon" href="/icon.png">
    <%= stylesheet_link_tag "tailwind", "inter-font", "data-turbo-track": "reload" %>
    <%= stylesheet_link_tag "application", "data-turbo-track": "reload" %>
    <%= javascript_importmap_tags %>
  </head>

  <body>
    <main class="container mx-auto mt-28 px-5 flex">
    <div>
      <p class="notice"><%= notice %></p> <!-- この行を追加 -->
      <p class="alert"><%= alert %></p> <!-- この行を追加 -->
    </div>
      <%= yield %>
    </main>
  </body>
</html>
```

「4.」はログインの View を改造したい場合に行う。
今回は firststep という事で、デフォルトのままやってみる。

## devise 用モデルの作成

firststep とは関係のない諸々のわけがあって、今回は `accounts` テーブルにログイン情報を保存するように設定していく。

```sh
rails generate devise Accounts
rails db:migrate
```

## devise 用コントローラーの作成

firststep とは関係のない諸々のわけがあって、今回は `accounts` テーブルにログイン情報を保存するように設定していく。

```sh
rails generate devise:controllers account
```

## devise 用ルートの設定

■ `app/config/routes.rb`

```rb
Rails.application.routes.draw do
  devise_for :account # この行を追加

  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Reveal health status on /up that returns 200 if the app boots with no exceptions, otherwise 500.
  # Can be used by load balancers and uptime monitors to verify that the app is live.
  get "up" => "rails/health#show", as: :rails_health_check

  # Render dynamic PWA files from app/views/pwa/*
  get "service-worker" => "rails/pwa#service_worker", as: :pwa_service_worker
  get "manifest" => "rails/pwa#manifest", as: :pwa_manifest

  # Defines the root path route ("/")
  # root "posts#index"
  root to: "top#index"
end
```

## ページ表示時に強制的にログインを促す

■ `app/controllers/application_controller.rb`

```rb
class ApplicationController < ActionController::Base
  before_action :authenticate_account! # この行を追加
  # Only allow modern browsers supporting webp images, web push, badges, import maps, CSS nesting, and CSS :has.
  allow_browser versions: :modern
end
```

この場合、ApplicationController に記載したので、全ページにログインが必要という事になる。
特定ページのみであれば、そのページの controller に仕込むことになるっぽい？


## 動作確認

ホスト PC のブラウザで、 `http://localhost:3000` にアクセスすると、ログイン画面が表示される。

右上の `Sign up` を押下し、ユーザー登録を済ませると、 `Welcome! You have signed up successfully.` という言葉とともに、トップページが表示される。

(スタイルを何も定義していないので一行に表示されてしまうが、今回は無視する)


