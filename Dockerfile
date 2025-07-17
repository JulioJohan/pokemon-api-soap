
#Download the OpenJDK 21 image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

#Copy the built JAR file into the container
COPY build/libs/soap-service-0.0.1-SNAPSHOT.jar app.jar

# Set the entry point to run the JAR file
EXPOSE 8080

#Execute my application
ENTRYPOINT ["java", "-jar", "app.jar"]



