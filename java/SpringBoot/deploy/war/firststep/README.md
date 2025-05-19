# Spring Boot WAR サンプルプロジェクト (firststep)

このサンプルプロジェクトは、Spring Boot アプリケーションを WAR としてパッケージし、Tomcat コンテナ上で実行する方法を示すデモです。

## 前提条件

- Docker / Docker Compose
- Java 21 以上 (ローカルでビルドする場合)
- Maven もしくは付属の Maven Wrapper (`./mvnw` / `mvnw.cmd`)

## ディレクトリ構成 (抜粋)

```text
.
├── compose.yaml         # Docker Compose 定義ファイル
├── mvnw, mvnw.cmd       # Maven Wrapper
├── pom.xml              # Maven プロジェクト設定
├── src/                 # ソースコード
├── webapp_dev/          # Dev 環境用のTomcat webapps フォルダ
└── webapp_prod/         # Prod 環境用のTomcat webapps フォルダ
```

## 動作確認手順

### 1. コンテナの起動と接続

```bash
docker compose up -d
docker compose exec dev-java bash
```

### 2. WAR ファイルのビルド

アプリケーションをビルドし、`target/` フォルダに WAR ファイルを出力します。

```bash
./mvnw clean package -DskipTests
```

### 2. WAR ファイルの配置

ビルドした WAR ファイルを、Tomcat の webapps ディレクトリとしてマウントする `webapp_dev` / `webapp_prod` 配下へコピーします。

```bash
cp target/firststep-0.0.1-SNAPSHOT.war webapp_dev/ROOT.war
cp target/firststep-0.0.1-SNAPSHOT.war webapp_prod/ROOT.war
```

### 3. Dev 環境の確認

ブラウザで以下の URL にアクセスし、実行中の Spring プロファイルが `dev` と表示されることを確認します。

http://localhost:8080/

### 4. Prod 環境の確認

ブラウザで以下の URL にアクセスし、実行中の Spring プロファイルが `prod` と表示されることを確認します。

http://localhost:8081/

### コンテナの停止

```bash
docker compose down
```
