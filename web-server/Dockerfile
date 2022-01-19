FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/web-server-0.0.1-SNAPSHOT.jar WebServer.jar
ENTRYPOINT ["java","-jar", "WebServer.jar"]