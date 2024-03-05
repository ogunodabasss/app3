

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /m1

CMD ["./gradlew", "clean","build -x test", "bootJar"]
COPY src ./src
COPY build/libs/*.jar ./m1-0.0.1-SNAPSHOT.jar

EXPOSE 28280
ENTRYPOINT ["java","-XX:TieredStopAtLevel=1","-Dfile.encoding=UTF-8","-Duser.country=US","-Duser.language=en","-jar","./m1-0.0.1-SNAPSHOT.jar"]


