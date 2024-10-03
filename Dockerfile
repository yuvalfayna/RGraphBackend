# Stage 1: Build the application
FROM openjdk:19-jdk AS build
WORKDIR /app

# Copy the Maven wrapper and pom.xml first for caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Set execution permission for the Maven wrapper
RUN chmod +x ./mvnw

# Build the application (skip tests)
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final Docker image using OpenJDK 19
FROM openjdk:19-jdk
VOLUME /tmp

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Expose the port the app runs on
EXPOSE 8080
