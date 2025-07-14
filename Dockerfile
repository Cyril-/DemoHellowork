FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /home/app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21

COPY --from=build /home/app/target/francetravailscrapper-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar

RUN mkdir /opt/app
COPY docker-entrypoint.sh /opt/app
RUN chmod +x /opt/app/docker-entrypoint.sh

ENTRYPOINT ["/opt/app/docker-entrypoint.sh"]
