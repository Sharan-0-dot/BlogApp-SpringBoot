# Use Maven to build the app
FROM maven:3.9.5-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
COPY --from=build /target/Blog-0.0.1-SNAPSHOT.jar Blog.jar
EXPOSE 8080

ENTRYPOINT ["sh", "-c" , "java -jar Blog.jar"]
