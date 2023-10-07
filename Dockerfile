FROM maven:3.8.5-openjdk-17 as build

WORKDIR /opt

COPY *pom.xml /opt/

COPY . /opt/

RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim

COPY --from=build /opt/target/easypay-*.jar /opt/app.jar

CMD java -jar /opt/app.jar --spring.profiles.active=prod