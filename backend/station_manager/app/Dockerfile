FROM openjdk:20-jdk-slim

COPY build/libs/app.jar /app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]