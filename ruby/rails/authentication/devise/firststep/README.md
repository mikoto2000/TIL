---
title: devise で認証を実現する
author: mikoto2000
date: 2024/9/29
---

# プロジェクト作成

```sh
rails new app --javascript importmap --css tailwind --asset-pipeline sprockets --no-api
```

# トップページの作成

## controller 作成

```sh
rails generate controller top
```

■ `app/controllers/top_controller.rb`

```sh
class TopController < ApplicationController
  def index
  end
end
```

## view 作成

### view ファイル作成

■ `app/view/top/index.html.erb`

```sh
<h1>Welcome top page</h1>
```

## ルーティング変更

■ `config/routes.rb`

```sh
...(snip)
  # Defines the root path route ("/")
  # root "posts#index"
  root "top#index"
...(snip)

## 動作確認

```sh
BINDING=0.0.0.0 ./bin/dev
```

ホスト PC のブラウザで、 `http://localhost:3000` にアクセスすると、 `Welcome top paga` とだけ記載されたページが表示される。

OK.


