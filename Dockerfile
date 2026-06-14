# 1단계: 빌드 스테이지
FROM gradle:8.5-jdk21 AS build
COPY --chown=gradle:gradle . /home/app
WORKDIR /home/app

RUN chmod +x ./gradlew
RUN ./gradlew build -x test --no-daemon -Dorg.gradle.jvmargs="-Xmx512m"

# 2단계: 실행 스테이지
FROM eclipse-temurin:21-jre-jammy
EXPOSE 8084
COPY --from=build /home/app/build/libs/*-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]