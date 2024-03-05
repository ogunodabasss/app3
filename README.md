Java21 preview

Spring Boot 3.2.3

Gradle 8.5

RabbitMQ 3.x

PostgreSql 16.x

Reactor 3.6.3


1) .env file fix
------------------

2) manuel build
------------------
root path........

cd m1

./gradlew clean

./gradlew build -x test

or

./gradlew build

cd ..

cd m2

./gradlew clean

./gradlew build -x test

------------------

3) docker-compose
-----------------
root path.....

docker-compose up --build -d

----------------

4) dockerfile build
---------------------
root path......... 

cd m1
docker build -t app3_m1_backend:latest .

cd ..

cd m2
docker build -t app3_m2_backend:latest .