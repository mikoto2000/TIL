SearchMessages
==============

OAuth ログインしたユーザーのメールを検索します。


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
7. `./src/main/resources/query.properties` の `QUERY` プロパティ(検索文字列) を変更


ビルド
------

```sh
./gradlew jar
```


実行
----

```sh
java -jar ./build/libs/GmailSearchMessages-1.0.jar
```

1. コンソールに表示された URL にアクセスし、Google にログインする
   (ブラウザが開ける環境であれば自動で標準ブラウザが開く)
2. 表示されたコードをコピーし、コンソールに張り付けて Enter
3. コンソールに、検索条件にヒットしたメッセージの Subject と本文が表示される



参考資料
--------

- [Gmail (Gmail API v1 (Rev. 96) 1.25.0)](https://developers.google.com/resources/api-libraries/documentation/gmail/v1/java/latest/com/google/api/services/gmail/Gmail.html)
- [Gmail.Users (Gmail API v1 (Rev. 96) 1.25.0)](https://developers.google.com/resources/api-libraries/documentation/gmail/v1/java/latest/com/google/api/services/gmail/Gmail.Users.html)
- [Gmail.Users.Messages (Gmail API v1 (Rev. 96) 1.25.0)](https://developers.google.com/resources/api-libraries/documentation/gmail/v1/java/latest/com/google/api/services/gmail/Gmail.Users.Messages.html)
- [Gmail.Users.Messages.List (Gmail API v1 (Rev. 96) 1.25.0)](https://developers.google.com/resources/api-libraries/documentation/gmail/v1/java/latest/com/google/api/services/gmail/Gmail.Users.Messages.List.html)
- [Gmail.Users.Messages.Get (Gmail API v1 (Rev. 96) 1.25.0)](https://developers.google.com/resources/api-libraries/documentation/gmail/v1/java/latest/com/google/api/services/gmail/Gmail.Users.Messages.Get.html#setFormat-java.lang.String-)
- [MessagePartHeader (Gmail API v1 (Rev. 96) 1.25.0)](https://developers.google.com/resources/api-libraries/documentation/gmail/v1/java/latest/com/google/api/services/gmail/model/MessagePartHeader.html)
- [MessagePart (Gmail API v1 (Rev. 96) 1.25.0)](https://developers.google.com/resources/api-libraries/documentation/gmail/v1/java/latest/com/google/api/services/gmail/model/MessagePart.html)
- [MessagePartBody (Gmail API v1 (Rev. 96) 1.25.0)](https://developers.google.com/resources/api-libraries/documentation/gmail/v1/java/latest/com/google/api/services/gmail/model/MessagePartBody.html)


