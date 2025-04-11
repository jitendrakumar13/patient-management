# Use an official Maven image to build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files into the container
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/billing-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 4001
EXPOSE 9001

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]