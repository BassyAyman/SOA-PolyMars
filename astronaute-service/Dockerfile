FROM openjdk:17-jdk-slim

# Add app user
ARG APPLICATION_USER=appuser
ARG SERVICE_NAME=astronaute-service

RUN adduser --disabled-password --gecos '' $APPLICATION_USER
RUN mkdir /app && chown -R $APPLICATION_USER /app

# Change to the app user
USER $APPLICATION_USER

# Copy jar file to /app in the container
COPY --chown=$APPLICATION_USER:root target/$SERVICE_NAME.jar /app/$SERVICE_NAME.jar

# Set working directory
WORKDIR /app

# Run the jar
CMD ["java", "-jar", "astronaute-service.jar"]