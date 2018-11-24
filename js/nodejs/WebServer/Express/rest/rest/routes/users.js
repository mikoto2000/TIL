var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function(req, res, next) {
  let users = [
      {"id": 1, "name": "User01"},
      {"id": 2, "name": "User02"},
      {"id": 3, "name": "User03"},
      {"id": 4, "name": "User04"},
  ];
  res.send(users);
});

module.exports = router;
