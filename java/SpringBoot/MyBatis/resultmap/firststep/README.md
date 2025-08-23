# Spring Boot MyBatis

- [https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.7.4&packaging=jar&jvmVersion=17&groupId=dev.mikoto2000.study.springboot.mybatis&artifactId=firststep&name=firststep&description=Demo%20project%20for%20Spring%20Boot&packageName=dev.mikoto2000.study.springboot.mybatis.firststep&dependencies=devtools,lombok,mybatis,postgresql](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.7.4&packaging=jar&jvmVersion=17&groupId=dev.mikoto2000.study.springboot.mybatis&artifactId=firststep&name=firststep&description=Demo%20project%20for%20Spring%20Boot&packageName=dev.mikoto2000.study.springboot.mybatis.firststep&dependencies=devtools,lombok,mybatis,postgresql)


## Tables:

SQL:

```sql
select
  *
from
  "user"
left outer join
  user_info
on
  "user".id = user_info.user_id;
```

Result:

```
 id | name  | id | user_id | description
----+-------+----+---------+-------------
  1 | user1 |  1 |       1 | user1 info1
  1 | user1 |  2 |       1 | user1 info2
  1 | user1 |  3 |       1 | user1 info3
  2 | user2 |  4 |       2 | user2 info1
  2 | user2 |  5 |       2 | user2 info2
  2 | user2 |  6 |       2 | user2 info3
(6 rows)
```
