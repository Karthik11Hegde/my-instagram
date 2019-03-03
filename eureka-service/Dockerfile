FROM openjdk:8-jdk-alpine
COPY target/eureka-service-0.0.1-SNAPSHOT.jar eureka-service-0.0.1-SNAPSHOT.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eureka-service-0.0.1-SNAPSHOT.jar"]