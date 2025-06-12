# Stage 1: Build the application
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle bootJar -x test

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
COPY --from=build /app /app
#Include full source tree so gradlew exists
ENTRYPOINT ["java", "-jar", "app.jar"]
