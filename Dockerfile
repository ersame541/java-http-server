# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the build files into the container
COPY . /app

# Build the application using Ant
RUN antRUN apt-get update && apt-get install -y ant && ant -version

# Command to run the application (adjust the path to your .jar file if necessary)
CMD ["java", "-jar", "dist/JavaApplication1.jar"]


