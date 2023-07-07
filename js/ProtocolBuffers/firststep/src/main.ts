import protobuf from "protobufjs"

const DEFAULT_JSON_STRING = `{
  "common_info": {
    "timestamp": "1234567890"
  },
  "member": [
    {
      "type": "student",
      "name": "mikoto2000",
      "student_props": {
        "student_id": "std_xxxx"
      }
    },
    {
      "type": "teacher",
      "name": "makoto2000",
      "teacher_props": {
        "teacher_id": "tea_yyy"
      }
    },
    {
      "type": "teacher",
      "name": "mokoto2000",
      "teacher_props": {
        "teacher_id": "tea_zzzz"
      }
    }
  ]
}
`;

const DEFAULT_PROTO_STRING = `syntax = "proto3";

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
`;

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#json').textContent = DEFAULT_JSON_STRING;
  document.querySelector('#proto').textContent = DEFAULT_PROTO_STRING;

  // 今回の JSON は、スネークケースで定義しており、それをそのまま使いたい
  // そのため、グローバルオプションでキャメルケース変換を抑制する
  protobuf.parse.defaults = { keepCase: true };

  // proto 定義を読み込む
  const root = protobuf.parse(DEFAULT_PROTO_STRING).root;

  // proto 定義から MemberInfo の定義を取得
  const MemberInfo = root.lookupType('MemberInfo');

  // JSON をパースして、 MemberInfo に流し込み、バイナリ化する
  const json_object = JSON.parse(DEFAULT_JSON_STRING);
  const mi = MemberInfo.create(json_object);
  const buffer = MemberInfo.encode(mi).finish();


  // デコードしてテキストエリアへ表示
  const decoded_mi = JSON.stringify(MemberInfo.decode(buffer), null, 2);
  document.querySelector('#decoded_mi_json').textContent = decoded_mi;

});

  // ゼロから JavaScript で組み立ててみる
  import { MemberInfo } from "./MemberInfo.js"
  const memberInfo: MemberInfo.MemberInfo = {
    commonInfo: {
      timestamp: "1234567890"
    },
    member: [
      {
        type: "student",
        name: "mikoto2000",
        studentProps: {
          studentId: "std_xxxx"
        }
      }
    ]
  };

  const memberInfoFromScript = MemberInfo.create(memberInfo);
  document.querySelector('#build_from_code').textContent = JSON.stringify(memberInfoFromScript, null, 2);


