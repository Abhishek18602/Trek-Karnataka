# Use official Java image
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper & pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy the rest of the source code
COPY src src

# Make mvnw executable
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "target/Web-scraping-0.0.1-SNAPSHOT.jar"]
