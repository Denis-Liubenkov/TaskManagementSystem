FROM openjdk:17-alpine
ARG JAR_FILE=target/TaskManagementSystem-0.0.1-SNAPSHOT.jar
RUN mkdir /app
WORKDIR /app
COPY ${JAR_FILE} /app
ENTRYPOINT java -jar /app/TaskManagementSystem-0.0.1-SNAPSHOT.jar