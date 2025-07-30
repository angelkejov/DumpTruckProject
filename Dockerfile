# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make the Maven wrapper executable
RUN chmod +x mvnw

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Create a non-root user to run the application
RUN addgroup --system javauser && adduser --system --ingroup javauser javauser

# Change ownership of the application directory
RUN chown -R javauser:javauser /app

# Switch to the non-root user
USER javauser

# Expose the port the application runs on
EXPOSE 8080

# Set default environment variables for Railway
ENV SPRING_PROFILES_ACTIVE=railway
ENV PORT=8080

# Set the default command to run the application
CMD ["java", "-jar", "target/dump-truck-services-0.0.1-SNAPSHOT.jar"] 