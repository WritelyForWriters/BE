FROM openjdk:21-jdk-slim

WORKDIR /writeon

ENV TZ=Asia/Seoul
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

COPY api/build/libs/*.jar writeon.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "writeon.jar"]