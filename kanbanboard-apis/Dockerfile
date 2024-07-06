# FROM openjdk:18
# WORKDIR /usr/src/bootapp
# COPY . /usr/src/bootapp/
# CMD ["java","-jar","target/com.betsol.kanbanboardTask-0.0.1-SNAPSHOT.jar"]

FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/com.betsol.kanbanboardTask-0.0.1-SNAPSHOT.jar kanban-live.jar
EXPOSE 9000
ENTRYPOINT ["java","-jar","kanban-live.jar"]
