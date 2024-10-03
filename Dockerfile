# Stage 1: Build the application using Maven and JDK 23
FROM openjdk:23-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY src src

# Copy Maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Set execution permission for the Maven wrapper
RUN chmod +x ./mvnw

# Build the project and create the jar
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final image with the built application
FROM openjdk:23-jdk
VOLUME /tmp

# Copy the JAR file from the previous build stage
COPY --from=build /app/target/SandBox-1.0-SNAPSHOT.jar app.jar

# Set the entry point for running the application
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
