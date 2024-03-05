SELECT * FROM public.courier;
SELECT * FROM public.courier_package_ids;
SELECT * FROM public.package;
SELECT * FROM public."user";

--------------------------------------------
cd ..
.env files fix...
--------------------------------------------

--------------------------------------------
./gradlew or gradle
...
gradle clean
gradle build
or
gradle build -x test

------

docker build -t app3_m2_backend:latest .

docker-compose up --build -d
docker compose up -d
docker compose down --rmi all

--------------------------------------------