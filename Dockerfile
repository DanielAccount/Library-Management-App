# Step 1 : use an official Java runtime as the base image
FROM eclipse-temurin:21-jdk

# Step 2 : Set the working directory inside the contAainer
WORKDIR /app

# Step 3 : Copy the JAR file fro the target folder to the working directory
ADD target/app-0.0.1-SNAPSHOT.jar app.jar

# Step 4 : Expose the port your application will run on
EXPOSE 8081

# Step 5: Speciry the command to run the application
ENTRYPOINT ["java", "-jar", "library_management_docker.jar"]
