[![Codacy Badge](https://app.codacy.com/project/badge/Grade/fa3ea2106c20412bb2a8f9609d8aa00f)](https://www.codacy.com/gh/Vladi207/restaurant-voting/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Vladi207/restaurant-voting&amp;utm_campaign=Badge_Grade)

REST API Restaurant voting
==========================

Spring Boot, Spring Data JPA, Jackson, Lombok, H2 Database, JUnit 5, Swagger/ OpenAPI 3.0

----------

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
  - If it is before 11:00 we assume that he changed his mind.
  - If it is after 11:00 then it is too late, vote can't be changed
* Each restaurant provides a new menu each day.

------------
API documentation

[Swagger UI](http://localhost:8080/swagger-ui.html) 

Test credentials:
- user@yandex.ru / password
- admin@gmail.com / admin

_____________

Register:

curl -X 'POST' \
'http://localhost:8080/api/profile' \
-H 'accept: application/json' \
-H 'Content-Type: application/json' \
-d '{
"name": "newName",
"email": "newEmail@mail.ru",
"password": "newPassword",
"new": true
}'

_____________

Authorized user:

curl -X 'GET' \
'http://localhost:8080/api/profile' \
-H 'accept: application/json'