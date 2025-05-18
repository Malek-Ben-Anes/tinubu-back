# Stage 1: Build with Maven and OpenJDK 21
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies first (for caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the app
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with OpenJDK 21 JRE (Alpine)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (change if your app uses a different one)
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
