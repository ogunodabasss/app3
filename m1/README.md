SELECT * FROM public.courier;
SELECT * FROM public.courier_package_ids;
SELECT * FROM public.package;
SELECT * FROM public."user";

--------------------------------------------
cd ..
.env files fix...
--------------------------------------------

--------------------------------------------
docker build -t app3_m1_backend:latest .

docker-compose up --build -d
docker compose up -d
docker compose down --rmi all

--------------------------------------------

./gradlew clean
./gradlew build