# Spring Data Rest Validation Example

many-to-many をベースとしているので、 many-to-many ディレクトリとの差分を確認してください。

### `pom.xml` に Spring Data REST の依存を追加

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
			<version>3.1.5</version>
		</dependency>
```

## 開発環境

VSCode DevContainer 拡張機能を使って、 VSCode で開発が行えます。

ただ、作りが甘く、一部初回起動時に権限変更作業が必要な作りになっています。

### 初回起動時

maven データ格納用ディレクトリのオーナーが root になっているので、 vscode ユーザーに変更する。

```sh
sudo chown 1000:1000 ~/.m2/
```

