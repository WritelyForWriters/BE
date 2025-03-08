FROM openjdk:21-jdk-slim

WORKDIR /writeon

ENV TZ=Asiz/Seoul

COPY api/build/libs/*.jar writeon.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "writeon.jar"]