FROM maven:3.8.7-eclipse-temurin-19-alpine AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean verify

FROM eclipse-temurin:19-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "app.jar"]