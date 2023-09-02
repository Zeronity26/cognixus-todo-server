FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY ./target/cognixus-todo-server-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","cognixus-todo-server-0.0.1-SNAPSHOT.jar"]