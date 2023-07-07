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

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#json').textContent = DEFAULT_JSON_STRING;
});
