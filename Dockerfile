FROM openjdk:11-jre-slim-buster
ADD target/authentication-service-0.0.1-SNAPSHOT.jar authentication-service-0.0.1-SNAPSHOT.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "authentication-service-0.0.1-SNAPSHOT.jar"]