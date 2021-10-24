# JPEG ファイルへの GPS 情報追加サンプル

## コンパイル

```sh
./mvnw compile
```

## 実行

`test.jpg` という名前の jpeg ファイルをプロジェクトルートに配置したうえで、以下コマンドを実行。

```sh
./mvnw exec:java
```

`test_dest.jpg` という名前の jpeg ファイルがプロジェクトルートに生成される。


## プロジェクト作成

```sh
mvn archetype:generate \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false \
  -DgroupId=dev.mikoto2000.study.exif \
  -DartifactId=firststep

mvn -N io.takari:maven:wrapper
```

この後、依存関係の追加や `exec-maven-plugin` の導入を行った。

## Exif 確認ツール

- [EXIF確認君 - 画像情報解析ツール](http://exif-check.org/)
- [Exif読取り君 -ソフトウェアの部屋-](https://enrai.matrix.jp/exif.html)


## 参考資料

- [Maven を使った Java project 作成方法 - Qiita](https://qiita.com/hide/items/6593f3f02c3f28e57f2d)
- [Java プログラムを Maven から実行する方法 - Qiita](https://qiita.com/hide/items/0c8795054219d04e5e98)
- [mavenのコンパイルjavaバージョンを指定する - Qiita](https://qiita.com/kz1202/items/aa0f2f110908ff7ff530)
- [【Java】JPEGのEXIFを取得・更新する方法 | 水戸スヤのSE備忘録](https://mitosuya.net/edit-exif)
- [Java Source Code: org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants](http://www.javased.com/index.php?source_dir=sanselan/src/main/java/org/apache/commons/imaging/formats/tiff/constants/GpsTagConstants.java)
- [ディジタルスチルカメラ用 - DC-008-2010_J.pdf](https://www.cipa.jp/std/documents/j/DC-008-2010_J.pdf)
- [Java Examples for org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants.GPS_TAG_GPS_IMG_DIRECTION](https://www.javatips.net/api/org.apache.commons.imaging.formats.tiff.constants.gpstagconstants.gps.tag.gps.img.direction)
- [EXIF確認君 - 画像情報解析ツール](http://exif-check.org/)

