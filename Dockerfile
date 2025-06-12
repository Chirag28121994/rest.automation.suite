# Stage 1: Build the app (compile, produce bootJar, skip tests)
FROM gradle:8.5-jdk17 AS build

# Copy the entire project, set ownership for gradle user
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

# Disable Gradle daemon (recommended in CI/container)
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false"

# Make gradlew executable
RUN chmod +x gradlew

# Build the bootJar (skip tests for faster build)
RUN ./gradlew bootJar -x test

# Stage 2: Runtime image with JDK and full source for dynamic gradle runs
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy the built bootJar for running the app
COPY --from=build /home/gradle/project/build/libs/app.jar app.jar

# Copy full source + gradlew for runtime gradle invocations
COPY --from=build /home/gradle/project /app

# Ensure gradlew is executable (needed for ProcessBuilder calls)
RUN chmod +x ./gradlew

# Optional: Set environment variables for gradle cache or JAVA options if needed

# Run your Spring Boot app normally
ENTRYPOINT ["java", "-jar", "app.jar"]
