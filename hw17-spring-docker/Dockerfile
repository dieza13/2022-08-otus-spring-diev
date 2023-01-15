FROM maven:3.5-jdk-11-slim
ENV PROJECT_DIR=/app
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
ADD ./pom.xml $PROJECT_DIR
ADD ./src/ $PROJECT_DIR/src
RUN mvn install
FROM openjdk:11-jre-slim
ENV PROJECT_DIR=/app
RUN mkdir -p $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/docker-hw17-spring-docker.jar $PROJECT_DIR/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
