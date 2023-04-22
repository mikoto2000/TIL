# Rails OIDC

firststep をベースとしていますので、 firststep との diff で差分を確認してください。

## 前提条件

OIDC の ID プロバイダと、それに対するクライアントの設定が完了していること。

動作確認では Cognito のユーザープールを利用している。


## 開発環境

### Cognito ユーザープール設定の定義

コンテナの環境変数として定義してしまう。

`.env` ファイルを作成し、必要な情報を記載。今回は以下のようにした。

```
AWS_REGION=ap-northeast-1
AWS_COGNITO_USERPOOL_ID=ap-northeast-1_XXXXXXXXX
AWS_COGNITO_CLIENT_ID=YYYYYYYYYYYYYYYYYYYYYYYYYY
AWS_COGNITO_REDIRECT_URL=http://localhost:3000/auth/openid_connect/callback
AWS_COGNITO_END_SESSION_ENDPOINT=https://ZZZZZZZZZZZZZZZZZZZZZZ.auth.ap-northeast-1.amazoncognito.com/logout?client_id=YYYYYYYYYYYYYYYYYYYYYYYYYY&logout_uri=http://localhost:3000
```

そして、 `docker-compose.yaml` の `app` サービスの環境変数に以下を追加。

```yaml
...(snip)
      AWS_REGION: ${AWS_REGION}
      AWS_COGNITO_USERPOOL_ID: ${AWS_COGNITO_USERPOOL_ID}
      AWS_COGNITO_CLIENT_ID: ${AWS_COGNITO_CLIENT_ID}
      AWS_COGNITO_REDIRECT_URL: ${AWS_COGNITO_REDIRECT_URL}
      AWS_COGNITO_END_SESSION_ENDPOINT: ${AWS_COGNITO_END_SESSION_ENDPOINT}
...(snip)
```

### 開発環境立ち上げ

docker compose で開発環境を立ち上げます。

構築手順については、 [./.devcontainer/dockerfile/app/Dockerfile](./.devcontainer/dockerfile/app/Dockerfile) を参照してください。

```sh
docker compose up
```

### 開発コンテナへの接続

以下コマンドで、 ruby, nodejs, yarn の環境構築済みのコンテナに入ります。

```sh
docker compose exec app bash
```

## OIDC の導入

### Gem のインストール

Gemfile に以下 3 つの項目を追加。

```Gemfile
gem 'omniauth'
gem 'omniauth-rails_csrf_protection'
gem 'omniauth_openid_connect'
```

- `omniauth`: マルチプロバイダー認証を提供するライブラリ。今回は OIDC 認証を行うために利用
- `omniauth-rails_csrf_protection`: OmniAuth と Rails の組み合わせに、 CSRF 対策を追加する Gem
- `omniauth_openid_connect`: omniauth の枠組みで OIDC を行う OmniAuth プロバイダー
- `pundit`: 認可周りの面倒を見てくれる Gem

追記したら、 `bundle install` でインストールする。


### OIDC の設定

`config/initializers/omniauth.rb` を作成し、 OIDC に必要な情報を定義する。

```ruby
# config/initializers/omniauth.rb
Rails.application.config.middleware.use OmniAuth::Builder do
  provider :openid_connect, {
    discovery: true,
    issuer: "https://cognito-idp.#{ENV['AWS_REGION']}.amazonaws.com/#{ENV['AWS_COGNITO_USERPOOL_ID']}",
    client_auth_method: 'jwks',
    client_options: {
      identifier: ENV['AWS_COGNITO_CLIENT_ID'],
      redirect_uri: ENV['AWS_COGNITO_REDIRECT_URL'],
      end_session_endpoint: ENV['AWS_COGNITO_END_SESSION_ENDPOINT']
    }
  }
end
```

### セッションコントローラーの作成

ログイン・ログアウトと連動してセッションを生成・削除するためのコントローラーを作成する。

#### セッションコントローラーの生成

```sh
rails generate controller session
```

#### セッションコントローラーの実装

```ruby
# app/controllers/session_controller.rb
class SessionController < ApplicationController
  # セッション開始時の処理
  # ユーザー名とグループを持つ Hash としてユーザー情報をセッションに格納
  def create
    session[:user] = {
      username: request.env['omniauth.auth']['extra']['raw_info']['cognito:username'],
      groups: request.env['omniauth.auth']['extra']['raw_info']['cognito:groups']
    }
    redirect_to root_url
  end

  # セッション終了時の処理
  # 全セッション情報削除
  def delete
    reset_session
    redirect_to root_url
  end
end
```


#### ルートの設定

`config/routes.rb` に、セッションコントローラーへのルートを追加。

```ruby
# config/route.rb
Rails.application.routes.draw do
  resources :accounts
  resources :roles
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # ログイン時は、OmniAuth がここを叩く
  get 'auth/:provider/callback', to: 'session#create'

  # ログアウト form で、ここを叩くようにする
  get 'auth/logout', to: 'session#delete'

  # Defines the root path route ("/")
  root "application#index"
end
```

### ログイン・ログアウトボタンの設置

`app/views/layouts/application.html.erb` に設置する。

```erb
# app/views/layouts/application.html.erb
<!DOCTYPE html>
<html>
  <head>
    <title>App</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <%= csrf_meta_tags %>
    <%= csp_meta_tag %>

    <%= stylesheet_link_tag "application", "data-turbo-track": "reload" %>
    <%= javascript_include_tag "application", "data-turbo-track": "reload", defer: true %>
  </head>

  <body>
<div>
<% if !session[:user].nil? %>
  user_info: <%= session[:user] %>

  <%= form_tag('auth/logout', method: 'get', data: {turbo: false}) do %>
  <button type='submit'>Logout</button>
  <%- end %>

  <%= yield %>

<% else %>

  <%= form_tag('/auth/openid_connect', method: 'post', data: {turbo: false}) do %>
  <button type='submit'>Login</button>
  <%- end %>

  <p>ログインしてください</p>

<% end %>

</div>
  </body>
</html>
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

- [omniauth/omniauth: OmniAuth is a flexible authentication system utilizing Rack middleware.](https://github.com/omniauth/omniauth)
- [cookpad/omniauth-rails_csrf_protection: Provides CSRF protection on OmniAuth request endpoint on Rails application.](https://github.com/cookpad/omniauth-rails_csrf_protection)
- [omniauth/omniauth_openid_connect](https://github.com/omniauth/omniauth_openid_connect)
- [Azure AD OIDC を OmniAuth で実現する - blog.osa.in.net](https://blog.osa.in.net/azure-ad-oidc-omniauth/)
- [Rails+omniauth-google-oauth2でGoogleログイン(devise無し)](https://zenn.dev/batacon/articles/e9b4a88ede2889)
- [sessionの中に何があるか知りたい。そんな時はto_hashメソッドを使おう。 Rails - Qiita](https://qiita.com/hirokishirai/items/7ee86f095649306a16ba)

