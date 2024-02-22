FROM openjdk:17-ea-17-jdk
VOLUME /tmp
COPY ./build/libs/stateservice-0.0.1-SNAPSHOT.jar state-service.jar
ENTRYPOINT ["java", "-jar", "-Dserver.port=9090", "state-service.jar"]