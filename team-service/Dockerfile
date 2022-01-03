FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/team-service-0.0.1-SNAPSHOT.jar TeamService.jar
ENTRYPOINT ["java","-jar","TeamService.jar"]