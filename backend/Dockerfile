FROM maven:3.9.5 AS maven
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package

FROM amazoncorretto:21.0.1-alpine3.18
WORKDIR /opt/app
COPY --from=maven /usr/src/app/target/backend-0.0.1-SNAPSHOT.jar /opt/app/
ENTRYPOINT ["java","-jar","/opt/app/backend-0.0.1-SNAPSHOT.jar"]