# Stage 1: Build the application
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN ./gradlew bootJar -x test

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk
WORKDIR /app
# Copy the compiled JAR
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
# Copy the whole project (including gradlew) if needed at runtime
COPY --from=build /home/gradle/project /app
ENTRYPOINT ["java", "-jar", "app.jar"]

