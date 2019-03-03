FROM openjdk:8-jdk-alpine
COPY target/zuul-service-0.0.1-SNAPSHOT.jar zuul-service-0.0.1-SNAPSHOT.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "zuul-service-0.0.1-SNAPSHOT.jar"]