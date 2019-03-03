FROM openjdk:8-jdk-alpine
COPY target/image-service-0.0.1-SNAPSHOT.jar image-service-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "image-service-0.0.1-SNAPSHOT.jar"]