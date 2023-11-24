# react-oidc-context activity tracking

router-rbac にコードを追加するかタイで作っています。
diff で差分を確認してください。

## 動作確認方法

以下ユーザーを作成済みの Keycloak が立ち上がる。

- username: test, password: test, role: admin
- username: normal, password: test, role: N/A

アプリを立ち上げログインすると、 `test` ユーザーにだけ `admin` 用のページへのリンクが表示される。
また、ユーザー `normal` が `admin` 用のページに URL 直打ちで入っても、本来のページが表示されない。

画面に触っている間はトークンの自動更新がし続けられるが、画面に触らなくなるとトークンの自動更新がされず、ログアウトする。

※ 設定が面倒なので Keycloak のトークン有効期限がデフォルトのままになっています。適宜、動作確認可能な程度に有効期限を短くして動かしてください。

## start environment

```sh
docker compose up -d
```

## create project

```sh
npx create-react-app /project --template typescript
cp -r /project/* ./project/
sed -i -e "s/\"project\"/\"react-oidc-example\"/g" ./project/package.json ./project/package-lock.json
```

### add dependencies

```sh
npm install react-oidc-context react-router react-router-dom
```

