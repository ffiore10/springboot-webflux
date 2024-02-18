FROM openjdk:17-jdk-slim
ADD target/springboot-webflux-0.0.1-SNAPSHOT.jar springboot-webflux-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "springboot-webflux-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080

