FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/post-service-0.0.1-SNAPSHOT.jar PostService.jar
ENTRYPOINT ["java","-jar", "PostService.jar"]