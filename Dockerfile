# Step 1: Use an official Java runtime as the base image
FROM eclipse-temurin:21-jdk

# Step 2: Set the working directory inside the container
WORKDIR /app

COPY target/*.jar app.jar

# Step 5: Specify the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]