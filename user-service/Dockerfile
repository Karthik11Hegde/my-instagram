FROM openjdk:8-jdk-alpine
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]