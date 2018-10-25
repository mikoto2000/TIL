ListLabel
=========

OAuth ログインしたユーザーの Gmail アカウントで定義しているラベル一覧を表示します。


事前準備
--------

[Google Cloud Console](https://console.cloud.google.com/home/dashboard) でプロジェクトと認証情報を作成する。

1. トップページ -> `API とサービス` -> `認証情報`
2. `認証情報を作成` ボタン -> `OAuth クライアント ID`
3. `その他` にチェック -> 名前を入力
    - 名前: `Gmail Search Messages`
4. 「OAuth クライアントダイアログ」が表示されるので `OK`
5. 認証情報の一覧に、今追加したクライアント ID が追加されているので、右側のダウンロードアイコンを押下
6. ダウンロードした `json` を `./src/main/resources/credentials.json` に配置する


ビルド
------

```sh
./gradlew jar
```


実行
----

```sh
java -jar ./build/libs/GmailListLabels-1.0.jar
```

1. コンソールに表示された URL にアクセスし、Google にログインする
   (ブラウザが開ける環境であれば自動で標準ブラウザが開く)
2. 表示されたコードをコピーし、コンソールに張り付けて Enter
3. コンソールにラベル一覧が表示される


