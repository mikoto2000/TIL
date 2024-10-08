---
title: Rails の Devise で認証を実現したプロジェクトに、 Pundit で認可を追加する
author: mikoto2000
date: 2024/9/29
---

# 前提

[Rails の Devise で認証を実現する](https://mikoto2000.blogspot.com/2024/09/rails-devise.html) からの続き。

`admin` と `user` で取得できるリソースを分ける。


# アカウントに role カラムを追加

## マイグレーションファイルを作成

```sh
rails generate migration add_role_to_accounts
```

## マイグレーションファイルで、 role カラムを追加

■`db/migrate/20241008113604_add_role_to_accounts.rb`:

```rb
class AddRoleToAccounts < ActiveRecord::Migration[7.2]
  def change
    add_column :accounts, :role, :string, default: "user"
  end
end
```

## マイグレーション実行

```sh
rails db:migrate
```

管理者は、 DB から直接 role カラムを修正することとし、ログインのビューは変更しない。


# リソースの作成

## 一般ユーザーも触れるリソースの追加

### リソースの作成

```sh
rails generate scaffold AllWelcomeResource name:string
rails db:migrate
```


## 管理者のみ触れるリソースの追加

### リソースの作成

```sh
rails generate scaffold AdminOnlyResource name:string
rails db:migrate
```

## トップ画面の更新

各リソースの index へ行けるように、トップ画面にリンクを作成。

■ `app/views/top/index.html.erb`:

```erb
<ul>
  <li>一般歓迎リソース</li>
  <li>
    <ul>
      <li>
        <%= link_to :all_welcome_resources, all_welcome_resources_path %>
      </li>
    </ul>
  </li>
  <li>管理者リソース</li>
  <li>
    <ul>
      <li>
        <%= link_to :admin_only_resources, admin_only_resources_path %>
      </li>
    </ul>
  </li>
</ul>
```

# Pundit ジェムのインストール

```sh
bundle add pundit
rails generate pundit:install
```

# AdminOnlyResource への認可処理追加

ざっくり手順は、以下。

1. コントローラーに認可処理に必要なボイラープレートを記載
2. ポリシーファイルに index, show, create, new, update, edit, destroy の 7 種類に対する認可ポリシーを記述する。


## コントローラーにボイラープレートを記載

### ApplicationController

今回は、 users テーブルではなく accounts テーブルを使って認証を行っているため、ログイン済みユーザーの取得には `current_account` 関数を使う必要がある。

Pundit のデフォルトでは、 `current_user` 関数を使う用になっているため、この定義を上書きする。

■ `app/controllers/application_controller.rb`:

```rb
class ApplicationController < ActionController::Base
  include Pundit    # この 4 行を追加
  def pundit_user   # この 4 行を追加
    current_account # この 4 行を追加
  end               # この 4 行を追加

  before_action :authenticate_account!
  # Only allow modern browsers supporting webp images, web push, badges, import maps, CSS nesting, and CSS :has.
  allow_browser versions: :modern
end
```

### AdminOnlyResourceController

■ `app/controllers/admin_only_resources_controller.rb`:

```rb
class AdminOnlyResourcesController < ApplicationController
  include Pundit                                                      # この 2 行を追加
  rescue_from Pundit::NotAuthorizedError, with: :user_not_authorized  # この 2 行を追加

  before_action :set_admin_only_resource, only: %i[ show edit update destroy ]

  # GET /admin_only_resources or /admin_only_resources.json
  def index
    authorize AdminOnlyResource # この行を追加
    @admin_only_resources = AdminOnlyResource.all
  end

  # GET /admin_only_resources/1 or /admin_only_resources/1.json
  def show
    authorize AdminOnlyResource # この行を追加
  end

  # GET /admin_only_resources/new
  def new
    authorize AdminOnlyResource # この行を追加
    @admin_only_resource = AdminOnlyResource.new
  end

  # GET /admin_only_resources/1/edit
  def edit
    authorize AdminOnlyResource # この行を追加
  end

  # POST /admin_only_resources or /admin_only_resources.json
  def create
    authorize AdminOnlyResource # この行を追加
    @admin_only_resource = AdminOnlyResource.new(admin_only_resource_params)

    respond_to do |format|
      if @admin_only_resource.save
        format.html { redirect_to @admin_only_resource, notice: "Admin only resource was successfully created." }
        format.json { render :show, status: :created, location: @admin_only_resource }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @admin_only_resource.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /admin_only_resources/1 or /admin_only_resources/1.json
  def update
    authorize AdminOnlyResource # この行を追加
    respond_to do |format|
      if @admin_only_resource.update(admin_only_resource_params)
        format.html { redirect_to @admin_only_resource, notice: "Admin only resource was successfully updated." }
        format.json { render :show, status: :ok, location: @admin_only_resource }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @admin_only_resource.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /admin_only_resources/1 or /admin_only_resources/1.json
  def destroy
    authorize AdminOnlyResource # この行を追加
    @admin_only_resource.destroy!

    respond_to do |format|
      format.html { redirect_to admin_only_resources_path, status: :see_other, notice: "Admin only resource was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_admin_only_resource
      @admin_only_resource = AdminOnlyResource.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def admin_only_resource_params
      params.require(:admin_only_resource).permit(:name)
    end

    def user_not_authorized                                             # この 4 行を追加
      flash[:alert] = "You are not authorized to perform this action."  # この 4 行を追加
      redirect_to(request.referer || root_path)                         # この 4 行を追加
    end                                                                 # この 4 行を追加
end
```

- `include Pundit`: Pundit の機能を使えるようにする
- `rescue_from Pundit::NotAuthorizedError, with: :user_not_authorized`: 認証失敗時にトップページへリダイレクト
- `authorize <モデル名>`: 認可処理、認可に失敗すると、 `Pundit::NotAuthorizedError` が発生する


## ポリシーファイルに index, show, create, new, update, edit, destroy の 7 種類に対する認可ポリシーを記述する。

今回は、 `AdminOnlyResource` のポリシーを作成するので、
`app/policies/admin_only_resource_policy.rb` にポリシーを記述する。

### ポリシーファイルの生成

■ `app/policies/admin_only_resource_policy.rb`:

```rb
class AdminOnlyResourcePolicy < ApplicationPolicy
  def index?
    user.role == "admin"
  end
  def show?
    user.role == "admin"
  end
  def new?
    user.role == "admin"
  end
  def edit?
    user.role == "admin"
  end
  def create?
    user.role == "admin"
  end
  def update?
    user.role == "admin"
  end
  def destroy?
    user.role == "admin"
  end
end
```

これで、 `role` が `admin` のユーザー以外が見れないようになる。

A5SQL で role を user にしたり admin にしたりして試してみよう。

OK.


# 参考資料

- [varvet/pundit: Minimal authorization through OO design and pure Ruby classes](https://github.com/varvet/pundit)
- [Pundit + Railsで認可の仕組みをシンプルに作る #Ruby - Qiita](https://qiita.com/zaru/items/8bf7b41b33f3f55bd27d)

