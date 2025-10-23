FROM openjdk:17-jdk-slim
LABEL authors="gtpkt"

WORKDIR /app

COPY target/delivery_service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]