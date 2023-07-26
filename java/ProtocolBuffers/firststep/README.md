---
title: Java で Protocol Buffers をやる
author: mikoto2000
date: 2023/7/26
---

# 前提

- Windows 11 Pro 22H2 22621.1848
- Docker Desktop version 4.20.1 (110738)
- 使用する Docker イメージ: `eclipse-temurin:17`


# Maven プロジェクト作成

[spring initializr で、 Mavem + Java17 + Lombok のプロジェクトを作成し、展開する](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.1.2&packaging=jar&jvmVersion=17&groupId=dev.mikoto2000.javastudy.protocolbuffers&artifactId=firststep&name=firststep&description=Protocol%20Buffers%20demo%20project%20for%20Spring%20Boot&packageName=dev.mikoto2000.javastudy.protocolbuffers.firststep&dependencies=lombok)


# 開発用コンテナ起動

共通ボリュームとして `maven_data` を利用しているので、あらかじめボリュームを作成しておいてください。

```sh
docker volume create maven_data
```

そのうえで、 `docker compose` コマンドで開発用コンテナを立ち上げます。

```sh
docker compose up -d
```


# 開発用コンテナへ接続

以下コマンドで `app` コンテナへ接続し、その中で開発を行ってください。

```sh
docker compose exec app bash
```


# Protocol Buffers に必要なパッケージのインストール

```sh
apt update
apt install -y protobuf-compiler
```


# Protocol Buffers のコンパイルに必要なライブラリを `pom.xml` に追加

- `protobuf-java` : 生成する Java ソースコードをコンパイルするためのライブラリ
- `protobuf-java-util` : Protocol Buffers 用便利 API を使うためのライブラリ

```xml
		<dependency>
			<groupId>com.google.protobuf</groupId>
			<artifactId>protobuf-java</artifactId>
			<version>3.23.0</version>
		</dependency>
		<dependency>
			<groupId>com.google.protobuf</groupId>
			<artifactId>protobuf-java-util</artifactId>
			<version>3.23.0</version>
		</dependency>
```

# `.proto` の作成

通信フォーマットを定義する `.proto` ファイルを作成する。

今回は、 `${PROJECT_ROOT}/proto/MemberInfo.proto` に以下内容でファイルを作成。

```proto
syntax = "proto3";

option java_multiple_files = false;
option java_package="dev.mikoto2000.javastudy.protocolbuffers.firststep.model";

message CommonInfo {
  string timestamp = 1;
}

message TeacherProps {
  string teacher_id = 1;
}

message StudentProps {
  string student_id = 1;
}

message Member {
  string type = 1;
  string name = 2;
  oneof properties {
    TeacherProps teacher_props = 3;
    StudentProps student_props = 4;
  }
}

message MemberInfo {
  CommonInfo common_info = 1;
  repeated Member member = 2;
}
```

# `.proto` から Java コードを生成

以下コマンドで、 Protocol Buffers のメッセージ作成に必要な Java コードを生成する。

```sh
protoc -I=./proto --java_out=./src/main/java/ ./proto/MemberInfo.proto
```

`${PROJECT_ROOT}/src/main/dev/mikoto2000/javastudy/protocolbuffers/firststep/model/MemberInfoOuterClass.java` にコードが生成される。


# Protocol Buffers のエンコード・デコードを実装

簡単のために Spring Boot の `CommandLineRunner` を利用して処理を実装する。

```java
package dev.mikoto2000.javastudy.protocolbuffers.firststep;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

import dev.mikoto2000.javastudy.protocolbuffers.firststep.model.MemberInfoOuterClass.CommonInfo;
import dev.mikoto2000.javastudy.protocolbuffers.firststep.model.MemberInfoOuterClass.Member;
import dev.mikoto2000.javastudy.protocolbuffers.firststep.model.MemberInfoOuterClass.MemberInfo;
import dev.mikoto2000.javastudy.protocolbuffers.firststep.model.MemberInfoOuterClass.StudentProps;
import dev.mikoto2000.javastudy.protocolbuffers.firststep.model.MemberInfoOuterClass.TeacherProps;

/**
 * CliEntrypoint
 */
@Component
@Profile("!test")
public class CliEntrypoint implements CommandLineRunner {
	@Override
	public void run(String... args) throws InvalidProtocolBufferException {
		// MemberInfo の組み立て
		MemberInfo.Builder builder = MemberInfo.newBuilder();
		MemberInfo memberInfo = builder
			.setCommonInfo(CommonInfo.newBuilder().setTimestamp("1234567890").build())
			.addMember(Member.newBuilder()
					.setType("student")
					.setStudentProps(StudentProps.newBuilder()
						.setStudentId("mikoto2000")
						.build())
					.build())
			.addMember(Member.newBuilder()
					.setType("teacher")
					.setTeacherProps(TeacherProps.newBuilder()
						.setTeacherId("makoto2000")
						.build())
					.build())
			.build();

		System.out.printf("memberInfo: %s\n", memberInfo);

		// エンコード
		byte[] memberInfoBytes = memberInfo.toByteArray();

		// デコード
		MemberInfo memberInfoFromByteArray = MemberInfo.parseFrom(memberInfoBytes);

		System.out.printf("memberInfoFromByteArray: %s\n", memberInfoFromByteArray);

		// エンコード前後の結果比較
		System.out.printf("memberInfo.toString().equals(memberInfoFromByteArray.toString()): %s\n", memberInfo.toString().equals(memberInfoFromByteArray.toString()));
	}
}
```

# 動作確認

```sh
./mvnw spring-boot:run
```

以下のような出力になる。(Maven の出力や、 Spring Boot のロゴは省略)

```sh
memberInfo: common_info {
  timestamp: "1234567890"
}
member {
  type: "student"
  student_props {
    student_id: "mikoto2000"
  }
}
member {
  type: "teacher"
  teacher_props {
    teacher_id: "makoto2000"
  }
}

memberInfoFromByteArray: common_info {
  timestamp: "1234567890"
}
member {
  type: "student"
  student_props {
    student_id: "mikoto2000"
  }
}
member {
  type: "teacher"
  teacher_props {
    teacher_id: "makoto2000"
  }
}

memberInfo.toString().equals(memberInfoFromByteArray.toString()): true
```

うん、できている気がする。

以上。


# 参考資料

- [protobuf/java at main · protocolbuffers/protobuf](https://github.com/protocolbuffers/protobuf/tree/main/java)
- [Protocol Buffer Basics: Java | Protocol Buffers Documentation](https://protobuf.dev/getting-started/javatutorial/)
- [Java Generated Code Guide | Protocol Buffers Documentation](https://protobuf.dev/reference/java/java-generated/)
- [Package com.google.protobuf.util (3.19.4)  |  Java client library  |  Google Cloud](https://cloud.google.com/java/docs/reference/protobuf/latest/com.google.protobuf.util)

