FROM openjdk:17-ea-17-jdk
VOLUME /tmp
COPY ./build/libs/community-service-0.0.1-SNAPSHOT.jar CommunityService.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","-Djava.net.preferIPv4Stack=true","CommunityService.jar"]