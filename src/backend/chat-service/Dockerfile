FROM openjdk:17-ea-17-jdk
VOLUME /tmp
COPY ./build/libs/chat-service-0.0.1-SNAPSHOT.jar chat-service.jar
ENTRYPOINT ["java", "-jar", "-Dserver.port=4000", "chat-service.jar"]