#!/bin/bash

# Build dell'applicazione Spring Boot
echo "Building Spring Boot application..."
./mvnw clean install

# Avvio di Docker Compose
echo "Starting Docker Compose..."
docker-compose up
