FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8083
ARG JAR_FILE=target/dsi.esprit.reclamationservice-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]