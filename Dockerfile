FROM maven:3.8.6-openjdk-18-slim AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean package -DskipTests

FROM openjdk:17-alpine
RUN mkdir /app
COPY --from=build /project/target/usersApp-0.0.1.jar /app/application.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "application.jar"]
EXPOSE 8080