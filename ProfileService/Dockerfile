FROM openjdk:8-jdk-alpine

ARG NAME=target/*.jar
COPY ${NAME} app.jar

ENTRYPOINT [ "java", "-jar", "/app.jar" ]