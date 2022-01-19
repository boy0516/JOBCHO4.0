FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/reply-service-0.0.1-SNAPSHOT.jar ReplyService.jar
ENTRYPOINT ["java","-jar", "ReplyService.jar"]