#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/werewolf-0.0.1-SNAPSHOT.jar werewolf.jar
# ENV PORT=8080
EXPOSE 8083
ENTRYPOINT ["java","-jar","werewolf.jar"]