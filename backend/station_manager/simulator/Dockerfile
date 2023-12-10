FROM openjdk:20-jdk-slim

COPY build/libs/simulator.jar.jar /app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
