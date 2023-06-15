FROM openjdk:17-jdk-slim

COPY build/libs/todoapp-0.0.1-SNAPSHOT.jar todoapp.jar

CMD ["java", "-jar", "todoapp.jar"]
