# 1단계: 빌드 스테이지
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/app
WORKDIR /home/app
RUN ./gradlew build -x test

# 2단계: 실행 스테이지
FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY --from=build /home/app/build/libs/*-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]