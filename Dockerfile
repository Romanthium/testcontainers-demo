FROM maven:3.9.5-eclipse-temurin-17 as BUILD
WORKDIR /
COPY /src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package -DskipTests

FROM eclipse-temurin:17-alpine
WORKDIR /
COPY /src /src
COPY --from=BUILD /target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]