# Use official JDK 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy your built jar into container
COPY target/course-management-0.0.1-SNAPSHOT.jar app.jar

# Expose backend port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Use official OpenJDK 21 image
# Stage 1: Build
# FROM maven:3.9.4-eclipse-temurin-21 AS build
# WORKDIR /app

# # Copy pom and download dependencies
# COPY pom.xml .
# RUN mvn dependency:go-offline -B

# # Copy source code
# COPY . .

# # Build the JAR
# RUN mvn clean package -DskipTests

# # Stage 2: Runtime
# FROM eclipse-temurin:21-jre-jammy
# WORKDIR /app

# # Copy JAR from build stage
# COPY --from=build /app/target/course-management-0.0.1-SNAPSHOT.jar ./course-management.jar

# # Expose port
# EXPOSE 8080

# # Run app
# CMD ["java", "-jar", "course-management.jar"]

