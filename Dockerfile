# 1단계: 빌드 스테이지
FROM gradle:8.5-jdk21 AS build
COPY --chown=gradle:gradle . /home/app
WORKDIR /home/app

# 👇 이 줄을 새로 추가해 줍니다 (실행 권한 부여)
RUN chmod +x ./gradlew

RUN ./gradlew build -x test

# 2단계: 실행 스테이지
FROM eclipse-temurin:21-jre-jammy
EXPOSE 8084
COPY --from=build /home/app/build/libs/*-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]