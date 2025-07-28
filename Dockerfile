# Use Eclipse Temurin JDK 17 (more reliable)
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Install necessary packages
RUN apk add --no-cache curl

# Copy the entire project (excluding target and .git)
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Download dependencies (with retry mechanism)
RUN ./mvnw dependency:go-offline -B || \
    (sleep 10 && ./mvnw dependency:go-offline -B) || \
    (sleep 20 && ./mvnw dependency:go-offline -B)

# Build the application (with retry mechanism)
RUN ./mvnw clean package -DskipTests || \
    (sleep 10 && ./mvnw clean package -DskipTests) || \
    (sleep 20 && ./mvnw clean package -DskipTests)

# Create logs directory
RUN mkdir -p logs

# Expose port
EXPOSE 8080

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
CMD ["java", "-jar", "target/dump-truck-services-0.0.1-SNAPSHOT.jar"] 