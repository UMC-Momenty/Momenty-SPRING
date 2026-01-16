FROM eclipse-temurin:21-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} momenty.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "momenty.jar"]