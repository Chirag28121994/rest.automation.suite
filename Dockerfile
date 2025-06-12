# Stage 1: Build the application
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

# 🔧 Make gradlew executable
RUN chmod +x gradlew

# ✅ Build the JAR without running tests
RUN ./gradlew bootJar -x test

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the executable JAR
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

# Optional: Copy entire source (only if your app uses gradlew at runtime)
COPY --from=build /home/gradle/project /app

ENTRYPOINT ["java", "-jar", "app.jar"]

