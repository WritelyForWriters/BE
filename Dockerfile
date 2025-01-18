FROM openjdk:21-jdk-slim

WORKDIR /writely

ENV TZ=Asiz/Seoul

COPY build/libs/*.jar writely.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "writely.jar"]