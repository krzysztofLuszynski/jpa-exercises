# jpa-exercises
Exercises for JPA.

## Database logging

To login into H2 database use link: http://localhost:8081/jpa-exercises/h2-console

Use JDBC URL from spring-boot logs (attaching example log):
```
2021-11-19 12:35:17.220  INFO 4156 --- [           main] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:2075d8a8-6c81-4422-b3dd-b30909517fd1'
```

## Usage
curl -v POST -H "Content-Type: application/json" -d "{\"name\": \"Jack\", \"surname\": \"White\", \"birthDate\": \"2010-01-01\"}" http://localhost:8081/jpa-exercises/persons
curl -v POST -H "Content-Type: application/json" -d "{\"name\": \"Johny\", \"surname\": \"Bravo\", \"birthDate\": \"2000-02-02\"}" http://localhost:8081/jpa-exercises/persons
curl -v http://localhost:8081/jpa-exercises/persons
curl -v http://localhost:8081/jpa-exercises/persons/1
curl -v http://localhost:8081/jpa-exercises/persons/2
curl -v -X PUT -H "Content-Type: application/json" -d "{\"name\": \"Jackie\", \"surname\": \"Whitee\", \"birthDate\": \"2022-01-01\"}" curl -v http://localhost:8081/jpa-exercises/persons/1
curl -v -X PUT -H "Content-Type: application/json" -d "{\"name\": \"Jack\", \"surname\": \"White\", \"birthDate\": \"2020-01-01\"}" curl -v http://localhost:8081/jpa-exercises/persons/1
curl -v -X PUT -H "Content-Type: application/json" -d "{\"name\": \"Jackie\", \"surname\": \"Whitee\", \"birthDate\": \"2018-01-01\"}" curl -v http://localhost:8081/jpa-exercises/persons/1

