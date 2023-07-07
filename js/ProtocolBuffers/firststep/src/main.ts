const DEFAULT_JSON_STRING = `{
  "common_info": {
    "timestamp": "1234567890"
  },
  "members": [
    {
      "type": "student",
      "name": "mikoto2000",
      "student_id": "std_xxxx"
    },
    {
      "type": "teacher",
      "name": "makoto2000",
      "student_id": "tea_yyyy"
    },
    {
      "type": "teacher",
      "name": "mokoto2000",
      "student_id": "tea_zzzz"
    }
  ]
}
`;

const DEFAULT_PROTO_STRING = `syntax = "proto3";

message CommonInfo {
  string timestamp = 1;
}

message TeacherProps {
  string student_id = 1;
}

message StudentProps {
  string teacher_id = 1;
}

message Member {
  string type = 1;
  string name = 2;
  oneof properties {
    TeacherProps teacher_props = 3;
    StudentProps teacher_props = 4;
  }
}

message MebmerInfo {
  CommonInfo common_info = 1;
  repeated Member member = 2;
}
`;

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#json').textContent = DEFAULT_JSON_STRING;
  document.querySelector('#proto').textContent = DEFAULT_PROTO_STRING;
});
