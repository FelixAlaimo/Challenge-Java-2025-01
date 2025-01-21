# Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY target//ventas-0.0.1-SNAPSHOT.jar /app//ventas-0.0.1-SNAPSHOT.jar

# Expose the port your Spring Boot application will run on
EXPOSE 8080

# Command to run your application when the container starts
ENTRYPOINT ["java", "-jar", "/app/ventas-0.0.1-SNAPSHOT.jar"]