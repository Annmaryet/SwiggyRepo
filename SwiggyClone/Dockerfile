# Use Java 17 slim image
FROM openjdk:17-jdk-slim

# Copy JAR file into container
COPY target/SwiggyClone-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]
