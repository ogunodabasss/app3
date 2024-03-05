# syntax=docker/dockerfile:1

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /m2

CMD ["./gradlew", "clean","build", "bootJar"]
COPY src ./src
COPY build/libs/*.jar ./m2-0.0.1-SNAPSHOT.jar

EXPOSE 28281
ENTRYPOINT ["java","-XX:TieredStopAtLevel=1","-Dfile.encoding=UTF-8","-Duser.country=US","-Duser.language=en","-jar","./m2-0.0.1-SNAPSHOT.jar"]