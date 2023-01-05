FROM adoptopenjdk/openjdk11-openj9:alpine-slim
EXPOSE 8080
ARG JAR_FILE=target/faulttolerance-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]