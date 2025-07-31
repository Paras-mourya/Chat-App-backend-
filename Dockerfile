# backend/Dockerfile

FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the jar file
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
