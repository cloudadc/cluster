### add users to database

  http://localhost:8080/cluster-demo-jpa/test/addUsers/1000000

this will add 1000000 users to database, which each user will asssign a Random name(name-0,name-1,name-2,name-3,name-4) and carries a 100 byte array, so 1000000 users will at least 100 MB size bytes

### get search user via name's time

  http://localhost:8080/cluster-demo-jpa/test/getUserByName/name-0
  http://localhost:8080/cluster-demo-jpa/test/getUserByName/name-1
  http://localhost:8080/cluster-demo-jpa/test/getUserByName/name-2
  http://localhost:8080/cluster-demo-jpa/test/getUserByName/name-3
  http://localhost:8080/cluster-demo-jpa/test/getUserByName/name-4

this will return string like: "Get 19987 users spend 52 milliseconds"
