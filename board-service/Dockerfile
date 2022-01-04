FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/board-service-0.0.1-SNAPSHOT.jar BoardService.jar
ENTRYPOINT ["java","-jar", "BoardService.jar"]