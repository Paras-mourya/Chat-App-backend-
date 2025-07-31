# ---- Stage 1: Build JAR using Maven ----
FROM maven:3.9.4-eclipse-temurin-17 as build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ---- Stage 2: Run JAR using OpenJDK ----
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
