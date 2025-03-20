# CLAUDE.md - Maven プロジェクトガイド

## ビルド/実行コマンド
- ビルド: `./mvnw clean package`
- 実行: `java -jar target/hello-world-1.0-SNAPSHOT.jar`
- 実行（ワンコマンド）: `./mvnw exec:java`
- 単体テスト: `./mvnw test`
- 特定のテスト実行: `./mvnw test -Dtest=TestClassName`
- クリーン: `./mvnw clean`

## コードスタイルガイドライン
- インデント: 4スペース
- 命名規則: 
  - クラス: UpperCamelCase
  - メソッド/変数: lowerCamelCase
  - 定数: ALL_CAPS
- インポート: 明示的なインポート推奨、ワイルドカード避ける
- パッケージ構造: 機能/ドメイン別に整理
- エラー処理: チェック例外は適切にログ記録、キャッチブロックは具体的に
- Javaバージョン: Java 21