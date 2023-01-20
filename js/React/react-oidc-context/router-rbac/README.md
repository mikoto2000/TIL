# react-oidc-context router firststep

## 動作確認方法

以下ユーザーを作成済みの Keycloak が立ち上がる。

- username: test, password: test, role: admin
- username: normal, password: test, role: N/A

アプリを立ち上げログインすると、 `test` ユーザーにだけ `admin` 用のページへのリンクが表示される。
また、ユーザー `normal` が `admin` 用のページに URL 直打ちで入っても、本来のページが表示されない。


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

